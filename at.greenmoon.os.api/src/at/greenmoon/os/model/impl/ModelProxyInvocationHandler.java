package at.greenmoon.os.model.impl;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import org.jsoup.helper.Validate;

import at.greenmoon.os.model.complement.IEntityComplementer;
import at.greenmoon.os.model.complement.IEntityComplementer.Complementer;
import at.greenmoon.os.model.impl.ModelProxyFactory.ResourceEntity;
import at.greenmoon.os.resource.IResourceEntity;

public final class ModelProxyInvocationHandler implements InvocationHandler {

    private final ResourceEntity entity;

    public ModelProxyInvocationHandler(ResourceEntity entity) {
        Validate.notNull(entity, "entity is null!"); //$NON-NLS-1$
        this.entity = entity;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        try {
            Object methodReturn = method.invoke(entity, args);
            if (methodReturn == null) {
                Complementer complementer = method.getAnnotation(Complementer.class);
                if (complementer != null) {
                    IEntityComplementer<?> entityComplementer = complementer.value().newInstance();
                    Type complementerType = getComplementerType(entityComplementer);
                    IResourceEntity original = extractOriginal(entity.getClass(), complementerType);
                    if (original != null) {
                        for (Method complementMethod : complementer.value().getMethods()) {
                            if (IEntityComplementer.COMPLEMENT_METHOD_NAME.equals(complementMethod.getName())) {
                                complementMethod.invoke(entityComplementer, original);
                            }
                        }
                        methodReturn = method.invoke(entity, args);
                    }
                }
            }
            return methodReturn;
        }
        catch (InvocationTargetException e) {
            throw e.getCause();
        }
    }

    private Type getComplementerType(IEntityComplementer<?> entityComplementer) {

        ParameterizedType type = ((ParameterizedType) entityComplementer.getClass().getGenericInterfaces()[0]);
        return type.getActualTypeArguments()[0];
    }

    private IResourceEntity extractOriginal(Class<?> clasz, Type complementerType) {

        for (Class<?> interfaceType : clasz.getInterfaces()) {
            if (complementerType.equals(interfaceType)) {
                return entity;
            }
            IResourceEntity original = extractOriginal(interfaceType, complementerType);
            if (original != null) {
                return original;
            }
        }
        return null;
    }
}