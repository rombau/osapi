package at.greenmoon.os.api.parser.test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import at.greenmoon.os.OnlineSoccer.AuthenticationException;
import at.greenmoon.os.OnlineSoccer.ParseException;
import at.greenmoon.os.api.test.fixtures.TestResource;
import at.greenmoon.os.model.ISpieler.Skill;
import at.greenmoon.os.model.ITeam;
import at.greenmoon.os.model.impl.ModelProxyFactory;
import at.greenmoon.os.parser.TeamSkillsParser;
import at.greenmoon.os.resource.IResource;

@SuppressWarnings("nls")
public class TeamSkillsParserTests {

    private final TeamSkillsParser parser = new TeamSkillsParser();

    @Test(expected = AuthenticationException.class)
    public void testDemoTeam() throws Exception {

        // @formatter:off
        IResource document = new TestResource(
                "<b>DemoTeam - </b><a href=\"javascript:tabellenplatz(0)\"/>");
        // @formatter:on

        parser.parse(document, null);
    }

    @Test(expected = ParseException.class)
    public void testNoTeamTitel() throws Exception {

        IResource document = new TestResource("<p/>");

        parser.parse(document, null);
    }

    @Test(expected = ParseException.class)
    public void testIncorrectTeamTitel() throws Exception {

        // @formatter:off
        IResource document = new TestResource(
                "<b>Bindestrich und Liga fehlen</b><a href=\"javascript:tabellenplatz(1)\"/>");
        // @formatter:on

        parser.parse(document, null);
    }

    @Test(expected = ParseException.class)
    public void testNoTeamId() throws Exception {

        // @formatter:off
        IResource document = new TestResource(
                "<b>Teamname - 1. Liga</b>");
        // @formatter:on

        parser.parse(document, null);
    }

    @Test(expected = ParseException.class)
    public void testNoTable() throws Exception {

        IResource document = new TestResource(
                "<b>FC Cork - 2. Liga A<a>Irland</a></b><a href=\"javascript:tabellenplatz(1)\"/>");

        parser.parse(document, null);
    }

    @Test
    public void testParseTeamIdToInitializedTeam() throws Exception {

        // @formatter:off
        IResource document = new TestResource(
                "<b>Teamname - 1. Liga</b><a href=\"javascript:tabellenplatz(1)\"/>"
                        + "<table>"
                        + "<tr><td>#</td><td>Name</td><td>Land</td><td>U</td><td>SCH</td><td>BAK</td><td>KOB</td><td>ZWK</td><td>DEC</td><td>GES</td><td>FUQ</td><td>ERF</td><td>AGG</td><td>PAS</td><td>AUS</td><td>UEB</td><td>WID</td><td>SEL</td><td>DIS</td><td>ZUV</td><td>EIN</td></tr>"
                        + "<tr><td>1</td><td><a onClick=\"spielerinfo(4711);return false;\" href=\"sp.php?s=4711\">Hugo</a></td><td>IRL</td><td></td><td>50</td><td>50</td><td>50</td><td>50</td><td>50</td><td>50</td><td>50</td><td>50</td><td>50</td><td>50</td><td>50</td><td>50</td><td>50</td><td>50</td><td>50</td><td>50</td><td>50</td></tr>"
                        + "<tr><td>#</td><td>Name</td><td>Land</td><td>U</td><td>SCH</td><td>BAK</td><td>KOB</td><td>ZWK</td><td>DEC</td><td>GES</td><td>FUQ</td><td>ERF</td><td>AGG</td><td>PAS</td><td>AUS</td><td>UEB</td><td>WID</td><td>SEL</td><td>DIS</td><td>ZUV</td><td>EIN</td></tr>"
                        + "</table>");
        // @formatter:on
        ITeam team = ModelProxyFactory.create(ITeam.class, document);
        team.setId(0);

        team = parser.parse(document, team);

        assertEquals(1, team.getId().intValue());
    }

