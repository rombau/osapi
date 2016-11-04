package at.greenmoon.os.parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import at.greenmoon.os.OnlineSoccer.AuthenticationException;
import at.greenmoon.os.OnlineSoccer.ParseException;
import at.greenmoon.os.resource.IResource;
import at.greenmoon.os.resource.IResourceParser;

public class ValidateParser implements IResourceParser<Void> {

    @Override
    public Void parse(IResource resource, Void original) throws ParseException, AuthenticationException {

        Document jsoupDocument = Jsoup.parse(resource.getContent());

        if (resource.getContent().equals("unknown")) { //$NON-NLS-1$
            throw new AuthenticationException(resource.getContent());
        }

        // TODO Check Login

        return null;
    }

}
