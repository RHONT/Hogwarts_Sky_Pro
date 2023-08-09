package ru.hogwarts.school.exceptionHandler;

public class PageErrorInputException extends RuntimeException {
    public PageErrorInputException() {
    }

    public PageErrorInputException(String message) {
        super(message);
    }

    public PageErrorInputException(String message, Throwable cause) {
        super(message, cause);
    }

    public PageErrorInputException(Throwable cause) {
        super(cause);
    }

    public PageErrorInputException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
