package ru.hogwarts.school.ExceptionHandler;

public class NotFoundStudent extends RuntimeException{
    public NotFoundStudent() {
    }

    public NotFoundStudent(String message) {
        super(message);
    }

    public NotFoundStudent(String message, Throwable cause) {
        super(message, cause);
    }

    public NotFoundStudent(Throwable cause) {
        super(cause);
    }

    public NotFoundStudent(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
