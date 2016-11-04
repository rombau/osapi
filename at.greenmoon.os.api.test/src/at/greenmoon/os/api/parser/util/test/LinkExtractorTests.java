package at.greenmoon.os.api.parser.util.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.junit.Test;

import at.greenmoon.os.parser.util.LinkExtractor;

@SuppressWarnings("nls")
public class LinkExtractorTests {

    @Test
    public void testLinkExtractorClass() throws Exception {

        new LinkExtractor() {
            //
        };
    }

    @Test
    public void testNoLink() throws Exception {

        Element element = Jsoup.parse("<div/>").select("div").first();

        assertNull(LinkExtractor.extractId(element));
    }

    @Test
    public void testNoHref() throws Exception {

        Element element = Jsoup.parse("<a/>").select("a").first();

        assertNull(LinkExtractor.extractId(element));
    }

    @Test
    public void testHrefWithoutNumber() throws Exception {

        Element element = Jsoup.parse("<a href=\"main.htm\"/>").select("a").first();

        assertNull(LinkExtractor.extractId(element));
    }

    @Test
    public void testHrefWithIllegalNumber() throws Exception {

        Element element = Jsoup.parse("<a href=\"goto('main.htm')\"/>").select("a").first();

        assertNull(LinkExtractor.extractId(element));
    }

    @Test
    public void testHrefWithLegalNumber() throws Exception {

        Element element = Jsoup.parse("<a href=\"goto(4711)\"/>").select("a").first();

        assertEquals(4711, LinkExtractor.extractId(element).longValue());
    }

    @Test
    public void testNestedHrefWithLegalNumber() throws Exception {

        Element element = Jsoup.parse("<div><a href=\"goto(4711)\"/></div>").select("div").first();

        assertEquals(4711, LinkExtractor.extractId(element).longValue());
    }

    @Test
    public void testNestedHrefWithClickListener() throws Exception {

        Element element = Jsoup.parse("<div><a onclick=\"goto(4711)\" href=\"anthing\"/></div>").select("div").first();

        assertEquals(4711, LinkExtractor.extractId(element).longValue());
    }

}
