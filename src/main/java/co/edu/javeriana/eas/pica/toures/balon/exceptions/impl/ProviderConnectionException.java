package co.edu.javeriana.eas.pica.toures.balon.exceptions.impl;

import co.edu.javeriana.eas.pica.toures.balon.enums.ProviderConnectionExceptionCode;
import co.edu.javeriana.eas.pica.toures.balon.exceptions.AbsProviderDatabaseProxyException;

public class ProviderConnectionException extends AbsProviderDatabaseProxyException {

    public ProviderConnectionException(ProviderConnectionExceptionCode exceptionCode) {
        super(exceptionCode);
    }

    public ProviderConnectionException(ProviderConnectionExceptionCode exceptionCode, String causeMessage) {
        super(exceptionCode, causeMessage);
    }

    public ProviderConnectionException(ProviderConnectionExceptionCode exceptionCode, String causeMessage, Exception e) {
        super(exceptionCode, causeMessage, e);
    }

}
