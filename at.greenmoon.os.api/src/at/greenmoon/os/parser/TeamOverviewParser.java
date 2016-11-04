package at.greenmoon.os.parser;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import at.greenmoon.os.OnlineSoccer.AuthenticationException;
import at.greenmoon.os.OnlineSoccer.ParseException;
import at.greenmoon.os.model.IKaderspieler;
import at.greenmoon.os.model.IKaderspieler.Leihstatus;
import at.greenmoon.os.model.IKaderspieler.Sperrenart;
import at.greenmoon.os.model.IKaderspieler.Transferstatus;
import at.greenmoon.os.model.ISpieler.Position;
import at.greenmoon.os.model.ISpieler.Sonderskill;
import at.greenmoon.os.model.ITeam;
import at.greenmoon.os.model.impl.ModelProxyFactory;
import at.greenmoon.os.parser.util.LinkExtractor;
import at.greenmoon.os.parser.util.TableExtractor;
import at.greenmoon.os.parser.util.TableExtractor.IColumn;
import at.greenmoon.os.parser.util.TableExtractor.IRow;
import at.greenmoon.os.parser.util.TableExtractor.ITable;
import at.greenmoon.os.parser.util.TableExtractor.Optional;
import at.greenmoon.os.parser.util.TableExtractor.Title;
import at.greenmoon.os.resource.IResource;
import at.greenmoon.os.resource.IResourceParser;

public class TeamOverviewParser extends AbstractTeamParser implements IResourceParser<ITeam> {

    private enum Column implements IColumn {
        @Title("Nr.") Nr, Name, Alter, Pos, @Title("") Flagge, Land, U, //
        @Optional MOR, @Optional FIT, Skillschnitt, @Title("Opt.Skill") Opti, //
        S, Sperre, @Title("Verl.") Verletzung, T, TS;
    }

    @Override
    public ITeam parse(IResource resource, ITeam team) throws ParseException, AuthenticationException {

        Document jsoupDocument = Jsoup.parse(resource.getContent());

        checkDemoteam(jsoupDocument);

        if (team == null) {
            team = ModelProxyFactory.create(ITeam.class, resource);
        }

        team.setId(extractTeamId(jsoupDocument));
        team.setName(extractTeamName(jsoupDocument));
        team.setWappen(extractTeamWappen(jsoupDocument, resource));

        ITable table = TableExtractor.extract(jsoupDocument, Column.values(), true, this).get(0);
        for (IRow row : table.getRows()) {

            Long id = LinkExtractor.extractId(row.getCell(Column.Name));
            IKaderspieler spieler = team.getKaderspielerById(id);

            spieler.setId(id);
            spieler.setTeam(team);
            spieler.setName(row.getCell(Column.Name).text());

            spieler.setAlter(Integer.valueOf(row.getCell(Column.Alter).text()));
            spieler.setNummer(Integer.valueOf(row.getCell(Column.Nr).text()));

            if (!IKaderspieler.POS_VERLIEHEN.equals(row.getCell(Column.Pos).text())) {
                spieler.setPosition(Position.valueOf(row.getCell(Column.Pos).text()));
            }

            spieler.setLand(row.getCell(Column.Land).text());
            spieler.setUefa(!row.getCell(Column.U).text().isEmpty());

            if (row.getCell(Column.MOR) != null) {
                spieler.setMoral(Integer.valueOf(row.getCell(Column.MOR).text()));
            }
            if (row.getCell(Column.FIT) != null) {
                spieler.setFitness(Integer.valueOf(row.getCell(Column.FIT).text()));
            }

            spieler.setSkillschnitt(new BigDecimal(row.getCell(Column.Skillschnitt).text()));
            spieler.setOpti(new BigDecimal(row.getCell(Column.Opti).text()));

            spieler.setSonderskills(extractSonderskills(row.getCell(Column.S).text()));

            spieler.setSperren(extractSperren(row.getCell(Column.Sperre).text()));

            if (!row.getCell(Column.Verletzung).text().isEmpty()) {
                Integer verletzungsdauer = Integer.valueOf(row.getCell(Column.Verletzung).text());
                if (verletzungsdauer > 0) {
                    spieler.setVerletzungsdauer(verletzungsdauer);
                }
            }

            spieler.setTransferstatus(extractTransferstatus(row.getCell(Column.T).text()));
            spieler.setTransfersperre(extractTransfersperre(row.getCell(Column.TS).text()));

            spieler.setLeihstatus(extractLeihstatus(row.getCell(Column.TS).text(), row.getCell(Column.Pos).text()));
        }

        return team;
    }

