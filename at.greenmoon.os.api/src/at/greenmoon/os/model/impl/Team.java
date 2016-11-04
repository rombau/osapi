package at.greenmoon.os.model.impl;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.helper.Validate;

import at.greenmoon.os.model.IWappen;
import at.greenmoon.os.model.IKaderspieler;
import at.greenmoon.os.model.ITeam;
import at.greenmoon.os.model.impl.ModelProxyFactory.ResourceEntity;

public class Team extends ResourceEntity implements ITeam {

    private Integer id;
    private String name;

    private IWappen wappen;

    private Liga liga;

    private List<IKaderspieler> kaderspieler;

    private List<Spieltag> saisonplan;

    Team() {
        super();
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
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
    public Liga getLiga() {
        return liga;
    }

    @Override
    public void setLeague(Liga league) {
        this.liga = league;
    }

    @Override
    public List<IKaderspieler> getKaderspieler() {
        if (kaderspieler == null) {
            kaderspieler = new ArrayList<IKaderspieler>();
        }
        return kaderspieler;
    }

    @Override
    public void setKaderspieler(List<IKaderspieler> kaderspieler) {
        this.kaderspieler = kaderspieler;
    }

    @Override
    public List<Spieltag> getSaisonplan() {
        if (saisonplan == null) {
            saisonplan = new ArrayList<Spieltag>();
        }
        return saisonplan;
    }

    @Override
    public void setSaisonplan(List<Spieltag> saisonplan) {
        this.saisonplan = saisonplan;
    }

    @Override
    public IKaderspieler getKaderspielerById(Long id) {
        Validate.notNull(id);
        for (IKaderspieler spieler : getKaderspieler()) {
            if (id.equals(spieler.getId())) {
                return spieler;
            }
        }
        if (getResource() == null) {
            return null;
        }
        IKaderspieler spieler = ModelProxyFactory.create(IKaderspieler.class, getResource());
        spieler.setTeam(this);
        getKaderspieler().add(spieler);
        return spieler;
    }

    @Override
    public IWappen getWappen() {
        return wappen;
    }

    @Override
    public void setWappen(IWappen wappen) {
        this.wappen = wappen;
    }
}
