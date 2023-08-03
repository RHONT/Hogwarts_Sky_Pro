package ru.hogwarts.school.exceptionHandler;

public class NotFoundFacultyException extends RuntimeException {
    public NotFoundFacultyException() {
    }

    public NotFoundFacultyException(String message) {
        super(message);
    }

    public NotFoundFacultyException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotFoundFacultyException(Throwable cause) {
        super(cause);
    }

    public NotFoundFacultyException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
