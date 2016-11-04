package at.greenmoon.os.resource.descriptors;

import java.io.IOException;

import at.greenmoon.os.OnlineSoccer.AuthenticationException;
import at.greenmoon.os.OnlineSoccer.OfflineException;
import at.greenmoon.os.parser.ValidateParser;
import at.greenmoon.os.resource.IWebResourceDescriptor.IWebLoginResource;
import at.greenmoon.os.resource.IWebResourceDescriptor.Path;

@Path(IValidateResource.PATH)
public interface IValidateResource extends IWebLoginResource {

    public static final String PATH = "validate.php"; //$NON-NLS-1$

    public static final String EMAIL_KEY = "loginemail"; //$NON-NLS-1$

    public static final String PASSWORD_KEY = "passwort"; //$NON-NLS-1$

    public static final String ACTION_KEY = "action"; //$NON-NLS-1$
    public static final String ACTION_LOGIN = "login"; //$NON-NLS-1$

    public static final String DURATION_KEY = "sdauer"; //$NON-NLS-1$
    public static final String DURATION_SESSION = "0"; //$NON-NLS-1$

    @Post
    @DefaultParams({ @Param(key = ACTION_KEY, value = ACTION_LOGIN),
            @Param(key = DURATION_KEY, value = DURATION_SESSION) })
    @Parser(ValidateParser.class)
    @Override
    void login(@Param(key = EMAIL_KEY) String email, @Param(key = PASSWORD_KEY) String password) throws IOException,
            AuthenticationException, OfflineException;

}
