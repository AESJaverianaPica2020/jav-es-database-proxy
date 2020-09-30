package co.edu.javeriana.eas.pica.toures.balon.exceptions;

import co.edu.javeriana.eas.pica.toures.balon.enums.ProviderConnectionExceptionCode;

public class AbsProviderDatabaseProxyException extends Exception {

    private final ProviderConnectionExceptionCode exceptionCode;

    public AbsProviderDatabaseProxyException(ProviderConnectionExceptionCode exceptionCode) {
        this.exceptionCode = exceptionCode;
    }

    public AbsProviderDatabaseProxyException(ProviderConnectionExceptionCode exceptionCode, String causeMessage) {
        super(causeMessage);
        this.exceptionCode = exceptionCode;
    }

    public AbsProviderDatabaseProxyException(ProviderConnectionExceptionCode exceptionCode, String causeMessage, Exception e) {
        super(causeMessage, e);
        this.exceptionCode = exceptionCode;
    }

    public ProviderConnectionExceptionCode getExceptionCode() {
        return exceptionCode;
    }

}
