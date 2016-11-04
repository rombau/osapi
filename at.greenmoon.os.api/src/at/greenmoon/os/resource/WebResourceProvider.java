package at.greenmoon.os.resource;

import java.io.IOException;
import java.util.Map;

import org.jsoup.Connection;
import org.jsoup.Connection.Method;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;

public class WebResourceProvider implements IResourceProvider {

    private static final String LOGIN_COOKIE = "lc"; //$NON-NLS-1$

    private final String url;

    private final String email;
    private final String password;

    private Map<String, String> cookies;

    public WebResourceProvider(String url, String email, String password) {
        this.url = url;
        this.email = email;
        this.password = password;
    }

    @Override
    public boolean isAuthenticated() {
        return cookies != null && !cookies.isEmpty() && cookies.containsKey(LOGIN_COOKIE);
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public IResource getResource(final IResourceDescriptor descriptor) throws IOException {

        Connection connection = Jsoup.connect(url + "/" + descriptor.getPath()); //$NON-NLS-1$

        if (cookies != null && !cookies.isEmpty()) {
            connection.cookies(cookies);
        }

        if (descriptor.getParameters() != null && !descriptor.getParameters().isEmpty()) {
            connection.data(descriptor.getParameters());
        }

        if (descriptor.isPost()) {
            connection.method(Method.POST);
        }

        final IResourceProvider provider = this;
        final Response response = connection.execute();

        if (response.cookies() != null && !response.cookies().isEmpty()) {
            cookies = response.cookies();
        }

        IResource document = new IResource() {

            @Override
            public String getContent() {
                return response.body();
            }

            @Override
            public byte[] getBytes() {
                return response.bodyAsBytes();
            }

            @Override
            public IResourceDescriptor getDescriptor() {
                return descriptor;
            }

            @Override
            public IResourceProvider getProvider() {
                return provider;
            }
        };

        return document;
    }
}
