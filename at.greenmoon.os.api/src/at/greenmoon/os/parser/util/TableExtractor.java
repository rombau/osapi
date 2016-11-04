package at.greenmoon.os.parser.util;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import at.greenmoon.os.Messages;
import at.greenmoon.os.OnlineSoccer.ParseException;

public abstract class TableExtractor {

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    public @interface Optional {

        // no value
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    public @interface Title {

        String value();
    }

    public interface IColumn {

        // marker interface for column enums
    }

    private static class Column implements IColumn {

        private final IColumn column;
        private final String header;
        private final boolean optional;
        private int rowIndex;

        public Column(IColumn column, String header, boolean optional) {
            this.column = column;
            this.header = header;
            this.optional = optional;
        }

        public IColumn getOriginalColumn() {
            return column;
        }

        public String getHeader() {
            return header;
        }

        public boolean setRowIndex(List<String> headers) {
            rowIndex = headers.indexOf(header);
            return rowIndex == -1 && !optional;
        }

        public int getRowIndex() {
            return rowIndex;
        }
    }

    public interface IRow {

        Element getCell(IColumn column);
    }

    private static class Row implements IRow {

        private final Map<IColumn, Element> cells = new HashMap<IColumn, Element>();

        @Override
        public Element getCell(IColumn column) {
            return cells.get(column);
        }

        public void addCell(Column column, Element htmlCell) {
            cells.put(column.getOriginalColumn(), htmlCell);
        }
    }

    public interface ITable {

        List<IRow> getRows();
    }

    private static class Table implements ITable {

        private final Elements htmlRows;

        private final List<IRow> rows = new ArrayList<IRow>();

        private final List<String> headers = new ArrayList<String>();
        private final List<String> unknownHeaders = new ArrayList<String>();

        public Table(Element htmlTable) {

            htmlRows = htmlTable.getElementsByTag("tr"); //$NON-NLS-1$
            if (!htmlRows.isEmpty()) {
                for (Element cell : htmlRows.first().getElementsByTag("td")) { //$NON-NLS-1$
                    headers.add(cell.text().trim());
                }
            }
        }

        @Override
        public List<IRow> getRows() {
            return rows;
        }

        public Elements getHtmlRows() {
            return htmlRows;
        }

        public List<String> getHeaders() {
            return headers;
        }

        public List<String> getUnknownHeaders() {
            return unknownHeaders;
        }

        public void addUnknownHeader(String header) {
            unknownHeaders.add(header);
        }
    }

    public static List<ITable> extract(Element parent, IColumn[] columns, boolean hasFooter, final Object parser)
            throws ParseException {

        // TODO check colspan and wrong column count

        Elements htmlTables = parent.getElementsByTag("table"); //$NON-NLS-1$
        if (htmlTables.isEmpty()) {
            throw new ParseException(Messages.getString("TableExtractor.TableNotFound"), parser); //$NON-NLS-1$
        }

        List<ITable> tables = new ArrayList<ITable>();

        List<Column> searchColumns = createSearchColumnList(columns, parser);
        List<String> columnsNotFound = null;

        for (Element htmlTable : htmlTables) {

            Table table = new Table(htmlTable);

            for (int r = 0; r < table.getHtmlRows().size() && table.getUnknownHeaders().isEmpty(); r++) {
                Element htmlRow = table.getHtmlRows().get(r);
                Elements htmlCells = htmlRow.getElementsByTag("td"); //$NON-NLS-1$
                if (r == 0) {
                    for (Column column : searchColumns) {
                        if (column.setRowIndex(table.getHeaders())) {
                            table.addUnknownHeader(column.getHeader());
                        }
                    }
                }
                else if (r < (table.getHtmlRows().size() - 1) || !hasFooter) {
                    if (table.getUnknownHeaders().isEmpty()) {
                        Row row = new Row();
                        for (Column column : searchColumns) {
                            if (column.getRowIndex() != -1) {
                                Element htmlCell = htmlCells.get(column.getRowIndex());
                                row.addCell(column, htmlCell);
                            }
                        }
                        table.getRows().add(row);
                    }
                }
            }

            if (table.getUnknownHeaders().isEmpty() && !table.getHtmlRows().isEmpty()) {
                tables.add(table);
            }

            if (columnsNotFound == null || columnsNotFound.size() > table.getUnknownHeaders().size()) {
                columnsNotFound = table.getUnknownHeaders();
            }
        }

        if (!tables.isEmpty()) {
            return tables;
        }

        throw new ParseException(MessageFormat.format(Messages.getString("TableExtractor.TableColumnsNotFound"), //$NON-NLS-1$
                columnsNotFound), parser);
    }

    private static List<Column> createSearchColumnList(IColumn[] columns, final Object parser) throws ParseException {

        List<Column> columnList = new ArrayList<Column>();
        for (IColumn column : columns) {
            String header = column.toString();
            boolean optional = false;
            try {
                Field field = column.getClass().getField(column.toString());
                Title title = field.getAnnotation(Title.class);
                if (title != null) {
                    header = title.value();
                }
                optional = field.isAnnotationPresent(Optional.class);
            }
            catch (Exception e) {
                throw new ParseException(e.getLocalizedMessage(), parser, e);
            }
            columnList.add(new Column(column, header, optional));
        }
        return columnList;
    }

    @SuppressWarnings("unused")
    private static int extractColSpan(Element cell) {

        try {
            return Integer.valueOf(cell.attr("colspan")); //$NON-NLS-1$
        }
        catch (Exception e) {
            return 1;
        }
    }
}
