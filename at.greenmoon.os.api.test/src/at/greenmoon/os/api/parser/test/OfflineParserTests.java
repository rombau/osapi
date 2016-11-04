package at.greenmoon.os.api.parser.test;

import org.junit.Test;

import at.greenmoon.os.OnlineSoccer.OfflineException;
import at.greenmoon.os.api.test.fixtures.TestResource;
import at.greenmoon.os.parser.OfflineParser;
import at.greenmoon.os.resource.IResource;

@SuppressWarnings("nls")
public class OfflineParserTests {

    @Test(expected = OfflineException.class)
    public void testOffline() throws Exception {

        IResource resource = new TestResource(
                "<div><p><iframe></iframe></p>Für die Dauer von ZAT x sind die Seiten von OS 2.0 gesperrt!<br><a>Zum Forum</a><br><a>Zum Chat</a></div>");

        new OfflineParser().parse(resource, null);
    }

    @Test(expected = OfflineException.class)
    public void testFunktionGesperrt() throws Exception {

        IResource resource = new TestResource(
                "<div><p><iframe></iframe></p>Diese Funktion ist bis ca. 25. April gesperrt</div>");

        new OfflineParser().parse(resource, null);
    }
}
