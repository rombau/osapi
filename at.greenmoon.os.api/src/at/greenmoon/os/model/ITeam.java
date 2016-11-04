package at.greenmoon.os.model;

import java.util.List;

import at.greenmoon.os.model.impl.Liga;
import at.greenmoon.os.model.impl.Spieltag;
import at.greenmoon.os.resource.IResourceEntity;

public interface ITeam extends IResourceEntity {

    Integer getId();

    void setId(Integer id);

    String getName();

    void setName(String name);

    Liga getLiga();

    void setLeague(Liga league);

    List<IKaderspieler> getKaderspieler();

    void setKaderspieler(List<IKaderspieler> kaderspieler);

    List<Spieltag> getSaisonplan();

    void setSaisonplan(List<Spieltag> saisonplan);

    IWappen getWappen();

    void setWappen(IWappen wappen);

    IKaderspieler getKaderspielerById(Long id);

}