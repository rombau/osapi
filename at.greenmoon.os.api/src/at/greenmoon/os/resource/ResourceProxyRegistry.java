package at.greenmoon.os.resource;

import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

public abstract class ResourceProxyRegistry {

    private static final Map<Class<? extends IWebResourceDescriptor>, Object> cache = new HashMap<Class<? extends IWebResourceDescriptor>, Object>();

    public static synchronized <T extends IWebResourceDescriptor> T getResourceProxy(Class<T> clasz,
            ResourceProxyInvocationHandler proxyInvocationHandler) {

        Object proxy = cache.get(clasz);

        if (proxy == null) {
            proxy = Proxy.newProxyInstance(ResourceProxyRegistry.class.getClassLoader(), new Class<?>[] { clasz },
                    proxyInvocationHandler);
            cache.put(clasz, proxy);
        }
        return clasz.cast(proxy);
    }

    public static synchronized void clearCache() {
        cache.clear();
    }
}
