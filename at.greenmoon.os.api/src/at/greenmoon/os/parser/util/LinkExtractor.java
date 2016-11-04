package at.greenmoon.os.parser.util;

import org.jsoup.nodes.Element;

public abstract class LinkExtractor {

    @SuppressWarnings("nls")
    public static Long extractId(Element link) {

        link = link.select("a").first();
        if (link != null) {
            String href = link.attr("href");
            if (href == null || href.isEmpty() || !href.contains("(")) {
                href = link.attr("onclick");
            }
            try {
                return Long.valueOf(href.split("\\(")[1].split("\\)")[0]); //$NON-NLS-1$
            }
            catch (Exception e) {
                return null;
            }
        }
        return null;
    }
}
