package at.greenmoon.os.api.model.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.lang.reflect.Proxy;

import org.junit.Test;

import at.greenmoon.os.model.complement.IEntityComplementer;
import at.greenmoon.os.model.complement.IEntityComplementer.ComplementException;
import at.greenmoon.os.model.complement.IEntityComplementer.Complementer;
import at.greenmoon.os.model.impl.ModelProxyFactory.ResourceEntity;
import at.greenmoon.os.model.impl.ModelProxyInvocationHandler;

@SuppressWarnings("nls")
public class ModelComplementTests {

    public static class TextComplmenter implements IEntityComplementer<ITestBaseObject> {

        @Override
        public void complement(ITestBaseObject original) throws ComplementException {
            original.setText("complemented");
        }
    }

    public static class IllegalComplmenter implements IEntityComplementer<String> {

        @Override
        public void complement(String original) throws ComplementException {
            original = "complemented";
        }
    }

    public static class ExceptionalComplmenter implements IEntityComplementer<ITestObject> {

        @Override
        public void complement(ITestObject original) throws ComplementException {
            throw new ComplementException(null);
        }
    }

    public interface ITestBaseObject {

        String getText();

        @Complementer(TextComplmenter.class)
        String getComplementedText();

        void setText(String text);
    }

    public interface ITestObject extends ITestBaseObject {

        @Complementer(IllegalComplmenter.class)
        String getIllegalComplementedText();

        @Complementer(ExceptionalComplmenter.class)
        String getExceptionalComplementedText() throws ComplementException;
    }

    public static class TestObject extends ResourceEntity implements ITestObject {

        private String text;

        @Override
        public String getText() {
            return text;
        }

        @Override
        public String getComplementedText() {
            return text;
        }

        @Override
        public void setText(String text) {
            this.text = text;
        }

        @Override
        public String getIllegalComplementedText() {
            return null;
        }

        @Override
        public String getExceptionalComplementedText() {
            return null;
        }

        public static ITestObject createProxy() {
            return (ITestObject) Proxy.newProxyInstance(TestObject.class.getClassLoader(),
                    new Class<?>[] { ITestObject.class }, new ModelProxyInvocationHandler(new TestObject()));
        }
    }

    @Test
    public void testGetText() throws Exception {

        ITestObject testObject = TestObject.createProxy();

        assertNull(testObject.getText());
    }

    @Test
    public void testGetComplementedText() throws Exception {

        ITestObject testObject = TestObject.createProxy();

        assertEquals("complemented", testObject.getComplementedText());
    }

    @Test
    public void testGetInitializedComplementedText() throws Exception {

        ITestObject testObject = TestObject.createProxy();
        testObject.setText("initalized");

        assertEquals("initalized", testObject.getComplementedText());
    }

    @Test
    public void testGetIllegalComplementedText() throws Exception {

        ITestObject testObject = TestObject.createProxy();

        assertNull(testObject.getIllegalComplementedText());
    }

    @Test(expected = ComplementException.class)
    public void testGetExceptionalComplementedText() throws Exception {

        ITestObject testObject = TestObject.createProxy();

        testObject.getExceptionalComplementedText();
    }
}