    public List<Sonderskill> extractSonderskills(String sonderskillsText) {

        List<Sonderskill> sonderskills = new ArrayList<Sonderskill>();

        if (!sonderskillsText.isEmpty()) {
            for (int i = 0; i < sonderskillsText.toCharArray().length; i++) {
                char sonderskill = sonderskillsText.toCharArray()[i];
                if (sonderskill == 'E') {
                    sonderskills.add(Sonderskill.Elferkiller);
                }
                else if (sonderskill == 'L') {
                    sonderskills.add(Sonderskill.Libero);
                }
                else if (sonderskill == 'S') {
                    sonderskills.add(Sonderskill.Spielmacher);
                }
                else if (sonderskill == 'F') {
                    sonderskills.add(Sonderskill.FreistossSpezialist);
                }
                else if (sonderskill == 'T') {
                    sonderskills.add(Sonderskill.Torinstinkt);
                }
                else if (sonderskill == 'G') {
                    sonderskills.add(Sonderskill.Flankengott);
                }
                else if (sonderskill == 'K') {
                    sonderskills.add(Sonderskill.Kapitaen);
                }
                else if (sonderskill == 'P') {
                    sonderskills.add(Sonderskill.Pferdelunge);
                }
            }
        }

        return sonderskills;
    }

    @SuppressWarnings("nls")
    public Map<Sperrenart, Integer> extractSperren(String sperrenText) {

        Map<Sperrenart, Integer> sperren = new HashMap<Sperrenart, Integer>();

        if (!sperrenText.isEmpty()) {
            String[] sperrenTextArray = sperrenText.split("\\s");
            for (String sperre : sperrenTextArray) {
                Sperrenart art = null;
                if (sperre.contains("L")) {
                    art = Sperrenart.Liga;
                }
                else if (sperre.contains("P")) {
                    art = Sperrenart.Pokal;
                }
                else if (sperre.contains("I")) {
                    art = Sperrenart.International;
                }
                if (art != null) {
                    sperre = sperre.replaceAll("\\D*", "");
                    sperren.put(art, Integer.valueOf(sperre));
                }
            }
        }

        return sperren;
    }

    @SuppressWarnings("nls")
    public Transferstatus extractTransferstatus(String transferstatusText) {

        if (transferstatusText.isEmpty()) {
            return null;
        }

        if ("A".equals(transferstatusText)) {
            return Transferstatus.Angebot;
        }
        else if ("T".equals(transferstatusText)) {
            return Transferstatus.Transfer;
        }
        else if ("U".equals(transferstatusText)) {
            return Transferstatus.Unverkaeuflich;
        }

        return null;
    }

    @SuppressWarnings("nls")
    public Integer extractTransfersperre(String transfersperreText) {

        if (transfersperreText.isEmpty()) {
            return null;
        }

        Integer transfersperre = Integer.valueOf(transfersperreText.replaceAll("\\D*", ""));
        return transfersperre > 0 ? transfersperre : null;
    }

    public Leihstatus extractLeihstatus(String transfersperreText, String position) {

        if (transfersperreText.isEmpty() || transfersperreText.charAt(0) != 'L') {
            return null;
        }

        if (IKaderspieler.POS_VERLIEHEN.equals(position)) {
            return Leihstatus.Verliehen;
        }
        return Leihstatus.Geliehen;
    }

}
