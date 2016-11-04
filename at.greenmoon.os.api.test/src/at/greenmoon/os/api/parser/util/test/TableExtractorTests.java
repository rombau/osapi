package at.greenmoon.os.api.parser.util.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.junit.Test;

import at.greenmoon.os.OnlineSoccer.ParseException;
import at.greenmoon.os.parser.util.TableExtractor;
import at.greenmoon.os.parser.util.TableExtractor.IColumn;
import at.greenmoon.os.parser.util.TableExtractor.ITable;

@SuppressWarnings("nls")
public class TableExtractorTests {

    private enum TestColumns implements IColumn {
        One, Two
    }

    @Test
    public void testTableExtractorClass() throws Exception {

        new TableExtractor() {
            //
        };
    }

    @Test(expected = ParseException.class)
    public void testNoTable() throws Exception {

        Element element = Jsoup.parse("<div/>");

        TableExtractor.extract(element, TestColumns.values(), false, this);
    }

    @Test(expected = ParseException.class)
    public void testEmptyHtmlTable() throws Exception {

        Element element = Jsoup.parse("<table/>");

        TableExtractor.extract(element, TestColumns.values(), false, this);
    }

    @Test
    public void testAllTableColumnsNotFound() throws Exception {

        Element element = Jsoup.parse("<table><tr><td>Three</td><td>Four</td></tr></table>");

        try {
            TableExtractor.extract(element, TestColumns.values(), false, this);
            fail("ParseException expected.");
        }
        catch (ParseException e) {
            assertEquals(
                    "Die Tabellenspalten [One, Two] können nicht gefunden werden. [at.greenmoon.os.api.parser.util.test.TableExtractorTests]",
                    e.getMessage());
        }
    }

    @Test
    public void testOneTableColumnNotFound() throws Exception {

        Element element = Jsoup.parse("<table><tr><td>Three</td><td>One</td></tr></table>");

        try {
            TableExtractor.extract(element, TestColumns.values(), false, this);
            fail("ParseException expected.");
        }
        catch (ParseException e) {
            assertEquals(
                    "Die Tabellenspalten [Two] können nicht gefunden werden. [at.greenmoon.os.api.parser.util.test.TableExtractorTests]",
                    e.getMessage());
        }
    }

    @Test
    public void testTwoTables() throws Exception {

        Element element = Jsoup
                .parse("<table><tr><td>Three</td><td>One</td></tr></table><table><tr><td>XXX</td><td>YYY</td></tr></table>");

        try {
            TableExtractor.extract(element, TestColumns.values(), false, this);
            fail("ParseException expected.");
        }
        catch (ParseException e) {
            assertEquals(
                    "Die Tabellenspalten [Two] können nicht gefunden werden. [at.greenmoon.os.api.parser.util.test.TableExtractorTests]",
                    e.getMessage());
        }
    }

    @Test
    public void testEmptyTableAllColumnsFound() throws Exception {

        Element element = Jsoup.parse("<table><tr><td>One</td><td>Two</td></tr></table>");

        ITable table = TableExtractor.extract(element, TestColumns.values(), false, this).get(0);

        assertEquals(0, table.getRows().size());
    }

    @Test
    public void testTwoEmptyTablesAllColumnsFound() throws Exception {

        Element element = Jsoup
                .parse("<table><tr><td>One</td><td>Two</td></tr></table><table><tr><td>One</td><td>Two</td></tr></table>");

        List<ITable> tables = TableExtractor.extract(element, TestColumns.values(), false, this);

        assertEquals(2, tables.size());
    }

    @Test
    public void testTwoEmptyTablesAllColumnsFoundOnce() throws Exception {

        Element element = Jsoup
                .parse("<table><tr><td>One</td><td>XXX</td></tr></table><table><tr><td>One</td><td>Two</td></tr></table>");

        List<ITable> tables = TableExtractor.extract(element, TestColumns.values(), false, this);

        assertEquals(1, tables.size());
    }

    @Test
    public void testTableWithOneRowAllColumnsFound() throws Exception {

        Element element = Jsoup.parse("<table><tr><td>One</td><td>Two</td></tr><tr><td>1</td><td>2</td></tr></table>");

        ITable table = TableExtractor.extract(element, TestColumns.values(), false, this).get(0);

        assertEquals(1, table.getRows().size());
        assertEquals("1", table.getRows().get(0).getCell(TestColumns.One).text());
        assertEquals("2", table.getRows().get(0).getCell(TestColumns.Two).text());
    }
}
