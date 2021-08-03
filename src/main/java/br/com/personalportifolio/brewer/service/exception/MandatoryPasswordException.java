package br.com.personalportifolio.brewer.service.exception;

public class MandatoryPasswordException extends RuntimeException {
    public MandatoryPasswordException() {
        super();
    }

    public MandatoryPasswordException(String msg) {
    }

    public MandatoryPasswordException(String message, Throwable cause) {
        super(message, cause);
    }

    public MandatoryPasswordException(Throwable cause) {
        super(cause);
    }

    protected MandatoryPasswordException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
