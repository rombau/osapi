package at.greenmoon.os.api.parser.test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import at.greenmoon.os.OnlineSoccer.AuthenticationException;
import at.greenmoon.os.OnlineSoccer.ParseException;
import at.greenmoon.os.api.test.fixtures.TestResource;
import at.greenmoon.os.model.ITeam;
import at.greenmoon.os.model.impl.ModelProxyFactory;
import at.greenmoon.os.parser.TeamContractsParser;
import at.greenmoon.os.resource.IResource;

@SuppressWarnings("nls")
public class TeamContractsParserTests {

    private final TeamContractsParser parser = new TeamContractsParser();

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
                        + "<tr><td>#</td><td>Nr.</td><td>Name</td><td>Alter</td><td>Pos</td><td></td><td>Land</td><td>U</td><td>Skillschnitt</td><td>Opt.Skill</td><td>Vertrag</td><td>Monatsgehalt</td><td>Spielerwert</td></tr>"
                        + "<tr><td>1</td><td>1</td><td><a onClick=\"spielerinfo(4711);return false;\" href=\"sp.php?s=4711\">Hugo</a></td><td>20</td><td>TOR</td><td>Flagge</td><td>IRL</td><td></td><td>49.15</td><td>78.65</td><td>15</td><td>123.456</td><td>12.345.678</td></tr>"
                        + "<tr><td>#</td><td>Nr.</td><td>Name</td><td>Alter</td><td>Pos</td><td></td><td>Land</td><td>U</td><td>Skillschnitt</td><td>Opt.Skill</td><td>Vertrag</td><td>Monatsgehalt</td><td>Spielerwert</td></tr>"
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
                        + "<tr><td>#</td><td>Nr.</td><td>Name</td><td>Alter</td><td>Pos</td><td></td><td>Land</td><td>U</td><td>Skillschnitt</td><td>Opt.Skill</td><td>Vertrag</td><td>Monatsgehalt</td><td>Spielerwert</td></tr>"
                        + "<tr><td>1</td><td>1</td><td><a onClick=\"spielerinfo(4711);return false;\" href=\"sp.php?s=4711\">Hugo</a></td><td>20</td><td>TOR</td><td>Flagge</td><td>IRL</td><td></td><td>49.15</td><td>78.65</td><td>15</td><td>123.456</td><td>12.345.678</td></tr>"
                        + "<tr><td>#</td><td>Nr.</td><td>Name</td><td>Alter</td><td>Pos</td><td></td><td>Land</td><td>U</td><td>Skillschnitt</td><td>Opt.Skill</td><td>Vertrag</td><td>Monatsgehalt</td><td>Spielerwert</td></tr>"
                        + "</table>");
        // @formatter:on

        ITeam team = parser.parse(document, null);

        assertEquals(1, team.getId().intValue());
        assertEquals("Teamname", team.getName());
        assertEquals(1, team.getKaderspieler().size());
        assertEquals("Hugo", team.getKaderspieler().get(0).getName());
        assertEquals(4711, team.getKaderspieler().get(0).getId().longValue());

        assertEquals(15, team.getKaderspieler().get(0).getVertragslaufzeit().intValue());
        assertEquals(123456, team.getKaderspieler().get(0).getMonatsgehalt().longValue());
        assertEquals(12345678, team.getKaderspieler().get(0).getMarktwert().longValue());
    }
}
