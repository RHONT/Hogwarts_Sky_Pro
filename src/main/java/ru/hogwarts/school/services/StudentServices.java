package ru.hogwarts.school.services;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.exceptionHandler.NotFoundStudentException;
import ru.hogwarts.school.entityDto.FacultyDTO;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.*;

@Service
public class StudentServices {

    private final StudentRepository studentRepository;


    public StudentServices(StudentRepository studentRepository, FacultyRepository facultyRepository) {
        this.studentRepository = studentRepository;
    }


    public Student add(Student student) {
        return studentRepository.save(student);
    }

    public Student remove(Long id) {
        Student student = studentRepository.findById(id).orElseThrow(() -> new NotFoundStudentException("Нет такого студента с id " + id));
        studentRepository.deleteById(id);
        return student;
    }

    public Student get(Long id) {
        return studentRepository.findById(id).orElseThrow(() -> new NotFoundStudentException("Нет такого студента с id " + id));
    }

    public List<Student> getAllStudent() {
        return studentRepository.findAll();
    }

    public Student update(Student student) {
        studentRepository.findById(student.getId()).orElseThrow(() -> new NotFoundStudentException("Нет такого студента : " + student));
        return studentRepository.save(student);
    }

    public List<Student> filterByAge(Integer age) {
        return studentRepository.findStudentByAge(age);
    }

    public List<Student> filterByAgeBetween(Integer min, Integer max) {
        return studentRepository.findByAgeBetween(min, max);
    }

    public FacultyDTO getFacultyStudentToDTO(Long student_id) {
        Student student = studentRepository.findById(student_id).orElseThrow(() -> new NotFoundStudentException("Студент с id " + student_id + " не найден"));
        return new FacultyDTO(student.getFaculty());
    }

}
