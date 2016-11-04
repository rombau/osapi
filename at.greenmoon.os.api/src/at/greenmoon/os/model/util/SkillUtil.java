package at.greenmoon.os.model.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import at.greenmoon.os.model.ISpieler.Position;
import at.greenmoon.os.model.ISpieler.Skill;
import at.greenmoon.os.model.ISpieler.Sonderskill;

public abstract class SkillUtil {

    private static final int SONDERSKILL_GRENZE = 75;

    public static boolean isPrimaerskill(Position position, Skill skill) {

        switch (position) {
        case TOR:
            return (skill.equals(Skill.KOB) || skill.equals(Skill.ZWK) //
                    || skill.equals(Skill.DEC) || skill.equals(Skill.GES));
        case ABW:
            return (skill.equals(Skill.KOB) || skill.equals(Skill.ZWK) //
                    || skill.equals(Skill.DEC) || skill.equals(Skill.ZUV));
        case DMI:
            return (skill.equals(Skill.BAK) || skill.equals(Skill.PAS) //
                    || skill.equals(Skill.UEB) || skill.equals(Skill.DEC));
        case MIT:
            return (skill.equals(Skill.BAK) || skill.equals(Skill.PAS) //
                    || skill.equals(Skill.UEB) || skill.equals(Skill.ZWK));
        case OMI:
            return (skill.equals(Skill.BAK) || skill.equals(Skill.PAS) //
                    || skill.equals(Skill.UEB) || skill.equals(Skill.GES));
        case STU:
            return (skill.equals(Skill.SCH) || skill.equals(Skill.KOB) //
                    || skill.equals(Skill.ZWK) || skill.equals(Skill.GES));
        }
        throw new IllegalStateException("Unknown position."); //$NON-NLS-1$
    }

    public static boolean isUnveraenderlich(Skill skill) {

        return (skill.equals(Skill.WID) || skill.equals(Skill.SEL) //
                || skill.equals(Skill.DIS) || skill.equals(Skill.EIN));
    }

    public static List<Sonderskill> getSonderskills(Position position, Map<Skill, Integer> skills) {

        List<Sonderskill> sonderskills = new ArrayList<Sonderskill>();

        if (position.equals(Position.TOR) //
                && skills.get(Skill.KOB) >= SONDERSKILL_GRENZE
                && skills.get(Skill.DEC) >= SONDERSKILL_GRENZE
                && skills.get(Skill.GES) >= SONDERSKILL_GRENZE) {
            sonderskills.add(Sonderskill.Elferkiller);
        }
        if ((position.equals(Position.DMI) || position.equals(Position.ABW))
                && skills.get(Skill.DEC) >= SONDERSKILL_GRENZE && skills.get(Skill.UEB) >= SONDERSKILL_GRENZE
                && skills.get(Skill.ZWK) >= SONDERSKILL_GRENZE) {
            sonderskills.add(Sonderskill.Libero);
        }
        if (!position.equals(Position.TOR) //
                && skills.get(Skill.UEB) >= SONDERSKILL_GRENZE
                && skills.get(Skill.PAS) >= SONDERSKILL_GRENZE
                && skills.get(Skill.BAK) >= SONDERSKILL_GRENZE) {
            sonderskills.add(Sonderskill.Spielmacher);
        }
        if (!position.equals(Position.TOR) //
                && skills.get(Skill.SCH) >= SONDERSKILL_GRENZE
                && skills.get(Skill.UEB) >= SONDERSKILL_GRENZE
                && skills.get(Skill.BAK) >= SONDERSKILL_GRENZE) {
            sonderskills.add(Sonderskill.FreistossSpezialist);
        }
        if (!position.equals(Position.TOR) //
                && skills.get(Skill.SCH) >= SONDERSKILL_GRENZE
                && skills.get(Skill.KOB) >= SONDERSKILL_GRENZE
                && skills.get(Skill.GES) >= SONDERSKILL_GRENZE) {
            sonderskills.add(Sonderskill.Torinstinkt);
        }
        if (!position.equals(Position.TOR) //
                && skills.get(Skill.BAK) >= SONDERSKILL_GRENZE
                && skills.get(Skill.PAS) >= SONDERSKILL_GRENZE
                && skills.get(Skill.GES) >= SONDERSKILL_GRENZE) {
            sonderskills.add(Sonderskill.Flankengott);
        }
        if (skills.get(Skill.FUQ) >= SONDERSKILL_GRENZE && skills.get(Skill.ERF) >= SONDERSKILL_GRENZE
                && skills.get(Skill.EIN) >= SONDERSKILL_GRENZE) {
            sonderskills.add(Sonderskill.Kapitaen);
        }
        if (!position.equals(Position.TOR) //
                && skills.get(Skill.AUS) >= SONDERSKILL_GRENZE
                && skills.get(Skill.GES) >= SONDERSKILL_GRENZE
                && skills.get(Skill.ZUV) >= SONDERSKILL_GRENZE) {
            sonderskills.add(Sonderskill.Pferdelunge);
        }

        return sonderskills;
    }
}
