package at.greenmoon.os.parser;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import at.greenmoon.os.Messages;
import at.greenmoon.os.OnlineSoccer.AuthenticationException;
import at.greenmoon.os.OnlineSoccer.ParseException;
import at.greenmoon.os.model.IWappen;
import at.greenmoon.os.model.impl.ModelProxyFactory;
import at.greenmoon.os.parser.util.LinkExtractor;
import at.greenmoon.os.resource.IResource;

public abstract class AbstractTeamParser {

    protected void checkDemoteam(Element element) throws AuthenticationException {

        if (!element.select("b:matches(DemoTeam\\s-)").isEmpty()) { //$NON-NLS-1$
            throw new AuthenticationException(Messages.getString("AbstractParser.DemoTeam")); //$NON-NLS-1$
        }
    }

    protected IWappen extractTeamWappen(Element element, IResource resource) throws ParseException {

        Elements img = element.select("img[src~=images/wappen/.+]"); //$NON-NLS-1$
        if (img.isEmpty()) {
            throw new ParseException(Messages.getString("AbstractTeamParser.TeamWappenNotFound"), this); //$NON-NLS-1$
        }
        IWappen wappen = ModelProxyFactory.create(IWappen.class, resource);
        wappen.setPath(img.attr("src")); //$NON-NLS-1$
        return wappen;
    }

    protected Integer extractTeamId(Element element) throws ParseException {

        Elements a = element.select("a[href~=javascript:tabellenplatz\\(\\d+\\)]"); //$NON-NLS-1$
        if (a.isEmpty()) {
            throw new ParseException(Messages.getString("AbstractTeamParser.TeamIdNotFound"), this); //$NON-NLS-1$
        }
        return LinkExtractor.extractId(a.get(0)).intValue();
    }

    protected String extractTeamName(Element element) throws ParseException {

        String pattern = "\\s-\\s\\d\\.\\sLiga"; //$NON-NLS-1$
        Elements b = element.select("b:matches(.+" + pattern + ")"); //$NON-NLS-1$ //$NON-NLS-2$
        if (b.isEmpty()) {
            throw new ParseException(Messages.getString("AbstractTeamParser.TeamNameNotFound"), this); //$NON-NLS-1$
        }
        return b.text().split(pattern)[0];
    }

}
