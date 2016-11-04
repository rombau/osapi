package at.greenmoon.os.model;

import at.greenmoon.os.model.complement.IEntityComplementer.Complementer;
import at.greenmoon.os.model.complement.ImageComplementer;
import at.greenmoon.os.resource.IResourceEntity;

public interface IWappen extends IResourceEntity {

    String getPath();

    void setPath(String path);

    @Complementer(ImageComplementer.class)
    byte[] getBytes();

    void setBytes(byte[] bytes);
}
