package co.edu.javeriana.eas.pica.toures.balon.exceptions.impl;

import co.edu.javeriana.eas.pica.toures.balon.enums.ProviderConnectionExceptionCode;
import co.edu.javeriana.eas.pica.toures.balon.exceptions.AbsProviderDatabaseProxyException;

public class ProviderStatementExecuteException extends AbsProviderDatabaseProxyException {

    public ProviderStatementExecuteException(ProviderConnectionExceptionCode exceptionCode) {
        super(exceptionCode);
    }

    public ProviderStatementExecuteException(ProviderConnectionExceptionCode exceptionCode, String causeMessage) {
        super(exceptionCode, causeMessage);
    }

    public ProviderStatementExecuteException(ProviderConnectionExceptionCode exceptionCode, String causeMessage, Exception e) {
        super(exceptionCode, causeMessage, e);
    }

}
