package at.greenmoon.os.model.complement;

import at.greenmoon.os.model.IWappen;
import at.greenmoon.os.resource.IResourceProvider;
import at.greenmoon.os.resource.ResourceProxyInvocationHandler;
import at.greenmoon.os.resource.ResourceProxyRegistry;
import at.greenmoon.os.resource.descriptors.IImageResource;

public class ImageComplementer implements IEntityComplementer<IWappen> {

    @Override
    public void complement(IWappen image) throws ComplementException {

        IResourceProvider originalProvider = image.getResource().getProvider();

        try {
            IImageResource resource = ResourceProxyRegistry.getResourceProxy(IImageResource.class,
                    new ResourceProxyInvocationHandler(originalProvider));
            image.setBytes(resource.getBytes(image.getPath()));
        }
        catch (Exception e) {
            throw new ComplementException(e);
        }

    }

}
