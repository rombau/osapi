package at.greenmoon.os.parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import at.greenmoon.os.OnlineSoccer.AuthenticationException;
import at.greenmoon.os.OnlineSoccer.ParseException;
import at.greenmoon.os.model.IKaderspieler;
import at.greenmoon.os.model.ITeam;
import at.greenmoon.os.model.impl.ModelProxyFactory;
import at.greenmoon.os.parser.util.LinkExtractor;
import at.greenmoon.os.parser.util.TableExtractor;
import at.greenmoon.os.parser.util.TableExtractor.IColumn;
import at.greenmoon.os.parser.util.TableExtractor.IRow;
import at.greenmoon.os.parser.util.TableExtractor.ITable;
import at.greenmoon.os.resource.IResource;
import at.greenmoon.os.resource.IResourceParser;

public class TeamContractsParser extends AbstractTeamParser implements IResourceParser<ITeam> {

    private enum Column implements IColumn {
        Name, Vertrag, Monatsgehalt, Spielerwert;
    }

    @SuppressWarnings("nls")
    @Override
    public ITeam parse(IResource resource, ITeam team) throws ParseException, AuthenticationException {
        Document jsoupDocument = Jsoup.parse(resource.getContent());

        checkDemoteam(jsoupDocument);

        if (team == null) {
            team = ModelProxyFactory.create(ITeam.class, resource);
        }

        team.setId(extractTeamId(jsoupDocument));
        team.setName(extractTeamName(jsoupDocument));

        ITable table = TableExtractor.extract(jsoupDocument, Column.values(), true, this).get(0);
        for (IRow row : table.getRows()) {

            Long id = LinkExtractor.extractId(row.getCell(Column.Name));
            IKaderspieler spieler = team.getKaderspielerById(id);

            spieler.setId(id);
            spieler.setTeam(team);
            spieler.setName(row.getCell(Column.Name).text());

            spieler.setVertragslaufzeit(Integer.valueOf(row.getCell(Column.Vertrag).text()));
            spieler.setMonatsgehalt(Long.valueOf(row.getCell(Column.Monatsgehalt).text().replaceAll("\\.", "")));
            spieler.setMarktwert(Long.valueOf(row.getCell(Column.Spielerwert).text().replaceAll("\\.", "")));
        }

        return team;
    }
}
