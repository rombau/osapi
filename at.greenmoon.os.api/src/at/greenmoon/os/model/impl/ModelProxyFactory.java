package at.greenmoon.os.model.impl;

import java.lang.reflect.Proxy;

import at.greenmoon.os.model.IKaderspieler;
import at.greenmoon.os.model.ITeam;
import at.greenmoon.os.model.IWappen;
import at.greenmoon.os.resource.IResource;
import at.greenmoon.os.resource.IResourceEntity;

public abstract class ModelProxyFactory {

    public abstract static class ResourceEntity implements IResourceEntity {

        private IResource resource;

        @Override
        public void setResource(IResource resource) {
            this.resource = resource;
        }

        @Override
        public IResource getResource() {
            return resource;
        }
    }

    private static <T extends IResourceEntity> ResourceEntity createResourceEntity(Class<T> clasz) {

        if (clasz.equals(ITeam.class)) {
            return new Team();
        }
        else if (clasz.equals(IKaderspieler.class)) {
            return new Kaderspieler();
        }
        else if (clasz.equals(IWappen.class)) {
            return new Wappen();
        }

        throw new IllegalArgumentException("No entity implementation for class defined."); //$NON-NLS-1$
    }

    public static <T extends IResourceEntity> T create(Class<T> clasz, IResource resource) {

        final ResourceEntity newEntity = createResourceEntity(clasz);
        newEntity.setResource(resource);

        return clasz.cast(Proxy.newProxyInstance(ModelProxyFactory.class.getClassLoader(), new Class<?>[] { clasz },
                new ModelProxyInvocationHandler(newEntity)));
    }
}
