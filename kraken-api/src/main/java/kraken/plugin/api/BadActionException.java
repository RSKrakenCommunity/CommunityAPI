package kraken.plugin.api;

public final class BadActionException extends KrakenRuntimeException {

    public BadActionException() {
    }

    public BadActionException(String message) {
        super(message);
    }

    public BadActionException(String message, Throwable cause) {
        super(message, cause);
    }

    public BadActionException(Throwable cause) {
        super(cause);
    }

    public BadActionException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
