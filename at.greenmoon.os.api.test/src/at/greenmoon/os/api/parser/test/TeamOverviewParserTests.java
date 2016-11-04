package at.greenmoon.os.api.parser.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;

import java.util.List;
import java.util.Map;

import org.junit.Test;

import at.greenmoon.os.OnlineSoccer.AuthenticationException;
import at.greenmoon.os.OnlineSoccer.ParseException;
import at.greenmoon.os.api.test.fixtures.TestResource;
import at.greenmoon.os.model.IKaderspieler.Leihstatus;
import at.greenmoon.os.model.IKaderspieler.Sperrenart;
import at.greenmoon.os.model.IKaderspieler.Transferstatus;
import at.greenmoon.os.model.ISpieler.Sonderskill;
import at.greenmoon.os.model.ITeam;
import at.greenmoon.os.model.impl.ModelProxyFactory;
import at.greenmoon.os.parser.TeamOverviewParser;
import at.greenmoon.os.resource.IResource;

@SuppressWarnings("nls")
public class TeamOverviewParserTests {

    private final TeamOverviewParser parser = new TeamOverviewParser();

    @Test(expected = AuthenticationException.class)
    public void testDemoTeam() throws Exception {

        // @formatter:off
        IResource document = new TestResource(
                "<img width=\"75\" heigth=\"75\" src=\"images/wappen/00000019.png\">"
                        + "<b>DemoTeam - </b><a href=\"javascript:tabellenplatz(0)\"/>"
                        + "<table>"
                        + "<tr><td>#</td><td>Nr.</td><td>Name</td><td>Alter</td><td>Pos</td><td></td><td>Land</td><td>U</td><td>MOR</td><td>FIT</td><td>Skillschnitt</td><td>Opt.Skill</td><td>S</td><td>Sperre</td><td>Verl.</td><td>T</td><td>TS</td></tr>"
                        + "<tr><td>1</td><td>1</td><td><a onClick=\"spielerinfo(4711);return false;\" href=\"sp.php?s=4711\">Hugo</a></td><td>20</td><td>TOR</td><td>Flagge</td><td>IRL</td><td></td><td>99</td><td>99</td><td>49.15</td><td>78.65</td><td>E</td><td></td><td></td><td></td><td></td></tr>"
                        + "<tr><td>#</td><td>Nr.</td><td>Name</td><td>Alter</td><td>Pos</td><td></td><td>Land</td><td>U</td><td>MOR</td><td>FIT</td><td>Skillschnitt</td><td>Opt.Skill</td><td>S</td><td>Sperre</td><td>Verl.</td><td>T</td><td>TS</td></tr>"
                        + "</table>");
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
                "<img width=\"75\" heigth=\"75\" src=\"images/wappen/00000019.png\">"
                        + "<b>Bindestrich und Liga fehlen</b><a href=\"javascript:tabellenplatz(1)\"/>"
                        + "<table>"
                        + "<tr><td>#</td><td>Nr.</td><td>Name</td><td>Alter</td><td>Pos</td><td></td><td>Land</td><td>U</td><td>MOR</td><td>FIT</td><td>Skillschnitt</td><td>Opt.Skill</td><td>S</td><td>Sperre</td><td>Verl.</td><td>T</td><td>TS</td></tr>"
                        + "<tr><td>1</td><td>1</td><td><a onClick=\"spielerinfo(4711);return false;\" href=\"sp.php?s=4711\">Hugo</a></td><td>20</td><td>TOR</td><td>Flagge</td><td>IRL</td><td></td><td>99</td><td>99</td><td>49.15</td><td>78.65</td><td>E</td><td></td><td></td><td></td><td></td></tr>"
                        + "<tr><td>#</td><td>Nr.</td><td>Name</td><td>Alter</td><td>Pos</td><td></td><td>Land</td><td>U</td><td>MOR</td><td>FIT</td><td>Skillschnitt</td><td>Opt.Skill</td><td>S</td><td>Sperre</td><td>Verl.</td><td>T</td><td>TS</td></tr>"
                        + "</table>");
        // @formatter:on

