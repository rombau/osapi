package at.greenmoon.os.resource;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import at.greenmoon.os.OnlineSoccer.AuthenticationException;
import at.greenmoon.os.parser.OfflineParser;
import at.greenmoon.os.resource.IWebResourceDescriptor.Authentication;
import at.greenmoon.os.resource.IWebResourceDescriptor.DefaultParams;
import at.greenmoon.os.resource.IWebResourceDescriptor.IWebLoginResource;
import at.greenmoon.os.resource.IWebResourceDescriptor.Original;
import at.greenmoon.os.resource.IWebResourceDescriptor.Param;
import at.greenmoon.os.resource.IWebResourceDescriptor.Parser;
import at.greenmoon.os.resource.IWebResourceDescriptor.Path;
import at.greenmoon.os.resource.IWebResourceDescriptor.PathParam;
import at.greenmoon.os.resource.IWebResourceDescriptor.Post;

public class ResourceProxyInvocationHandler implements InvocationHandler {

    private final IResourceProvider resourceProvider;

    public ResourceProxyInvocationHandler(IResourceProvider resourceProvider) {
        this.resourceProvider = resourceProvider;
    }

    @Override
    public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {

        checkAuthenticationAndLoginIfRequired(method);

        final String path = extractPath(method, args);
        if (path != null) {

            IResource resource = resourceProvider.getResource(new IResourceDescriptor() {

                @Override
                public boolean isPost() {
                    return method.isAnnotationPresent(Post.class);
                }

                @Override
                public String getPath() {
                    return path;
                }

                @Override
                public Map<String, String> getParameters() {
                    return extractParameters(method, args);
                }
            });

            new OfflineParser().parse(resource, null);

            Parser parser = method.getAnnotation(Parser.class);
            if (parser != null) {

                Object original = extractOriginal(method, args);

                IResourceParser<?> parserInstance = parser.value().newInstance();
                Method parseMethod = parser.value().getMethod(IResourceParser.PARSE_METHOD_NAME, IResource.class,
                        Object.class);
                try {
                    return parseMethod.invoke(parserInstance, resource, original);
                }
                catch (InvocationTargetException e) {
                    if (e.getCause() instanceof ClassCastException) {
                        throw new IllegalArgumentException(e.getCause());
                    }
                    throw e.getCause();
                }
            }

            if (method.getReturnType().equals(String.class)) {
                return resource.getContent();
            }
            return resource.getBytes();
        }
        return null;
    }

    private void checkAuthenticationAndLoginIfRequired(final Method method) throws Exception, AuthenticationException {

        IWebLoginResource webLoginResource = extractAuthentication(method);
        if (webLoginResource != null && !resourceProvider.isAuthenticated()) {

            try {
                webLoginResource.login(resourceProvider.getEmail(), resourceProvider.getPassword());
            }
            catch (Exception e) {
                // rethrow any exception from recursive login invocation
                throw e;
            }
        }
    }

    private IWebLoginResource extractAuthentication(Method method) {

        Authentication authentication = method.getAnnotation(Authentication.class);
        if (authentication == null) {
            authentication = method.getDeclaringClass().getAnnotation(Authentication.class);
        }
        if (authentication == null) {
            return null;
        }
        return ResourceProxyRegistry.getResourceProxy(authentication.value(), this);
    }

    private String extractPath(final Method method, Object[] args) {

        Path path = method.getAnnotation(Path.class);
        if (path == null) {
            path = method.getDeclaringClass().getAnnotation(Path.class);
        }

        if (path == null) {
            return null;
        }

        List<Object> params = new ArrayList<Object>();
        Annotation[][] parameterAnnotations = method.getParameterAnnotations();
        for (int p = 0; p < parameterAnnotations.length; p++) {
            for (int a = 0; a < parameterAnnotations[p].length; a++) {
                if (parameterAnnotations[p][a] instanceof PathParam) {
                    params.add(args[p]);
                }
            }
        }

        if (!params.isEmpty()) {
            return MessageFormat.format(path.value(), params.toArray());
        }

        return path.value();
    }

    private Map<String, String> extractParameters(Method method, Object[] args) {

        Map<String, String> params = new HashMap<String, String>();

        DefaultParams defaultParams = method.getAnnotation(DefaultParams.class);
        if (defaultParams != null) {
            for (Param param : defaultParams.value()) {
                params.put(param.key(), param.value());
            }
        }

        Annotation[][] parameterAnnotations = method.getParameterAnnotations();
        for (int p = 0; p < parameterAnnotations.length; p++) {
            for (int a = 0; a < parameterAnnotations[p].length; a++) {
                if (parameterAnnotations[p][a] instanceof Param) {
                    Param param = (Param) parameterAnnotations[p][a];
                    params.put(param.key(), args[p].toString());
                }
            }
        }

        if (params.isEmpty()) {
            return null;
        }
        return params;
    }

    private Object extractOriginal(Method method, Object[] args) {

        Annotation[][] parameterAnnotations = method.getParameterAnnotations();
        for (int p = 0; p < parameterAnnotations.length; p++) {
            for (int a = 0; a < parameterAnnotations[p].length; a++) {
                if (parameterAnnotations[p][a] instanceof Original) {
                    return args[p];
                }
            }
        }
        return null;
    }

}
