package at.greenmoon.os;

import java.io.IOException;

import org.jsoup.helper.Validate;

import at.greenmoon.os.model.ITeam;
import at.greenmoon.os.resource.IResourceProvider;
import at.greenmoon.os.resource.ResourceProxyInvocationHandler;
import at.greenmoon.os.resource.ResourceProxyRegistry;
import at.greenmoon.os.resource.WebResourceProvider;
import at.greenmoon.os.resource.descriptors.IMainResource;
import at.greenmoon.os.resource.descriptors.IPublicShowteamResource;
import at.greenmoon.os.resource.descriptors.IShowteamResource;

/**
 * Central access point to Online Soccer.
 */
public final class OnlineSoccer {

    // XXX make it configurable
    private static final String OS_URL = "http://os.ongapo.com"; //$NON-NLS-1$

    private final ResourceProxyInvocationHandler resourceProxyInvocationHandler;

    private int currentTeam = 1;

    /**
     * The AuthenticationException is thrown, when anything goes wrong with the
     * access to protected sites.
     */
    public static class AuthenticationException extends Exception {

        private static final long serialVersionUID = -4322011659822107246L;

        public AuthenticationException(String msg) {
            super(msg);
        }
    }

    /**
     * The OfflineException is thrown, when the Online Soccer website is offline
     * due to report generation (ZAT).
     */
    public static class OfflineException extends Exception {

        private static final long serialVersionUID = -4322011659822107246L;

    }

    /**
     * The ParseException is thrown, if the information requested is not found
     * on the Online Soccer website. A reason could be, that the site is
     * changed.
     */
    public static class ParseException extends Exception {

        private static final long serialVersionUID = -4322011659822107246L;

        private final Object parser;

        public ParseException(String msg, Object parser) {
            super(msg);
            this.parser = parser;
        }

        public ParseException(String msg, Object parser, Exception e) {
            super(msg, e);
            this.parser = parser;
        }

        @Override
        public String getMessage() {
            return super.getMessage() + " [" + parser.getClass().getName() + "]"; //$NON-NLS-1$ //$NON-NLS-2$
        }
    }

    /**
     * Initialize a central access point to Online Soccer website. To access
     * protected sites, the given {@code email} and {@code passwort} are used.
     * If email and password are {@code null} only public sites are accessible.
     * When accessing protected sites without valid email and password an
     * {@link AuthenticationException} is thrown.
     *
     * @param email
     *            the email adress to access protected sites.
     * @param password
     *            the password to access protected sites
     */
    public OnlineSoccer(String email, String password) {
        resourceProxyInvocationHandler = new ResourceProxyInvocationHandler( //
                new WebResourceProvider(OS_URL, email, password));
    }

    /**
     * Initialize a central access point to Online Soccer with the given
     * {@link IResourceProvider}.
     *
     * @param resourceProvider
     *            the provider for resources apart the Online Soccer website.
     */
    public OnlineSoccer(IResourceProvider resourceProvider) {
        Validate.notNull(resourceProvider, "resourceProvider is null"); //$NON-NLS-1$
        resourceProxyInvocationHandler = new ResourceProxyInvocationHandler(resourceProvider);
    }

    /**
     * Returns the first {@link ITeam} of the manager authenticated by email and
     * password.
     *
     * @return first team
     *
     * @throws IOException
     *             if the website could not be reached
     * @throws AuthenticationException
     *             if email and/or password is wrong.
     * @throws ParseException
     *             if anything changed on the website
     * @throws OfflineException
     *             if the website is offline
     */
    public ITeam getTeam() throws IOException, AuthenticationException, ParseException, OfflineException {

        if (currentTeam != 1) {
            IMainResource mainResource = ResourceProxyRegistry.getResourceProxy(IMainResource.class,
                    resourceProxyInvocationHandler);
            mainResource.changeTeam();
            currentTeam = 1;
        }

        IShowteamResource showteam = ResourceProxyRegistry.getResourceProxy(IShowteamResource.class,
                resourceProxyInvocationHandler);
        return showteam.getTeamOverview(null);
    }

    /**
     * Returns the second {@link ITeam} of the manager authenticated by email
     * and password.
     *
     * @return second team
     *
     * @throws IOException
     *             if the website could not be reached
     * @throws AuthenticationException
     *             if email and/or password is wrong.
     * @throws ParseException
     *             if anything changed on the website
     * @throws OfflineException
     *             if the website is offline
     */
    public ITeam getSecondTeam() throws IOException, AuthenticationException, ParseException, OfflineException {

        if (currentTeam != 2) {
            IMainResource mainResource = ResourceProxyRegistry.getResourceProxy(IMainResource.class,
                    resourceProxyInvocationHandler);
            mainResource.changeTeam();
            currentTeam = 2;
        }

        IShowteamResource showteam = ResourceProxyRegistry.getResourceProxy(IShowteamResource.class,
                resourceProxyInvocationHandler);
        return showteam.getTeamOverview(null);
    }

    /**
     * Returns the {@link ITeam} with the given {@code id}. No authentication is
     * needed.
     *
     * @param id
     *            the number of the team
     *
     * @return first team
     *
     * @throws IOException
     *             if the website could not be reached
     * @throws ParseException
     *             if anything changed on the website
     * @throws OfflineException
     *             if the website is offline
     */
    public ITeam getTeam(Integer id) throws IOException, ParseException, OfflineException {
        Validate.notNull(id, "id is null"); //$NON-NLS-1$
        IPublicShowteamResource showteam = ResourceProxyRegistry.getResourceProxy(IPublicShowteamResource.class,
                resourceProxyInvocationHandler);
        return showteam.getTeamOverview(id, null);
    }
}
