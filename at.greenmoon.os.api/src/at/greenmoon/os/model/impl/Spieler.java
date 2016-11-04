package at.greenmoon.os.model.impl;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import at.greenmoon.os.model.ISpieler;
import at.greenmoon.os.model.ITeam;
import at.greenmoon.os.model.impl.ModelProxyFactory.ResourceEntity;
import at.greenmoon.os.model.util.SkillUtil;

public abstract class Spieler extends ResourceEntity implements ISpieler {

    Spieler() {
        super();
    }

    protected Long id;

    protected ITeam team;

    protected Integer alter;

    protected String land;
    protected boolean uefa = true;

    protected Map<Skill, Integer> skills;
    private BigDecimal skillschnitt;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public ITeam getTeam() {
        return team;
    }

    @Override
    public void setTeam(ITeam team) {
        this.team = team;
    }

    @Override
    public String getLand() {
        return land;
    }

    @Override
    public void setLand(String land) {
        this.land = land;
    }

    @Override
    public boolean isUefa() {
        return uefa;
    }

    @Override
    public void setUefa(boolean uefa) {
        this.uefa = uefa;
    }

    @Override
    public Integer getAlter() {
        return alter;
    }

    @Override
    public void setAlter(Integer alter) {
        this.alter = alter;
    }

    @Override
    public Map<Skill, Integer> getSkills() {
        return skills;
    }

    @Override
    public void setSkills(Map<Skill, Integer> skills) {
        this.skills = skills;
    }

    @Override
    public BigDecimal getSkillschnitt() {
        if (skillschnitt == null) {
            Integer skillSumme = 0;
            for (Integer skillValue : getSkills().values()) {
                skillSumme += skillValue;
            }
            skillschnitt = new BigDecimal(skillSumme).divide(new BigDecimal(getSkills().size()), 2,
                    RoundingMode.HALF_UP);
        }
        return skillschnitt;
    }

    @Override
    public void setSkillschnitt(BigDecimal skillschnitt) {
        this.skillschnitt = skillschnitt;
    }

    @Override
    public BigDecimal getOpti(Position position) {

        Integer skillSumme = 0;

        for (Entry<Skill, Integer> skillEntry : getSkills().entrySet()) {
            skillSumme += skillEntry.getValue();
            if (SkillUtil.isPrimaerskill(position, skillEntry.getKey())) {
                skillSumme += (skillEntry.getValue() * 4);
            }
        }

        return new BigDecimal(skillSumme).divide(new BigDecimal(27), 2, RoundingMode.HALF_UP);
    }

    @Override
    public List<Sonderskill> getSonderskills(Position position) {

        return SkillUtil.getSonderskills(position, getSkills());
    }

    @Override
    public Long getMarktwert(Position position) {

        // (1+Durchschnitt/100)^10)*((1+Optiskill/100)^10)*(100-ALter/100)^10*(1,025^(Anzahl
        // der Sonderskill))*2

        BigDecimal exakterSkillschnitt = getSkillschnitt();
        BigDecimal exakterOptischnitt = getOpti(position);

        int sonderskills = getSonderskills(position).size();
        if (sonderskills > 0) {

            Integer skillSumme = 0;
            Integer skillSummeOpti = 0;
            for (Entry<Skill, Integer> skillEntry : getSkills().entrySet()) {
                skillSumme += skillEntry.getValue();
                skillSummeOpti += skillEntry.getValue();
                if (SkillUtil.isPrimaerskill(position, skillEntry.getKey())) {
                    skillSummeOpti += (skillEntry.getValue() * 4);
                }
            }
            exakterSkillschnitt = new BigDecimal(skillSumme).divide(new BigDecimal(getSkills().size()), 16,
                    RoundingMode.HALF_UP);
            exakterOptischnitt = new BigDecimal(skillSummeOpti).divide(new BigDecimal(27), 16, RoundingMode.HALF_UP);
        }

        MathContext mc = new MathContext(0);

        return exakterSkillschnitt.movePointLeft(2).add(BigDecimal.ONE).pow(10) //
                .multiply(exakterOptischnitt.movePointLeft(2).add(BigDecimal.ONE).pow(10)) //
                .multiply(new BigDecimal(100 - getAlter()).movePointLeft(2).add(BigDecimal.ONE).pow(10)) //
                .multiply(new BigDecimal(1.025).pow(getSonderskills(position).size())) //
                .multiply(new BigDecimal(2)).round(mc).longValue();
    }
}
