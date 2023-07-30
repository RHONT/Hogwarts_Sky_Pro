package ru.hogwarts.school.services;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.ExceptionHandler.NotFoundStudent;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

import javax.annotation.PostConstruct;
import java.util.*;

@Service
public class StudentServices {

    private final StudentRepository studentRepository;

    public StudentServices(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }


    public Student add(Student student) {
        return studentRepository.save(student);
    }

//    @PostConstruct
//    private void init() {
//        add(new Student(0, "Петров", 18));
//        add(new Student(0, "Генадий", 25));
//        add(new Student(0, "Зигмунд", 21));
//        add(new Student(0, "Фрейд", 23));
//        add(new Student(0, "Штар", 54));
//    }

    public Student remove(Long id) {
        Student student = studentRepository.findById(id).orElseThrow(() -> new NotFoundStudent("Нет такого студента"));
        studentRepository.deleteById(id);
        return student;
    }

    public Student get(Long id) {
        return studentRepository.findById(id).orElseThrow(() -> new NotFoundStudent("Нет такого студента"));
    }

    public List<Student> getAllStudent() {
        return studentRepository.findAll();
    }

    public Student update(Student student) {
        Student studentFind = studentRepository.findById(student.getId()).orElseThrow(() -> new NotFoundStudent("Нет такого студента"));
        return studentRepository.save(student);
    }

    public List<Student> filterByAge(Integer age) {
        return studentRepository.findStudentByAge(age);
    }

}
