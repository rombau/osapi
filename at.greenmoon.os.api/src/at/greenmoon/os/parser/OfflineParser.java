package at.greenmoon.os.parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import at.greenmoon.os.OnlineSoccer.OfflineException;
import at.greenmoon.os.resource.IResource;
import at.greenmoon.os.resource.IResourceParser;

public class OfflineParser implements IResourceParser<Void> {

    @Override
    public Void parse(IResource resource, Void original) throws OfflineException {

        Document jsoupDocument = Jsoup.parse(resource.getContent());

        if (!jsoupDocument.select("div:matches(Für\\sdie\\sDauer\\svon\\s.+\\sgesperrt!)").isEmpty()) { //$NON-NLS-1$
            throw new OfflineException();
        }

        if (!jsoupDocument.select("div:matches(Diese\\sFunktion\\sist\\s.+\\sgesperrt)").isEmpty()) { //$NON-NLS-1$
            throw new OfflineException();
        }

        return original;
    }
}
