package ru.hogwarts.school.exceptionHandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class SchoolExceptionHandler {

    @ExceptionHandler({NotFoundStudentException.class, NotFoundFacultyException.class, IllegalFormatContentException.class, PageErrorInputException.class})
    public ResponseEntity<String> NotFound(Exception exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }


}
