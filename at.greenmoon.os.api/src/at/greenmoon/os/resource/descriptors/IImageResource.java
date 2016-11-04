package at.greenmoon.os.resource.descriptors;

import java.io.IOException;

import at.greenmoon.os.OnlineSoccer.OfflineException;
import at.greenmoon.os.resource.IWebResourceDescriptor;
import at.greenmoon.os.resource.IWebResourceDescriptor.Path;

@Path(IImageResource.PATH)
public interface IImageResource extends IWebResourceDescriptor {

    public static final String PATH = "{0}"; //$NON-NLS-1$

    byte[] getBytes(@PathParam String path) throws IOException, OfflineException;
}
