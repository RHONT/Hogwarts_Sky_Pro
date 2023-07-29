package ru.hogwarts.school.services;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Student;

import java.util.HashMap;
import java.util.Map;

@Service
public class StudentServices {

    private final Map<Long, Student> storageStudent = new HashMap<>();
    
    public StudentServices() {
    }


}
