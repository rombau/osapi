package at.greenmoon.os.model.impl;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.List;
import java.util.Map;

import at.greenmoon.os.model.IKaderspieler;

public class Kaderspieler extends Spieler implements IKaderspieler {

    private String name;
    private Integer nummer;
    private Position position;

    private BigDecimal opti;

    private Integer moral;
    private Integer fitness;

    private Integer verletzungsdauer;

    private Leihstatus leihstatus;

    private Transferstatus transferstatus;
    private Integer transfersperre;

    private Integer vertragslaufzeit;
    private Long monatsgehalt;
    private Long marktwert;

    private List<Sonderskill> sonderskills;

    private Map<Sperrenart, Integer> sperren;

    Kaderspieler() {
        super();
    }

    @Override
    public String toString() {
        return MessageFormat.format("{0} ({1,number,#0})", name, id); //$NON-NLS-1$
    }

    @Override
    public Position getPosition() {
        return position;
    }

    @Override
    public void setPosition(Position position) {
        this.position = position;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public Integer getNummer() {
        return nummer;
    }

    @Override
    public void setNummer(Integer nummer) {
        this.nummer = nummer;
    }

    @Override
    public Integer getMoral() {
        return moral;
    }

    @Override
    public void setMoral(Integer moral) {
        this.moral = moral;
    }

    @Override
    public Integer getFitness() {
        return fitness;
    }

    @Override
    public void setFitness(Integer fitness) {
        this.fitness = fitness;
    }

    @Override
    public Integer getVerletzungsdauer() {
        return verletzungsdauer;
    }

    @Override
    public void setVerletzungsdauer(Integer verletzungsdauer) {
        this.verletzungsdauer = verletzungsdauer;
    }

    @Override
    public Leihstatus getLeihstatus() {
        return leihstatus;
    }

    @Override
    public void setLeihstatus(Leihstatus leihstatus) {
        this.leihstatus = leihstatus;
    }

    @Override
    public Transferstatus getTransferstatus() {
        return transferstatus;
    }

    @Override
    public void setTransferstatus(Transferstatus transferstatus) {
        this.transferstatus = transferstatus;
    }

    @Override
    public Integer getTransfersperre() {
        return transfersperre;
    }

    @Override
    public void setTransfersperre(Integer transfersperre) {
        this.transfersperre = transfersperre;
    }

    @Override
    public Integer getVertragslaufzeit() {
        return vertragslaufzeit;
    }

    @Override
    public void setVertragslaufzeit(Integer vertragslaufzeit) {
        this.vertragslaufzeit = vertragslaufzeit;
    }

    @Override
    public Long getMonatsgehalt() {
        return monatsgehalt;
    }

    @Override
    public void setMonatsgehalt(Long monatsgehalt) {
        this.monatsgehalt = monatsgehalt;
    }

    @Override
    public Map<Sperrenart, Integer> getSperren() {
        return sperren;
    }

    @Override
    public void setSperren(Map<Sperrenart, Integer> sperren) {
        this.sperren = sperren;
    }

    @Override
    public BigDecimal getOpti() {
        if (opti == null) {
            opti = getOpti(getPosition());
        }
        return opti;
    }

    @Override
    public void setOpti(BigDecimal opti) {
        this.opti = opti;
    }

    @Override
    public List<Sonderskill> getSonderskills() {
        if (sonderskills == null) {
            sonderskills = getSonderskills(getPosition());
        }
        return sonderskills;
    }

    @Override
    public void setSonderskills(List<Sonderskill> sonderskills) {
        this.sonderskills = sonderskills;
    }

    @Override
    public Long getMarktwert() {
        if (marktwert == null) {
            marktwert = getMarktwert(getPosition());
        }
        return marktwert;
    }

    @Override
    public void setMarktwert(Long marktwert) {
        this.marktwert = marktwert;
    }
}
