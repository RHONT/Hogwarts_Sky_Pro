package ru.hogwarts.school.exceptionHandler;

public class IllegalFormatContentException extends RuntimeException {

    public IllegalFormatContentException(String message, Throwable cause) {
        super(message, cause);
    }

    public IllegalFormatContentException(Throwable cause) {
        super(cause);
    }

    public IllegalFormatContentException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public IllegalFormatContentException() {
    }

    public IllegalFormatContentException(String extension) {
        super("Желаемый формат " + extension + " Не поддерживается");
    }
}
