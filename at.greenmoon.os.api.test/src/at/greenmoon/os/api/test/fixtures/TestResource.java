package at.greenmoon.os.api.test.fixtures;

import at.greenmoon.os.resource.IResource;
import at.greenmoon.os.resource.IResourceDescriptor;
import at.greenmoon.os.resource.IResourceProvider;

public class TestResource implements IResource {

    private final String content;

    public TestResource(String content) {
        this.content = content;
    }

    @Override
    public IResourceProvider getProvider() {
        return null;
    }

    @Override
    public String getContent() {
        return content;
    }

    @Override
    public IResourceDescriptor getDescriptor() {
        return null;
    }

    @Override
    public byte[] getBytes() {
        return null;
    }

}
