package at.greenmoon.os.model.complement;

import at.greenmoon.os.model.ISpieler;
import at.greenmoon.os.model.ITeam;
import at.greenmoon.os.resource.IResourceDescriptor;
import at.greenmoon.os.resource.IResourceProvider;
import at.greenmoon.os.resource.ResourceProxyInvocationHandler;
import at.greenmoon.os.resource.ResourceProxyRegistry;
import at.greenmoon.os.resource.descriptors.IPublicShowteamResource;
import at.greenmoon.os.resource.descriptors.IShowteamResource;

public class SkillComplementer implements IEntityComplementer<ISpieler> {

    @Override
    public void complement(ISpieler original) throws ComplementException {

        IResourceProvider originalProvider = original.getResource().getProvider();
        IResourceDescriptor originalDescriptor = original.getResource().getDescriptor();

        try {
            if (original.getTeam() != null) {
                ITeam team = original.getTeam();
                if (originalDescriptor.getPath().equals(IPublicShowteamResource.PATH)) {
                    IPublicShowteamResource showteam = ResourceProxyRegistry.getResourceProxy(
                            IPublicShowteamResource.class, new ResourceProxyInvocationHandler(originalProvider));
                    showteam.getTeamSkills(team.getId(), team);
                }
                else {
                    IShowteamResource showteam = ResourceProxyRegistry.getResourceProxy(IShowteamResource.class,
                            new ResourceProxyInvocationHandler(originalProvider));
                    showteam.getTeamSkills(team);
                }
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