        parser.parse(document, null);
    }

    @Test(expected = ParseException.class)
    public void testNoTeamId() throws Exception {

        // @formatter:off
        IResource document = new TestResource(
                "<img width=\"75\" heigth=\"75\" src=\"images/wappen/00000019.png\">"
                        + "<b>Teamname - 1. Liga</b>"
                        + "<table>"
                        + "<tr><td>#</td><td>Nr.</td><td>Name</td><td>Alter</td><td>Pos</td><td></td><td>Land</td><td>U</td><td>MOR</td><td>FIT</td><td>Skillschnitt</td><td>Opt.Skill</td><td>S</td><td>Sperre</td><td>Verl.</td><td>T</td><td>TS</td></tr>"
                        + "<tr><td>1</td><td>1</td><td><a onClick=\"spielerinfo(4711);return false;\" href=\"sp.php?s=4711\">Hugo</a></td><td>20</td><td>TOR</td><td>Flagge</td><td>IRL</td><td></td><td>99</td><td>99</td><td>49.15</td><td>78.65</td><td>E</td><td></td><td></td><td></td><td></td></tr>"
                        + "<tr><td>#</td><td>Nr.</td><td>Name</td><td>Alter</td><td>Pos</td><td></td><td>Land</td><td>U</td><td>MOR</td><td>FIT</td><td>Skillschnitt</td><td>Opt.Skill</td><td>S</td><td>Sperre</td><td>Verl.</td><td>T</td><td>TS</td></tr>"
                        + "</table>");
        // @formatter:on

        parser.parse(document, null);
    }

    @Test(expected = ParseException.class)
    public void testNoWappen() throws Exception {

        // @formatter:off
        IResource document = new TestResource(
                "<b>FC Cork - 2. Liga A<a>Irland</a></b><a href=\"javascript:tabellenplatz(1)\"/>"
                        + "<table>"
                        + "<tr><td>#</td><td>Nr.</td><td>Name</td><td>Alter</td><td>Pos</td><td></td><td>Land</td><td>U</td><td>MOR</td><td>FIT</td><td>Skillschnitt</td><td>Opt.Skill</td><td>S</td><td>Sperre</td><td>Verl.</td><td>T</td><td>TS</td></tr>"
                        + "<tr><td>1</td><td>1</td><td><a onClick=\"spielerinfo(4711);return false;\" href=\"sp.php?s=4711\">Hugo</a></td><td>20</td><td>TOR</td><td>Flagge</td><td>IRL</td><td></td><td>99</td><td>99</td><td>49.15</td><td>78.65</td><td>E</td><td></td><td></td><td></td><td></td></tr>"
                        + "<tr><td>#</td><td>Nr.</td><td>Name</td><td>Alter</td><td>Pos</td><td></td><td>Land</td><td>U</td><td>MOR</td><td>FIT</td><td>Skillschnitt</td><td>Opt.Skill</td><td>S</td><td>Sperre</td><td>Verl.</td><td>T</td><td>TS</td></tr>"
                        + "</table>");
        // @formatter:on

        parser.parse(document, null);
    }

    @Test(expected = ParseException.class)
    public void testNoTable() throws Exception {

        IResource document = new TestResource("<img width=\"75\" heigth=\"75\" src=\"images/wappen/00000019.png\">"
                + "<b>FC Cork - 2. Liga A<a>Irland</a></b><a href=\"javascript:tabellenplatz(1)\"/>");

        parser.parse(document, null);
    }

    @Test
    public void testParseTeamIdToInitializedTeam() throws Exception {

        // @formatter:off
        IResource document = new TestResource(
                "<img width=\"75\" heigth=\"75\" src=\"images/wappen/00000019.png\">"
                        + "<b>Teamname - 1. Liga</b><a href=\"javascript:tabellenplatz(1)\"/>"
                        + "<table>"
                        + "<tr><td>#</td><td>Nr.</td><td>Name</td><td>Alter</td><td>Pos</td><td></td><td>Land</td><td>U</td><td>MOR</td><td>FIT</td><td>Skillschnitt</td><td>Opt.Skill</td><td>S</td><td>Sperre</td><td>Verl.</td><td>T</td><td>TS</td></tr>"
                        + "<tr><td>1</td><td>1</td><td><a onClick=\"spielerinfo(4711);return false;\" href=\"sp.php?s=4711\">Hugo</a></td><td>20</td><td>TOR</td><td>Flagge</td><td>IRL</td><td></td><td>99</td><td>99</td><td>49.15</td><td>78.65</td><td>E</td><td></td><td></td><td></td><td></td></tr>"
                        + "<tr><td>#</td><td>Nr.</td><td>Name</td><td>Alter</td><td>Pos</td><td></td><td>Land</td><td>U</td><td>MOR</td><td>FIT</td><td>Skillschnitt</td><td>Opt.Skill</td><td>S</td><td>Sperre</td><td>Verl.</td><td>T</td><td>TS</td></tr>"
                        + "</table>");
        // @formatter:on
        ITeam team = ModelProxyFactory.create(ITeam.class, document);
        team.setId(0);

        team = parser.parse(document, team);

        assertEquals(1, team.getId().intValue());
    }

    @Test
    public void testPublicTeamWithOnePlayer() throws Exception {

        // @formatter:off
        IResource document = new TestResource(
                "<img width=\"75\" heigth=\"75\" src=\"images/wappen/00000019.png\">"
                        + "<b>Teamname - 1. Liga</b><a href=\"javascript:tabellenplatz(1)\"/>"
                        + "<table>"
                        + "<tr><td>#</td><td>Nr.</td><td>Name</td><td>Alter</td><td>Pos</td><td></td><td>Land</td><td>U</td><td>Skillschnitt</td><td>Opt.Skill</td><td>S</td><td>Sperre</td><td>Verl.</td><td>T</td><td>TS</td></tr>"
                        + "<tr><td>1</td><td>1</td><td><a onClick=\"spielerinfo(4711);return false;\" href=\"sp.php?s=4711\">Hugo</a></td><td>20</td><td>TOR</td><td>Flagge</td><td>IRL</td><td></td><td>49.15</td><td>78.65</td><td>E</td><td></td><td></td><td></td><td></td></tr>"
                        + "<tr><td>#</td><td>Nr.</td><td>Name</td><td>Alter</td><td>Pos</td><td></td><td>Land</td><td>U</td><td>Skillschnitt</td><td>Opt.Skill</td><td>S</td><td>Sperre</td><td>Verl.</td><td>T</td><td>TS</td></tr>"
                        + "</table>");
        // @formatter:on

        ITeam team = parser.parse(document, null);

        assertEquals(1, team.getId().intValue());
        assertNull(team.getKaderspieler().get(0).getMoral());
        assertNull(team.getKaderspieler().get(0).getFitness());
    }

    @Test
    public void testTeamWithOnePlayer() throws Exception {

        // @formatter:off
        IResource document = new TestResource(
                "<img width=\"75\" heigth=\"75\" src=\"images/wappen/00000019.png\">"
                        + "<b>Teamname - 1. Liga</b><a href=\"javascript:tabellenplatz(1)\"/>"
                        + "<table>"
                        + "<tr><td>#</td><td>Nr.</td><td>Name</td><td>Alter</td><td>Pos</td><td></td><td>Land</td><td>U</td><td>MOR</td><td>FIT</td><td>Skillschnitt</td><td>Opt.Skill</td><td>S</td><td>Sperre</td><td>Verl.</td><td>T</td><td>TS</td></tr>"
                        + "<tr><td>1</td><td>1</td><td><a onClick=\"spielerinfo(4711);return false;\" href=\"sp.php?s=4711\">Hugo</a></td><td>20</td><td>TOR</td><td>Flagge</td><td>IRL</td><td></td><td>99</td><td>99</td><td>49.15</td><td>78.65</td><td></td><td>0</td><td>0</td><td>N</td><td>0</td></tr>"
                        + "<tr><td>#</td><td>Nr.</td><td>Name</td><td>Alter</td><td>Pos</td><td></td><td>Land</td><td>U</td><td>MOR</td><td>FIT</td><td>Skillschnitt</td><td>Opt.Skill</td><td>S</td><td>Sperre</td><td>Verl.</td><td>T</td><td>TS</td></tr>"
                        + "</table>");
        // @formatter:on

        ITeam team = parser.parse(document, null);

        assertEquals(1, team.getId().intValue());
        assertEquals("Teamname", team.getName());
        assertEquals(1, team.getKaderspieler().size());
        assertEquals("Hugo", team.getKaderspieler().get(0).getName());
        assertEquals(4711, team.getKaderspieler().get(0).getId().longValue());

        assertEquals(20, team.getKaderspieler().get(0).getAlter().intValue());
        assertEquals(1, team.getKaderspieler().get(0).getNummer().intValue());
        assertEquals("TOR", team.getKaderspieler().get(0).getPosition().name());
        assertEquals("IRL", team.getKaderspieler().get(0).getLand());
        assertFalse(team.getKaderspieler().get(0).isUefa());
        assertEquals(99, team.getKaderspieler().get(0).getMoral().intValue());
        assertEquals(99, team.getKaderspieler().get(0).getFitness().intValue());
        assertEquals("49.15", team.getKaderspieler().get(0).getSkillschnitt().toString());
        assertEquals("78.65", team.getKaderspieler().get(0).getOpti().toString());
        assertEquals(0, team.getKaderspieler().get(0).getSonderskills().size());
        assertEquals(0, team.getKaderspieler().get(0).getSperren().size());
        assertNull(team.getKaderspieler().get(0).getVerletzungsdauer());
        assertNull(team.getKaderspieler().get(0).getTransferstatus());
        assertNull(team.getKaderspieler().get(0).getTransfersperre());
        assertNull(team.getKaderspieler().get(0).getLeihstatus());
    }

    @Test
    public void testAllSonderskills() throws Exception {

        List<Sonderskill> sonderskills = parser.extractSonderskills("ELSFTGKP");

        assertEquals(8, sonderskills.size());
        assertEquals(Sonderskill.Elferkiller, sonderskills.get(0));
        assertEquals(Sonderskill.Libero, sonderskills.get(1));
        assertEquals(Sonderskill.Spielmacher, sonderskills.get(2));
        assertEquals(Sonderskill.FreistossSpezialist, sonderskills.get(3));
        assertEquals(Sonderskill.Torinstinkt, sonderskills.get(4));
        assertEquals(Sonderskill.Flankengott, sonderskills.get(5));
        assertEquals(Sonderskill.Kapitaen, sonderskills.get(6));
        assertEquals(Sonderskill.Pferdelunge, sonderskills.get(7));
    }

    @Test
    public void testAllSperren() throws Exception {

        Map<Sperrenart, Integer> sperren = parser.extractSperren("1L 22P 33I");

        assertEquals(3, sperren.size());
        assertEquals(1, sperren.get(Sperrenart.Liga).intValue());
        assertEquals(22, sperren.get(Sperrenart.Pokal).intValue());
        assertEquals(33, sperren.get(Sperrenart.International).intValue());
    }

    @Test
    public void testLeihstatusVerliehen() throws Exception {

        assertEquals(Leihstatus.Verliehen, parser.extractLeihstatus("L47", "LEI"));
    }

    @Test
    public void testLeihstatusGeliehen() throws Exception {

        assertEquals(Leihstatus.Geliehen, parser.extractLeihstatus("L47", "ABW"));
    }

    @Test
    public void testTransferstatusAngebot() throws Exception {

        assertEquals(Transferstatus.Angebot, parser.extractTransferstatus("A"));
    }

    @Test
    public void testTransferstatusTransfer() throws Exception {

        assertEquals(Transferstatus.Transfer, parser.extractTransferstatus("T"));
    }

    @Test
    public void testTransferstatusUnverkaeuflich() throws Exception {

        assertEquals(Transferstatus.Unverkaeuflich, parser.extractTransferstatus("U"));
    }

    @Test
    public void testTransfersperre() throws Exception {

        assertEquals(40, parser.extractTransfersperre("40").intValue());
    }

    @Test
    public void testTransfersperreLeihe() throws Exception {

        assertEquals(47, parser.extractTransfersperre("L47").intValue());
    }

}
