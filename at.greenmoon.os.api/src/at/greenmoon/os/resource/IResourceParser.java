package at.greenmoon.os.resource;

import at.greenmoon.os.OnlineSoccer.AuthenticationException;
import at.greenmoon.os.OnlineSoccer.OfflineException;
import at.greenmoon.os.OnlineSoccer.ParseException;

public interface IResourceParser<T> {

    final String PARSE_METHOD_NAME = "parse"; //$NON-NLS-1$

    T parse(IResource resource, T original) throws ParseException, OfflineException, AuthenticationException;
}
