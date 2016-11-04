package at.greenmoon.os.api.model.test;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import at.greenmoon.os.model.IKaderspieler;
import at.greenmoon.os.model.ISpieler.Position;
import at.greenmoon.os.model.ISpieler.Skill;
import at.greenmoon.os.model.ISpieler.Sonderskill;
import at.greenmoon.os.model.impl.ModelProxyFactory;

@SuppressWarnings("nls")
public class SpielerTests {

    private IKaderspieler getTestSpieler(Position position, Integer[] skillValues) {

        IKaderspieler spieler = ModelProxyFactory.create(IKaderspieler.class, null);

        Map<Skill, Integer> skills = new HashMap<Skill, Integer>();
        for (int i = 0; i < Skill.values().length; i++) {
            Skill skill = Skill.values()[i];
            Integer value = skillValues[i];
            skills.put(skill, value);
        }

        spieler.setPosition(position);
        spieler.setSkills(skills);

        return spieler;
    }

    @Test
    public void testSkillschnitt() throws Exception {

        Integer[] skills = { 50, 80, 85, 85, 85, 85, 0, 58, 32, 60, 40, 63, 28, 34, 3, 80, 30 };
        IKaderspieler spieler = getTestSpieler(Position.TOR, skills);

        assertEquals("52.82", spieler.getSkillschnitt().toString());
    }

    @Test
    public void testOptiTOR() throws Exception {

        Integer[] skills = { 50, 80, 85, 85, 85, 85, 0, 58, 32, 60, 40, 63, 28, 34, 3, 80, 30 };
        IKaderspieler spieler = getTestSpieler(Position.TOR, skills);

        assertEquals("83.63", spieler.getOpti().toString());
    }

    @Test
    public void testOptiABW() throws Exception {

        Integer[] skills = { 51, 52, 77, 80, 78, 57, 0, 50, 47, 53, 51, 75, 39, 8, 37, 77, 35 };
        IKaderspieler spieler = getTestSpieler(Position.ABW, skills);

        assertEquals("78.33", spieler.getOpti().toString());
    }

    @Test
    public void testOptiDMI() throws Exception {

        Integer[] skills = { 55, 80, 61, 70, 80, 60, 19, 61, 55, 79, 52, 78, 40, 6, 66, 57, 53 };
        IKaderspieler spieler = getTestSpieler(Position.DMI, skills);

        assertEquals("82.96", spieler.getOpti().toString());
    }

    @Test
    public void testOptiMIT() throws Exception {

        Integer[] skills = { 65, 77, 56, 77, 60, 56, 2, 68, 50, 76, 57, 76, 42, 79, 4, 56, 15 };
        IKaderspieler spieler = getTestSpieler(Position.MIT, skills);

        assertEquals("79.26", spieler.getOpti().toString());
    }

    @Test
    public void testOptiOMI() throws Exception {

        Integer[] skills = { 67, 81, 28, 78, 15, 85, 1, 39, 20, 81, 50, 81, 4, 46, 14, 42, 42 };
        IKaderspieler spieler = getTestSpieler(Position.OMI, skills);

        assertEquals("77.26", spieler.getOpti().toString());
    }

    @Test
    public void testOptiSTU() throws Exception {

        Integer[] skills = { 83, 42, 76, 42, 19, 43, 0, 5, 25, 42, 20, 43, 76, 91, 14, 19, 71 };
        IKaderspieler spieler = getTestSpieler(Position.STU, skills);

        assertEquals("62.48", spieler.getOpti().toString());
    }

    @Test
    public void testAllSonderskillsTOR() throws Exception {

        Integer[] skills = { 75, 75, 75, 75, 75, 75, 75, 75, 75, 75, 75, 75, 75, 75, 75, 75, 75 };
        IKaderspieler spieler = getTestSpieler(Position.TOR, skills);

        assertEquals(2, spieler.getSonderskills().size());
        assertEquals(Sonderskill.Elferkiller, spieler.getSonderskills().get(0));
        assertEquals(Sonderskill.Kapitaen, spieler.getSonderskills().get(1));
    }

