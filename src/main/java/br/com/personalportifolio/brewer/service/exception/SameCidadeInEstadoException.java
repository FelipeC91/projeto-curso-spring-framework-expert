package br.com.personalportifolio.brewer.service.exception;

public class SameCidadeInEstadoException extends RuntimeException {
    public SameCidadeInEstadoException() {
        super();
    }

    public SameCidadeInEstadoException(String message) {
        super(message);
    }

    public SameCidadeInEstadoException(String message, Throwable cause) {
        super(message, cause);
    }

    public SameCidadeInEstadoException(Throwable cause) {
        super(cause);
    }

    protected SameCidadeInEstadoException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