    @Test
    public void testTeamWithOnePlayer() throws Exception {

        // @formatter:off
        IResource document = new TestResource(
                "<b>Teamname - 1. Liga</b><a href=\"javascript:tabellenplatz(1)\"/>"
                        + "<table>"
                        + "<tr><td>#</td><td>Name</td><td>Land</td><td>U</td><td>SCH</td><td>BAK</td><td>KOB</td><td>ZWK</td><td>DEC</td><td>GES</td><td>FUQ</td><td>ERF</td><td>AGG</td><td>PAS</td><td>AUS</td><td>UEB</td><td>WID</td><td>SEL</td><td>DIS</td><td>ZUV</td><td>EIN</td></tr>"
                        + "<tr><td>1</td><td><a onClick=\"spielerinfo(4711);return false;\" href=\"sp.php?s=4711\">Hugo</a></td><td>IRL</td><td></td><td>50</td><td>50</td><td>50</td><td>50</td><td>50</td><td>50</td><td>50</td><td>50</td><td>50</td><td>50</td><td>50</td><td>50</td><td>50</td><td>50</td><td>50</td><td>50</td><td>50</td></tr>"
                        + "<tr><td>#</td><td>Name</td><td>Land</td><td>U</td><td>SCH</td><td>BAK</td><td>KOB</td><td>ZWK</td><td>DEC</td><td>GES</td><td>FUQ</td><td>ERF</td><td>AGG</td><td>PAS</td><td>AUS</td><td>UEB</td><td>WID</td><td>SEL</td><td>DIS</td><td>ZUV</td><td>EIN</td></tr>"
                        + "</table>");
        // @formatter:on

        ITeam team = parser.parse(document, null);

        assertEquals(1, team.getId().intValue());
        assertEquals("Teamname", team.getName());
        assertEquals(1, team.getKaderspieler().size());
        assertEquals("Hugo", team.getKaderspieler().get(0).getName());
        assertEquals(4711, team.getKaderspieler().get(0).getId().longValue());

        assertEquals(50, team.getKaderspieler().get(0).getSkills().get(Skill.SCH).intValue());
        assertEquals(50, team.getKaderspieler().get(0).getSkills().get(Skill.BAK).intValue());
        assertEquals(50, team.getKaderspieler().get(0).getSkills().get(Skill.KOB).intValue());
        assertEquals(50, team.getKaderspieler().get(0).getSkills().get(Skill.ZWK).intValue());
        assertEquals(50, team.getKaderspieler().get(0).getSkills().get(Skill.DEC).intValue());
        assertEquals(50, team.getKaderspieler().get(0).getSkills().get(Skill.GES).intValue());
        assertEquals(50, team.getKaderspieler().get(0).getSkills().get(Skill.FUQ).intValue());
        assertEquals(50, team.getKaderspieler().get(0).getSkills().get(Skill.ERF).intValue());
        assertEquals(50, team.getKaderspieler().get(0).getSkills().get(Skill.AGG).intValue());
        assertEquals(50, team.getKaderspieler().get(0).getSkills().get(Skill.PAS).intValue());
        assertEquals(50, team.getKaderspieler().get(0).getSkills().get(Skill.AUS).intValue());
        assertEquals(50, team.getKaderspieler().get(0).getSkills().get(Skill.UEB).intValue());
        assertEquals(50, team.getKaderspieler().get(0).getSkills().get(Skill.WID).intValue());
        assertEquals(50, team.getKaderspieler().get(0).getSkills().get(Skill.SEL).intValue());
        assertEquals(50, team.getKaderspieler().get(0).getSkills().get(Skill.DIS).intValue());
        assertEquals(50, team.getKaderspieler().get(0).getSkills().get(Skill.ZUV).intValue());
        assertEquals(50, team.getKaderspieler().get(0).getSkills().get(Skill.EIN).intValue());
    }
}
