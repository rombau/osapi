package at.greenmoon.os.api.test;

import org.junit.BeforeClass;
import org.junit.Test;

import at.greenmoon.os.OnlineSoccer;
import at.greenmoon.os.OnlineSoccer.AuthenticationException;
import at.greenmoon.os.api.test.fixtures.FileSystemResourceProvider;
import at.greenmoon.os.resource.ResourceProxyRegistry;

@SuppressWarnings("nls")
public class AuthenticationTests {

    @BeforeClass
    public static void setUp() {
        ResourceProxyRegistry.clearCache();
    }

    @Test(expected = AuthenticationException.class)
    public void testUnkownUserLogin() throws Exception {

        OnlineSoccer OS = new OnlineSoccer(new FileSystemResourceProvider("unknown@os.at", "geheim"));

        OS.getTeam();
    }
}
