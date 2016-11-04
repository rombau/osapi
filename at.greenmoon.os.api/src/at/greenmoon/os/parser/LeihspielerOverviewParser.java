package at.greenmoon.os.parser;

import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import at.greenmoon.os.OnlineSoccer.AuthenticationException;
import at.greenmoon.os.OnlineSoccer.OfflineException;
import at.greenmoon.os.OnlineSoccer.ParseException;
import at.greenmoon.os.model.IKaderspieler;
import at.greenmoon.os.model.IKaderspieler.Leihstatus;
import at.greenmoon.os.model.ISpieler.Position;
import at.greenmoon.os.model.ITeam;
import at.greenmoon.os.model.impl.ModelProxyFactory;
import at.greenmoon.os.parser.util.LinkExtractor;
import at.greenmoon.os.parser.util.TableExtractor;
import at.greenmoon.os.parser.util.TableExtractor.IColumn;
import at.greenmoon.os.parser.util.TableExtractor.IRow;
import at.greenmoon.os.parser.util.TableExtractor.ITable;
import at.greenmoon.os.parser.util.TableExtractor.Title;
import at.greenmoon.os.resource.IResource;
import at.greenmoon.os.resource.IResourceParser;

public class LeihspielerOverviewParser extends AbstractParser implements IResourceParser<ITeam> {

    private static final int TABLE_INDEX_VERLIEHEN = 0;
    private static final int TABLE_INDEX_GELIEHEN = 1;

    private static final String POSITION_CLASS = "class"; //$NON-NLS-1$

    private enum Column implements IColumn {
        Name, Leihdauer, Gehalt, @Title("Leihgebühr") Leihgebuehr, Leihclub;
    }

    @Override
    public ITeam parse(IResource resource, ITeam team) throws ParseException, OfflineException, AuthenticationException {

        Document jsoupDocument = Jsoup.parse(resource.getContent());

        checkDemoteam(jsoupDocument);

        if (team == null) {
            team = ModelProxyFactory.create(ITeam.class, resource);
        }

        List<ITable> tables = TableExtractor.extract(jsoupDocument, Column.values(), false, this);

        for (IRow row : tables.get(TABLE_INDEX_VERLIEHEN).getRows()) {

            Long id = LinkExtractor.extractId(row.getCell(Column.Name));
            IKaderspieler spieler = team.getKaderspielerById(id);

            spieler.setId(id);
            spieler.setPosition(Position.valueOf(row.getCell(Column.Name).attr(POSITION_CLASS)));
            spieler.setName(row.getCell(Column.Name).text());
            spieler.setLeihstatus(Leihstatus.Verliehen);

        }

        for (IRow row : tables.get(TABLE_INDEX_GELIEHEN).getRows()) {

            Long id = LinkExtractor.extractId(row.getCell(Column.Name));
            IKaderspieler spieler = team.getKaderspielerById(id);

            spieler.setId(id);
            spieler.setPosition(Position.valueOf(row.getCell(Column.Name).attr(POSITION_CLASS)));
            spieler.setName(row.getCell(Column.Name).text());
            spieler.setLeihstatus(Leihstatus.Geliehen);

        }

        return team;
    }
}
