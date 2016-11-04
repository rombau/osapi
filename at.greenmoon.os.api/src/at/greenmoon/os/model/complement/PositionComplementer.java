package at.greenmoon.os.model.complement;

import at.greenmoon.os.model.IKaderspieler;
import at.greenmoon.os.model.IKaderspieler.Leihstatus;
import at.greenmoon.os.resource.IResourceProvider;
import at.greenmoon.os.resource.ResourceProxyInvocationHandler;
import at.greenmoon.os.resource.ResourceProxyRegistry;
import at.greenmoon.os.resource.descriptors.IViewLeihResource;

public class PositionComplementer implements IEntityComplementer<IKaderspieler> {

    @Override
    public void complement(IKaderspieler original) throws ComplementException {

        IResourceProvider originalProvider = original.getResource().getProvider();

        try {
            if (original.getLeihstatus().equals(Leihstatus.Verliehen)) {
                IViewLeihResource viewleih = ResourceProxyRegistry.getResourceProxy(IViewLeihResource.class,
                        new ResourceProxyInvocationHandler(originalProvider));
                // XXX team null check
                viewleih.getLeihspielerInfo(original.getTeam());
            }
            else {
                // TODO perhaps via player resource ??
            }
        }
        catch (Exception e) {
            throw new ComplementException(e);
        }
    }
}
