package at.greenmoon.os.resource.descriptors;

import java.io.IOException;
import java.util.List;

import at.greenmoon.os.OnlineSoccer.AuthenticationException;
import at.greenmoon.os.OnlineSoccer.OfflineException;
import at.greenmoon.os.OnlineSoccer.ParseException;
import at.greenmoon.os.model.ITeam;
import at.greenmoon.os.model.impl.Spieltag;
import at.greenmoon.os.parser.TeamContractsParser;
import at.greenmoon.os.parser.TeamOverviewParser;
import at.greenmoon.os.parser.TeamSaisonParser;
import at.greenmoon.os.parser.TeamSkillsParser;
import at.greenmoon.os.resource.IWebResourceDescriptor;
import at.greenmoon.os.resource.IWebResourceDescriptor.Authentication;
import at.greenmoon.os.resource.IWebResourceDescriptor.Path;

@Authentication
@Path(IShowteamResource.PATH)
public interface IShowteamResource extends IWebResourceDescriptor {

    public static final String PATH = "showteam.php"; //$NON-NLS-1$

    public static final String SAISON_KEY = "saison"; //$NON-NLS-1$

    public static final String SITE_KEY = "s"; //$NON-NLS-1$
    public static final String SITE_CONTRACTS = "1"; //$NON-NLS-1$
    public static final String SITE_SKILLS = "2"; //$NON-NLS-1$
    public static final String SITE_SAISON = "6"; //$NON-NLS-1$

    @Parser(TeamOverviewParser.class)
    ITeam getTeamOverview(@Original ITeam original) throws IOException, AuthenticationException, ParseException,
            OfflineException;

    @DefaultParams({ @Param(key = SITE_KEY, value = SITE_CONTRACTS) })
    @Parser(TeamContractsParser.class)
    ITeam getTeamContracts(@Original ITeam original) throws IOException, AuthenticationException, ParseException,
            OfflineException;

    @DefaultParams({ @Param(key = SITE_KEY, value = SITE_SKILLS) })
    @Parser(TeamSkillsParser.class)
    ITeam getTeamSkills(@Original ITeam original) throws IOException, AuthenticationException, ParseException,
            OfflineException;

    @DefaultParams({ @Param(key = SITE_KEY, value = SITE_SAISON) })
    @Parser(TeamSaisonParser.class)
    List<Spieltag> getTeamFixtures() throws IOException, AuthenticationException, ParseException, OfflineException;

    @Post
    @DefaultParams({ @Param(key = SITE_KEY, value = SITE_SAISON) })
    @Parser(TeamSaisonParser.class)
    List<Spieltag> getTeamFixtures(@Param(key = SAISON_KEY) Integer saison) throws IOException,
            AuthenticationException, ParseException, OfflineException;
}
