package at.greenmoon.os.resource.descriptors;

import java.io.IOException;

import at.greenmoon.os.OnlineSoccer.AuthenticationException;
import at.greenmoon.os.OnlineSoccer.OfflineException;
import at.greenmoon.os.resource.IWebResourceDescriptor;
import at.greenmoon.os.resource.IWebResourceDescriptor.Authentication;
import at.greenmoon.os.resource.IWebResourceDescriptor.Path;

@Authentication
@Path(IMainResource.PATH)
public interface IMainResource extends IWebResourceDescriptor {

    public static final String PATH = "haupt.php"; //$NON-NLS-1$

    public static final String CHANGE_TEAM_KEY = "changetosecond"; //$NON-NLS-1$
    public static final String CHANGE_TEAM = "true"; //$NON-NLS-1$

    @DefaultParams({ @Param(key = CHANGE_TEAM_KEY, value = CHANGE_TEAM) })
    void changeTeam() throws IOException, AuthenticationException, OfflineException;
}
