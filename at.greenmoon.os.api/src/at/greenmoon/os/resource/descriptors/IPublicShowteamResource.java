package at.greenmoon.os.resource.descriptors;

import java.io.IOException;
import java.util.List;

import at.greenmoon.os.OnlineSoccer.OfflineException;
import at.greenmoon.os.OnlineSoccer.ParseException;
import at.greenmoon.os.model.ITeam;
import at.greenmoon.os.model.impl.Spieltag;
import at.greenmoon.os.parser.TeamContractsParser;
import at.greenmoon.os.parser.TeamOverviewParser;
import at.greenmoon.os.parser.TeamSaisonParser;
import at.greenmoon.os.parser.TeamSkillsParser;
import at.greenmoon.os.resource.IWebResourceDescriptor;
import at.greenmoon.os.resource.IWebResourceDescriptor.Path;

@Path(IPublicShowteamResource.PATH)
public interface IPublicShowteamResource extends IWebResourceDescriptor {

    public static final String PATH = "st.php"; //$NON-NLS-1$

    public static final String TEAM_ID_KEY = "c"; //$NON-NLS-1$

    @Parser(TeamOverviewParser.class)
    ITeam getTeamOverview(@Param(key = TEAM_ID_KEY) Integer teamId, @Original ITeam original) throws IOException,
            ParseException, OfflineException;

    @DefaultParams({ @Param(key = IShowteamResource.SITE_KEY, value = IShowteamResource.SITE_CONTRACTS) })
    @Parser(TeamContractsParser.class)
    ITeam getTeamContracts(@Param(key = TEAM_ID_KEY) Integer teamId, @Original ITeam original) throws IOException,
            ParseException, OfflineException;

    @DefaultParams({ @Param(key = IShowteamResource.SITE_KEY, value = IShowteamResource.SITE_SKILLS) })
    @Parser(TeamSkillsParser.class)
    ITeam getTeamSkills(@Param(key = TEAM_ID_KEY) Integer teamId, @Original ITeam original) throws IOException,
            ParseException, OfflineException;

    @DefaultParams({ @Param(key = IShowteamResource.SITE_KEY, value = IShowteamResource.SITE_SAISON) })
    @Parser(TeamSaisonParser.class)
    List<Spieltag> getTeamFixtures(@Param(key = TEAM_ID_KEY) Integer teamId) throws IOException, ParseException,
            OfflineException;

    @Post
    @DefaultParams({ @Param(key = IShowteamResource.SITE_KEY, value = IShowteamResource.SITE_SAISON) })
    @Parser(TeamSaisonParser.class)
    List<Spieltag> getTeamFixtures(@Param(key = TEAM_ID_KEY) Integer teamId,
            @Param(key = IShowteamResource.SAISON_KEY) Integer saison) throws IOException, ParseException,
            OfflineException;
}
