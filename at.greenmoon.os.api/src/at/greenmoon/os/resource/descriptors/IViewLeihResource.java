package at.greenmoon.os.resource.descriptors;

import java.io.IOException;

import at.greenmoon.os.OnlineSoccer.AuthenticationException;
import at.greenmoon.os.OnlineSoccer.OfflineException;
import at.greenmoon.os.model.ITeam;
import at.greenmoon.os.parser.LeihspielerOverviewParser;
import at.greenmoon.os.resource.IWebResourceDescriptor;
import at.greenmoon.os.resource.IWebResourceDescriptor.Authentication;
import at.greenmoon.os.resource.IWebResourceDescriptor.Path;

@Authentication
@Path(IViewLeihResource.PATH)
public interface IViewLeihResource extends IWebResourceDescriptor {

    public static final String PATH = "viewleih.php"; //$NON-NLS-1$

    public static final String CHANGE_TEAM_KEY = "changetosecond"; //$NON-NLS-1$
    public static final String CHANGE_TEAM = "true"; //$NON-NLS-1$

    @Parser(LeihspielerOverviewParser.class)
    ITeam getLeihspielerInfo(@Original ITeam original) throws IOException, AuthenticationException, OfflineException;
}
