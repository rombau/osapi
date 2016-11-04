package at.greenmoon.os.api.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Map;

import org.junit.BeforeClass;
import org.junit.Test;

import at.greenmoon.os.OnlineSoccer;
import at.greenmoon.os.api.test.fixtures.FileSystemResourceProvider;
import at.greenmoon.os.model.IKaderspieler;
import at.greenmoon.os.model.ISpieler.Position;
import at.greenmoon.os.model.ISpieler.Skill;
import at.greenmoon.os.model.ITeam;
import at.greenmoon.os.model.IWappen;
import at.greenmoon.os.resource.ResourceProxyRegistry;

@SuppressWarnings("nls")
public class TeamTests {

    private static final OnlineSoccer OS = new OnlineSoccer(new FileSystemResourceProvider("manager@os.at", "geheim"));

    private static final Long ID_NEILL_MURPHY = 92292L;

    @BeforeClass
    public static void setUp() {
        ResourceProxyRegistry.clearCache();
    }

    @Test
    public void testGetTeam() throws Exception {

        ITeam team = OS.getTeam();

        assertNotNull(team);
        assertEquals(19, team.getId().intValue());
        assertEquals("FC Cork", team.getName());
        assertEquals(33, team.getKaderspieler().size());
        assertNotNull(team.getKaderspielerById(ID_NEILL_MURPHY));
    }

    @Test
    public void testGetTeamComplemntPosition() throws Exception {

        IKaderspieler spieler = OS.getTeam().getKaderspielerById(ID_NEILL_MURPHY);

        Position position = spieler.getPosition();

        assertNotNull(position);
        assertEquals(Position.OMI, position);
    }

    @Test
    public void testGetTeamComplemntContracts() throws Exception {

        IKaderspieler spieler = OS.getTeam().getKaderspielerById(ID_NEILL_MURPHY);

        Integer vertragslaufzeit = spieler.getVertragslaufzeit();

        assertNotNull(vertragslaufzeit);
        assertEquals(12, vertragslaufzeit.intValue());
    }

    @Test
    public void testGetTeamComplemntSkills() throws Exception {

        IKaderspieler spieler = OS.getTeam().getKaderspielerById(ID_NEILL_MURPHY);

        Map<Skill, Integer> skills = spieler.getSkills();

        assertNotNull(skills);
        assertEquals(25, skills.get(Skill.SCH).intValue());
    }

    @Test
    public void testGetTeamComplemntWappen() throws Exception {

        IWappen wappen = OS.getTeam().getWappen();

        byte[] bytes = wappen.getBytes();

        assertNotNull(bytes);
        assertEquals(12131, bytes.length);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetNullTeam() throws Exception {

        OS.getTeam(null);
    }

    @Test
    public void testGetAnyTeam() throws Exception {

        ITeam team = OS.getTeam(708);

        assertNotNull(team);
        assertEquals("Cork College", team.getName());
        assertEquals(36, team.getKaderspieler().size());
    }
}
