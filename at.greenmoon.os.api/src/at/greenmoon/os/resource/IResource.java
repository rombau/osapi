package at.greenmoon.os.resource;

public interface IResource {

    String getContent();

    byte[] getBytes();

    IResourceDescriptor getDescriptor();

    IResourceProvider getProvider();
}