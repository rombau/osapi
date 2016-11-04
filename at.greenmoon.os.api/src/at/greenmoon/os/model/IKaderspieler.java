package at.greenmoon.os.model;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import at.greenmoon.os.model.complement.ContractComplementer;
import at.greenmoon.os.model.complement.IEntityComplementer.Complementer;
import at.greenmoon.os.model.complement.PositionComplementer;

public interface IKaderspieler extends ISpieler {

    public static final String POS_VERLIEHEN = "LEI"; //$NON-NLS-1$

    public enum Leihstatus {
        Verliehen, Geliehen
    }

    public enum Transferstatus {
        Angebot, Transfer, Unverkaeuflich
    }

    public enum Sperrenart {
        Liga, Pokal, International
    }

    Integer getNummer();

    void setNummer(Integer nummer);

    @Complementer(PositionComplementer.class)
    Position getPosition();

    void setPosition(Position position);

    String getName();

    void setName(String name);

    Integer getMoral();

    void setMoral(Integer moral);

    Integer getFitness();

    void setFitness(Integer fitness);

    Integer getVerletzungsdauer();

    void setVerletzungsdauer(Integer verletzungsdauer);

    Leihstatus getLeihstatus();

    void setLeihstatus(Leihstatus leihstatus);

    Transferstatus getTransferstatus();

    void setTransferstatus(Transferstatus transferstatus);

    Integer getTransfersperre();

    void setTransfersperre(Integer transfersperre);

    @Complementer(ContractComplementer.class)
    Integer getVertragslaufzeit();

    void setVertragslaufzeit(Integer vertragslaufzeit);

    @Complementer(ContractComplementer.class)
    Long getMonatsgehalt();

    void setMonatsgehalt(Long monatsgehalt);

    Map<Sperrenart, Integer> getSperren();

    void setSperren(Map<Sperrenart, Integer> sperren);

    BigDecimal getOpti();

    void setOpti(BigDecimal opti);

    List<Sonderskill> getSonderskills();

    void setSonderskills(List<Sonderskill> opti);

    Long getMarktwert();

    void setMarktwert(Long marktwert);

}