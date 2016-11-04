package at.greenmoon.os.parser;

import java.util.HashMap;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import at.greenmoon.os.OnlineSoccer.AuthenticationException;
import at.greenmoon.os.OnlineSoccer.ParseException;
import at.greenmoon.os.model.IKaderspieler;
import at.greenmoon.os.model.ISpieler.Skill;
import at.greenmoon.os.model.ITeam;
import at.greenmoon.os.model.impl.ModelProxyFactory;
import at.greenmoon.os.parser.util.LinkExtractor;
import at.greenmoon.os.parser.util.TableExtractor;
import at.greenmoon.os.parser.util.TableExtractor.IColumn;
import at.greenmoon.os.parser.util.TableExtractor.IRow;
import at.greenmoon.os.parser.util.TableExtractor.ITable;
import at.greenmoon.os.resource.IResource;
import at.greenmoon.os.resource.IResourceParser;

public class TeamSkillsParser extends AbstractTeamParser implements IResourceParser<ITeam> {

    private enum Column implements IColumn {
        Name, SCH, BAK, KOB, ZWK, DEC, GES, FUQ, ERF, AGG, PAS, AUS, UEB, WID, SEL, DIS, ZUV, EIN;
    }

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

            Map<Skill, Integer> skills = new HashMap<Skill, Integer>();
            skills.put(Skill.SCH, Integer.valueOf(row.getCell(Column.SCH).text()));
            skills.put(Skill.BAK, Integer.valueOf(row.getCell(Column.BAK).text()));
            skills.put(Skill.KOB, Integer.valueOf(row.getCell(Column.KOB).text()));
            skills.put(Skill.ZWK, Integer.valueOf(row.getCell(Column.ZWK).text()));
            skills.put(Skill.DEC, Integer.valueOf(row.getCell(Column.DEC).text()));
            skills.put(Skill.GES, Integer.valueOf(row.getCell(Column.GES).text()));
            skills.put(Skill.FUQ, Integer.valueOf(row.getCell(Column.FUQ).text()));
            skills.put(Skill.ERF, Integer.valueOf(row.getCell(Column.ERF).text()));
            skills.put(Skill.AGG, Integer.valueOf(row.getCell(Column.AGG).text()));
            skills.put(Skill.PAS, Integer.valueOf(row.getCell(Column.PAS).text()));
            skills.put(Skill.AUS, Integer.valueOf(row.getCell(Column.AUS).text()));
            skills.put(Skill.UEB, Integer.valueOf(row.getCell(Column.UEB).text()));
            skills.put(Skill.WID, Integer.valueOf(row.getCell(Column.WID).text()));
            skills.put(Skill.SEL, Integer.valueOf(row.getCell(Column.SEL).text()));
            skills.put(Skill.DIS, Integer.valueOf(row.getCell(Column.DIS).text()));
            skills.put(Skill.ZUV, Integer.valueOf(row.getCell(Column.ZUV).text()));
            skills.put(Skill.EIN, Integer.valueOf(row.getCell(Column.EIN).text()));

            spieler.setSkills(skills);
        }

        return team;
    }
}
