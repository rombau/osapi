package at.greenmoon.os.parser;

import org.jsoup.nodes.Element;

import at.greenmoon.os.Messages;
import at.greenmoon.os.OnlineSoccer.AuthenticationException;

public abstract class AbstractParser {

    protected void checkDemoteam(Element element) throws AuthenticationException {

        if (!element.select("div:matches(Diese Seite ist ohne Team nicht verfügbar!)").isEmpty()) { //$NON-NLS-1$
            throw new AuthenticationException(Messages.getString("AbstractParser.DemoTeam")); //$NON-NLS-1$
        }
    }

}
