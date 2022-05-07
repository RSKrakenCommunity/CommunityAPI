package kraken.plugin.api;

public class KrakenRuntimeException extends RuntimeException {

    KrakenRuntimeException() {
    }

    KrakenRuntimeException(String message) {
        super(message);
    }

    KrakenRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    KrakenRuntimeException(Throwable cause) {
        super(cause);
    }

    KrakenRuntimeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
