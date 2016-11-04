package at.greenmoon.os.model.complement;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public interface IEntityComplementer<T> {

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    public @interface Complementer {

        Class<? extends IEntityComplementer<?>> value();
    }

    public class ComplementException extends Exception {

        private static final long serialVersionUID = 6027203260698220103L;

        public ComplementException(Exception e) {
            super(e);
        }

    }

    String COMPLEMENT_METHOD_NAME = "complement"; //$NON-NLS-1$

    void complement(T original) throws ComplementException;
}
