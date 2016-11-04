package at.greenmoon.os.api.test.fixtures;

import java.io.IOException;
import java.util.Map;

import at.greenmoon.os.resource.IResource;
import at.greenmoon.os.resource.IResourceDescriptor;
import at.greenmoon.os.resource.IResourceProvider;

@SuppressWarnings("nls")
public class FileSystemResourceProvider implements IResourceProvider {

    private boolean authenticated = false;

    private final String email;
    private final String password;

    public FileSystemResourceProvider(String email, String password) {
        this.email = email;
        this.password = password;
    }

    @Override
    public IResource getResource(final IResourceDescriptor descriptor) throws IOException {

        final String path = descriptor.getPath();
        final Map<String, String> parameters = descriptor.getParameters();

        if (path.contains("validate.php")) {

            descriptor.isPost(); // only for coverage

            if (parameters != null) {
                if ("manager@os.at".equals(parameters.get("loginemail"))) {
                    authenticated = true;
                }
                else {
                    return createResource("unknown", null, descriptor, this);
                }
            }
            return createResource("ok", null, descriptor, this);
        }

        byte[] bytes = ResourceLoader.getResourceStream(path, parameters);
        return createResource(new String(bytes, "UTF-8"), bytes, descriptor, this);
    }

    private IResource createResource(final String content, final byte[] bytes, final IResourceDescriptor descriptor,
            final IResourceProvider provider) {

        return new IResource() {

            @Override
            public String getContent() {
                return content;
            }

            @Override
            public byte[] getBytes() {
                return bytes;
            }

            @Override
            public IResourceProvider getProvider() {
                return provider;
            }

            @Override
            public IResourceDescriptor getDescriptor() {
                return descriptor;
            }
        };
    }

    @Override
    public boolean isAuthenticated() {
        return authenticated;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
    }
}
