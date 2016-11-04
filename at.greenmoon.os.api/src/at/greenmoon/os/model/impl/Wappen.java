package at.greenmoon.os.model.impl;

import at.greenmoon.os.model.IWappen;
import at.greenmoon.os.model.impl.ModelProxyFactory.ResourceEntity;

public class Wappen extends ResourceEntity implements IWappen {

    private String path;
    private byte[] bytes;

    @Override
    public String getPath() {
        return path;
    }

    @Override
    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public byte[] getBytes() {
        return bytes;
    }

    @Override
    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }

}
