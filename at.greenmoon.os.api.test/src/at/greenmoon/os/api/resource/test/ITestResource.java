package at.greenmoon.os.api.resource.test;

import java.io.IOException;

import at.greenmoon.os.OnlineSoccer.AuthenticationException;
import at.greenmoon.os.OnlineSoccer.OfflineException;
import at.greenmoon.os.OnlineSoccer.ParseException;
import at.greenmoon.os.model.ITeam;
import at.greenmoon.os.parser.TeamOverviewParser;
import at.greenmoon.os.resource.IResource;
import at.greenmoon.os.resource.IResourceParser;
import at.greenmoon.os.resource.IWebResourceDescriptor;

public interface ITestResource extends IWebResourceDescriptor {

    public static class TestParserThrowsParseException implements IResourceParser<ITeam> {

        @Override
        public ITeam parse(IResource resource, ITeam original) throws ParseException, OfflineException,
                AuthenticationException {
            throw new ParseException("test", this); //$NON-NLS-1$
        }
    }

    public interface IUnavailableLoginResource extends IWebLoginResource {

        @Path("xyz")
        void login(String email, String password) throws IOException, AuthenticationException, OfflineException;
    }

    Object methodWithoutPath();

    @Path("showteam.php")
    void methodWithPath();

    @Path("xyz")
    void methodThrowsIOException() throws IOException;

    @Authentication
    void methodThrowsAuthenticationException() throws AuthenticationException;

    @Authentication
    @Path("showteam.php")
    void methodWithAuthentication();

    @Authentication(IUnavailableLoginResource.class)
    @Path("showteam.php")
    void methodWithAuthenticationThrowsIOException() throws IOException;

    @Post
    @Path("showteam.php")
    void methodWithPost();

    @Path("showteam.php")
    @Parser(TeamOverviewParser.class)
    void methodWithParserWithoutOriginal();

    @Path("showteam.php")
    @Parser(TeamOverviewParser.class)
    void methodWithParserAndIllegalOriginal(@Original String original);

    @Path("offline.php")
    @Parser(TeamOverviewParser.class)
    void methodThrowsOfflineException(@Original ITeam original) throws OfflineException;

    @Path("showteam.php")
    @Parser(TestParserThrowsParseException.class)
    void methodThrowsParseException(@Original ITeam original) throws ParseException;

    @Path("{0}")
    byte[] methodReturnsImage(@PathParam String path);
}