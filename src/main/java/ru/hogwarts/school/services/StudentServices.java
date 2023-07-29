package ru.hogwarts.school.services;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.ExceptionHandler.NotFoundStudent;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class StudentServices {

    private final Map<Long, Student> storageStudent = new HashMap<>();
    private static long count = 1;

    public Student add(Student student) {
        student.setId(count);
        storageStudent.put(count++, student);
        return student;
    }

    @PostConstruct
    private void init() {
        add(new Student(0, "Петров", 18));
        add(new Student(0, "Генадий", 25));
        add(new Student(0, "Зигмунд", 21));
        add(new Student(0, "Фрейд", 23));
        add(new Student(0, "Штар", 54));
    }

    public Student remove(long id) {

        if (storageStudent.containsKey(id)) {
            return storageStudent.remove(id);
        }
        throw new NotFoundStudent("Нет такого студента");
    }

    public Student get(long id) {
        if (storageStudent.containsKey(id)) {
            return storageStudent.get(id);
        }
        throw new NotFoundStudent("Нет такого студента");
    }

    public List<Student> getAllStudent() {
        return new ArrayList<>(storageStudent.values());
    }

    public Student update(Student student) {

        if (storageStudent.containsKey(student.getId())) {
            storageStudent.put(student.getId(), student);
            return student;
        }
        throw new NotFoundStudent("Нет такого студента");
    }

    public List<Student> filterByAge(Integer age) {
        return storageStudent.values().stream().filter(e -> Objects.equals(e.getAge(), age)).collect(Collectors.toList());
    }

}
