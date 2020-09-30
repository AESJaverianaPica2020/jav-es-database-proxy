package co.edu.javeriana.eas.pica.toures.balon.enums;

import org.springframework.http.HttpStatus;

public enum ProviderConnectionExceptionCode {

    TPC_ESTABLISH_OR_CLOSEABLE_CONNECTION(HttpStatus.SERVICE_UNAVAILABLE),
    STATEMENT_EXECUTE_FAILED(HttpStatus.NOT_ACCEPTABLE),
    INVALID_PARAMETERS(HttpStatus.BAD_REQUEST);

    private HttpStatus status;

    ProviderConnectionExceptionCode(HttpStatus code) {
        this.status = code;
    }

    public HttpStatus getCode() {
        return status;
    }

}
