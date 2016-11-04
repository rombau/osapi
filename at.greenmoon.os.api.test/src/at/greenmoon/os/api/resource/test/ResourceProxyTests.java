package at.greenmoon.os.api.resource.test;

import static org.junit.Assert.assertNull;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import at.greenmoon.os.OnlineSoccer.AuthenticationException;
import at.greenmoon.os.OnlineSoccer.OfflineException;
import at.greenmoon.os.OnlineSoccer.ParseException;
import at.greenmoon.os.api.test.fixtures.FileSystemResourceProvider;
import at.greenmoon.os.resource.ResourceProxyInvocationHandler;
import at.greenmoon.os.resource.ResourceProxyRegistry;

@SuppressWarnings("nls")
public class ResourceProxyTests {

    @Before
    public void setUp() {

        ResourceProxyRegistry.clearCache();
    }

    @Test
    public void testResourceProxyRegistry() throws Exception {

        new ResourceProxyRegistry() {
            //
        };
    }

    @Test
    public void testMethodWithoutPath() throws Exception {

        ITestResource proxy = ResourceProxyRegistry.getResourceProxy(ITestResource.class,
                new ResourceProxyInvocationHandler(new FileSystemResourceProvider("manager@os.at", "geheim")));

        assertNull(proxy.methodWithoutPath());
    }

    @Test
    public void testMethodWithPath() throws Exception {

        ITestResource proxy = ResourceProxyRegistry.getResourceProxy(ITestResource.class,
                new ResourceProxyInvocationHandler(new FileSystemResourceProvider("manager@os.at", "geheim")));

        proxy.methodWithPath();
    }

    @Test
    public void testMethodWithAuthentication() throws Exception {

        ITestResource proxy = ResourceProxyRegistry.getResourceProxy(ITestResource.class,
                new ResourceProxyInvocationHandler(new FileSystemResourceProvider("manager@os.at", "geheim")));

        proxy.methodWithAuthentication();
    }

    @Test
    public void testMethodWithAuthenticationAlreadyEstablished() throws Exception {

        ITestResource proxy = ResourceProxyRegistry.getResourceProxy(ITestResource.class,
                new ResourceProxyInvocationHandler(new FileSystemResourceProvider("manager@os.at", "geheim")));

        proxy.methodWithAuthentication();
        proxy.methodWithAuthentication();
    }

    @Test
    public void testMethodWithPost() throws Exception {

        ITestResource proxy = ResourceProxyRegistry.getResourceProxy(ITestResource.class,
                new ResourceProxyInvocationHandler(new FileSystemResourceProvider("manager@os.at", "geheim")));

        proxy.methodWithPost();
    }

    @Test
    public void testMethodWithParserWithoutOriginal() throws Exception {

        ITestResource proxy = ResourceProxyRegistry.getResourceProxy(ITestResource.class,
                new ResourceProxyInvocationHandler(new FileSystemResourceProvider("manager@os.at", "geheim")));

        proxy.methodWithParserWithoutOriginal();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testMethodWithParserAndIllegalOriginal() throws Exception {

        ITestResource proxy = ResourceProxyRegistry.getResourceProxy(ITestResource.class,
                new ResourceProxyInvocationHandler(new FileSystemResourceProvider("manager@os.at", "geheim")));

        proxy.methodWithParserAndIllegalOriginal("this is no team instance for parser");
    }

    @Test(expected = IOException.class)
    public void testMethodThrowsIOException() throws Exception {

        ITestResource proxy = ResourceProxyRegistry.getResourceProxy(ITestResource.class,
                new ResourceProxyInvocationHandler(new FileSystemResourceProvider("manager@os.at", "geheim")));

        proxy.methodThrowsIOException();
    }

    @Test(expected = AuthenticationException.class)
    public void testMethodThrowsAuthenticationException() throws Exception {

        ITestResource proxy = ResourceProxyRegistry.getResourceProxy(ITestResource.class,
                new ResourceProxyInvocationHandler(new FileSystemResourceProvider("unknown", "geheim")));

        proxy.methodThrowsAuthenticationException();
    }

    @Test(expected = IOException.class)
    public void testMethodWithAuthentivationThrowsIOException() throws Exception {

        ITestResource proxy = ResourceProxyRegistry.getResourceProxy(ITestResource.class,
                new ResourceProxyInvocationHandler(new FileSystemResourceProvider("manager@os.at", "geheim")));

        proxy.methodWithAuthenticationThrowsIOException();
    }

    @Test(expected = OfflineException.class)
    public void testMethodThrowsOfflineException() throws Exception {

        ITestResource proxy = ResourceProxyRegistry.getResourceProxy(ITestResource.class,
                new ResourceProxyInvocationHandler(new FileSystemResourceProvider("manager@os.at", "geheim")));

        proxy.methodThrowsOfflineException(null);
    }

    @Test(expected = ParseException.class)
    public void testGetTeamWithParseException() throws Exception {

        ITestResource proxy = ResourceProxyRegistry.getResourceProxy(ITestResource.class,
                new ResourceProxyInvocationHandler(new FileSystemResourceProvider("manager@os.at", "geheim")));

        proxy.methodThrowsParseException(null);
    }

    @Test
    public void testGetImage() throws Exception {

        ITestResource proxy = ResourceProxyRegistry.getResourceProxy(ITestResource.class,
                new ResourceProxyInvocationHandler(new FileSystemResourceProvider("manager@os.at", "geheim")));

        byte[] image = proxy.methodReturnsImage("images/wappen/00000019.png");

        Assert.assertNotNull(image);

    }
}
