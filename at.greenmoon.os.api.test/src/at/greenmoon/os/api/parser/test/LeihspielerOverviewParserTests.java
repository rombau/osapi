package at.greenmoon.os.api.parser.test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import at.greenmoon.os.OnlineSoccer.AuthenticationException;
import at.greenmoon.os.OnlineSoccer.ParseException;
import at.greenmoon.os.api.test.fixtures.TestResource;
import at.greenmoon.os.model.IKaderspieler.Leihstatus;
import at.greenmoon.os.model.ISpieler.Position;
import at.greenmoon.os.model.ITeam;
import at.greenmoon.os.parser.LeihspielerOverviewParser;
import at.greenmoon.os.resource.IResource;

@SuppressWarnings("nls")
public class LeihspielerOverviewParserTests {

    private final LeihspielerOverviewParser parser = new LeihspielerOverviewParser();

    @Test(expected = AuthenticationException.class)
    public void testDemoTeam() throws Exception {

        // @formatter:off
        IResource document = new TestResource(
                "<div>Diese Seite ist ohne Team nicht verfügbar!</div>");
        // @formatter:on

        parser.parse(document, null);
    }

    @Test(expected = ParseException.class)
    public void testNoTable() throws Exception {

        IResource document = new TestResource("<div/>");

        parser.parse(document, null);
    }

    @Test(expected = ParseException.class)
    public void testNoTableWithColumns() throws Exception {

        IResource document = new TestResource("<table/>");

        parser.parse(document, null);
    }

    @Test
    public void testTeamWithOnePlayerVerliehen() throws Exception {

        // @formatter:off
        IResource document = new TestResource(
                "<b>Teamname - 1. Liga</b><a href=\"javascript:tabellenplatz(1)\"/>"
                        + "<table>"
                        + "<tr><td>Name</td><td>Alter</td><td>Land</td><td>U</td><td>Skillschnitt</td><td>Opt. Skill</td><td>Leihdauer</td><td>Gehalt</td><td>Leihgeb&uuml;hr</td><td>Leihclub</td></tr>"
                        + "<tr><td class=\"OMI\"><a href=\"javascript:spielerinfo(4711)\">Hugo</a></td><td>20</td><td>CRC</td><td>#</td><td>41.41</td><td>65.33</td><td>2</td><td>31.767</td><td>32.562</td><td><a href=\"javascript:teaminfo(1436)\">Ravne Razunif</a></td></tr>"
                        + "</table>"
                        + "<table>"
                        + "<tr><td>Name</td><td>Alter</td><td>Land</td><td>U</td><td>Skillschnitt</td><td>Opt. Skill</td><td>Leihdauer</td><td>Gehalt</td><td>Leihgeb&uuml;hr</td><td>Leihclub</td></tr>"
                        + "</table>");
        // @formatter:on

        ITeam team = parser.parse(document, null);

        assertEquals(1, team.getKaderspieler().size());
        assertEquals("Hugo", team.getKaderspieler().get(0).getName());
        assertEquals(4711, team.getKaderspieler().get(0).getId().longValue());
        assertEquals(Position.OMI, team.getKaderspieler().get(0).getPosition());
        assertEquals(Leihstatus.Verliehen, team.getKaderspieler().get(0).getLeihstatus());
    }

    @Test
    public void testTeamWithOnePlayerGeliehen() throws Exception {

        // @formatter:off
        IResource document = new TestResource(
                "<b>Teamname - 1. Liga</b><a href=\"javascript:tabellenplatz(1)\"/>"
                        + "<table>"
                        + "<tr><td>Name</td><td>Alter</td><td>Land</td><td>U</td><td>Skillschnitt</td><td>Opt. Skill</td><td>Leihdauer</td><td>Gehalt</td><td>Leihgeb&uuml;hr</td><td>Leihclub</td></tr>"
                        + "</table>"
                        + "<table>"
                        + "<tr><td>Name</td><td>Alter</td><td>Land</td><td>U</td><td>Skillschnitt</td><td>Opt. Skill</td><td>Leihdauer</td><td>Gehalt</td><td>Leihgeb&uuml;hr</td><td>Leihclub</td></tr>"
                        + "<tr><td class=\"OMI\"><a href=\"javascript:spielerinfo(4711)\">Hugo</a></td><td>20</td><td>CRC</td><td>#</td><td>41.41</td><td>65.33</td><td>2</td><td>31.767</td><td>32.562</td><td><a href=\"javascript:teaminfo(1436)\">Ravne Razunif</a></td></tr>"
                        + "</table>");
        // @formatter:on

        ITeam team = parser.parse(document, null);

        assertEquals(1, team.getKaderspieler().size());
        assertEquals("Hugo", team.getKaderspieler().get(0).getName());
        assertEquals(4711, team.getKaderspieler().get(0).getId().longValue());
        assertEquals(Position.OMI, team.getKaderspieler().get(0).getPosition());
        assertEquals(Leihstatus.Geliehen, team.getKaderspieler().get(0).getLeihstatus());
    }
}