    @Test
    public void testAllSonderskillsABW() throws Exception {

        Integer[] skills = { 75, 75, 75, 75, 75, 75, 75, 75, 75, 75, 75, 75, 75, 75, 75, 75, 75 };
        IKaderspieler spieler = getTestSpieler(Position.ABW, skills);

        assertEquals(7, spieler.getSonderskills().size());
        assertEquals(Sonderskill.Libero, spieler.getSonderskills().get(0));
        assertEquals(Sonderskill.Spielmacher, spieler.getSonderskills().get(1));
        assertEquals(Sonderskill.FreistossSpezialist, spieler.getSonderskills().get(2));
        assertEquals(Sonderskill.Torinstinkt, spieler.getSonderskills().get(3));
        assertEquals(Sonderskill.Flankengott, spieler.getSonderskills().get(4));
        assertEquals(Sonderskill.Kapitaen, spieler.getSonderskills().get(5));
        assertEquals(Sonderskill.Pferdelunge, spieler.getSonderskills().get(6));
    }

    @Test
    public void testAllSonderskillsDMI() throws Exception {

        Integer[] skills = { 75, 75, 75, 75, 75, 75, 75, 75, 75, 75, 75, 75, 75, 75, 75, 75, 75 };
        IKaderspieler spieler = getTestSpieler(Position.DMI, skills);

        assertEquals(7, spieler.getSonderskills().size());
        assertEquals(Sonderskill.Libero, spieler.getSonderskills().get(0));
        assertEquals(Sonderskill.Spielmacher, spieler.getSonderskills().get(1));
        assertEquals(Sonderskill.FreistossSpezialist, spieler.getSonderskills().get(2));
        assertEquals(Sonderskill.Torinstinkt, spieler.getSonderskills().get(3));
        assertEquals(Sonderskill.Flankengott, spieler.getSonderskills().get(4));
        assertEquals(Sonderskill.Kapitaen, spieler.getSonderskills().get(5));
        assertEquals(Sonderskill.Pferdelunge, spieler.getSonderskills().get(6));
    }

    @Test
    public void testAllSonderskillsSTU() throws Exception {

        Integer[] skills = { 75, 75, 75, 75, 75, 75, 75, 75, 75, 75, 75, 75, 75, 75, 75, 75, 75 };
        IKaderspieler spieler = getTestSpieler(Position.STU, skills);

        assertEquals(6, spieler.getSonderskills().size());
        assertEquals(Sonderskill.Spielmacher, spieler.getSonderskills().get(0));
        assertEquals(Sonderskill.FreistossSpezialist, spieler.getSonderskills().get(1));
        assertEquals(Sonderskill.Torinstinkt, spieler.getSonderskills().get(2));
        assertEquals(Sonderskill.Flankengott, spieler.getSonderskills().get(3));
        assertEquals(Sonderskill.Kapitaen, spieler.getSonderskills().get(4));
        assertEquals(Sonderskill.Pferdelunge, spieler.getSonderskills().get(5));
    }

    @Test
    public void testMarktwert() throws Exception {

        Integer[] skills = { 34, 36, 70, 76, 75, 40, 0, 12, 31, 30, 45, 41, 20, 23, 87, 66, 50 };
        IKaderspieler spieler = getTestSpieler(Position.ABW, skills);
        spieler.setAlter(23);

        assertEquals(4383154, spieler.getMarktwert().longValue());
    }

    @Test
    public void testMarktwert2() throws Exception {

        Integer[] skills = { 25, 29, 71, 59, 64, 31, 0, 0, 37, 26, 31, 30, 47, 62, 26, 74, 0 };
        IKaderspieler spieler = getTestSpieler(Position.ABW, skills);
        spieler.setAlter(19);

        assertEquals(2081003, spieler.getMarktwert().longValue());
    }

    @Test
    public void testMarktwertMitSonderskill() throws Exception {

        Integer[] skills = { 50, 80, 85, 85, 85, 85, 0, 58, 32, 60, 40, 65, 28, 34, 3, 80, 30 };
        IKaderspieler spieler = getTestSpieler(Position.TOR, skills);
        spieler.setAlter(26);

        assertEquals(15983421, spieler.getMarktwert().longValue());
    }

    @Test
    public void testMarktwertMitSonderskills() throws Exception {

        Integer[] skills = { 75, 82, 61, 60, 52, 82, 2, 88, 44, 82, 54, 82, 62, 48, 60, 49, 1 };
        IKaderspieler spieler = getTestSpieler(Position.OMI, skills);
        spieler.setAlter(32);

        assertEquals(17466946, spieler.getMarktwert().longValue());
    }

}
