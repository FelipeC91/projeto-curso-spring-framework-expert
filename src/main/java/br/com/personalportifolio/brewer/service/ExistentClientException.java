package br.com.personalportifolio.brewer.service;

public class ExistentClientException extends RuntimeException{
    public ExistentClientException() {
    }

    public ExistentClientException(String message) {
        super(message);
    }

    public ExistentClientException(String message, Throwable cause) {
        super(message, cause);
    }

    public ExistentClientException(Throwable cause) {
        super(cause);
    }

    public ExistentClientException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
