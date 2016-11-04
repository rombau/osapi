package at.greenmoon.os.model;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import at.greenmoon.os.model.complement.IEntityComplementer.Complementer;
import at.greenmoon.os.model.complement.SkillComplementer;
import at.greenmoon.os.resource.IResourceEntity;

public interface ISpieler extends IResourceEntity {

    public enum Position {
        TOR, ABW, DMI, MIT, OMI, STU
    }

    public enum Skill {
        SCH, BAK, KOB, ZWK, DEC, GES, FUQ, ERF, AGG, PAS, AUS, UEB, WID, SEL, DIS, ZUV, EIN
    }

    public enum Sonderskill {
        Elferkiller, Libero, Pferdelunge, Spielmacher, Flankengott, Torinstinkt, FreistossSpezialist, Kapitaen
    }

    Long getId();

    void setId(Long id);

    ITeam getTeam();

    void setTeam(ITeam team);

    String getLand();

    void setLand(String land);

    boolean isUefa();

    void setUefa(boolean uefa);

    Integer getAlter();

    void setAlter(Integer alter);

    @Complementer(SkillComplementer.class)
    Map<Skill, Integer> getSkills();

    void setSkills(Map<Skill, Integer> skills);

    BigDecimal getSkillschnitt();

    void setSkillschnitt(BigDecimal skillschnitt);

    List<Sonderskill> getSonderskills(Position position);

    BigDecimal getOpti(Position position);

    Long getMarktwert(Position position);
}