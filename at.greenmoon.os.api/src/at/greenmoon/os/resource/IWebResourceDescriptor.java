package at.greenmoon.os.resource;

import java.io.IOException;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import at.greenmoon.os.OnlineSoccer.AuthenticationException;
import at.greenmoon.os.OnlineSoccer.OfflineException;
import at.greenmoon.os.resource.descriptors.IValidateResource;

public interface IWebResourceDescriptor {

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    public @interface Post {

        // post method marker
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target({ ElementType.METHOD, ElementType.TYPE })
    public @interface Path {

        String value();
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target({ ElementType.PARAMETER })
    public @interface PathParam {

        // used for path construction
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target({ ElementType.METHOD, ElementType.TYPE })
    public @interface Authentication {

        Class<? extends IWebLoginResource> value() default IValidateResource.class;
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    public @interface DefaultParams {

        Param[] value();
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target({ ElementType.ANNOTATION_TYPE, ElementType.PARAMETER })
    public @interface Param {

        String key();

        String value() default "";
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.PARAMETER)
    public @interface Original {

        // original marker
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    public @interface Parser {

        Class<? extends IResourceParser<?>> value();
    }

    public interface IWebLoginResource extends IWebResourceDescriptor {

        void login(String email, String password) throws IOException, AuthenticationException, OfflineException;
    }
}
