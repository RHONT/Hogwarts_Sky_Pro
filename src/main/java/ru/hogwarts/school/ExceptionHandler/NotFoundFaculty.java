package ru.hogwarts.school.ExceptionHandler;

public class NotFoundFaculty extends RuntimeException{
    public NotFoundFaculty() {
    }

    public NotFoundFaculty(String message) {
        super(message);
    }

    public NotFoundFaculty(String message, Throwable cause) {
        super(message, cause);
    }

    public NotFoundFaculty(Throwable cause) {
        super(cause);
    }

    public NotFoundFaculty(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
