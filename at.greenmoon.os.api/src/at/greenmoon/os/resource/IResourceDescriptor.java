package at.greenmoon.os.resource;

import java.util.Map;

public interface IResourceDescriptor {

    String getPath();

    Map<String, String> getParameters();

    boolean isPost();
}
