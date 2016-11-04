package at.greenmoon.os.resource;

import java.io.IOException;

public interface IResourceProvider {

    IResource getResource(IResourceDescriptor descriptor) throws IOException;

    String getEmail();

    String getPassword();

    boolean isAuthenticated();
}
