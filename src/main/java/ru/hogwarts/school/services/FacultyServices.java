package ru.hogwarts.school.services;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.exceptionHandler.NotFoundFacultyException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;

import java.util.*;

@Service
public class FacultyServices {
    private final FacultyRepository facultyRepository;

    public FacultyServices(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    public Faculty add(Faculty faculty) {
        return facultyRepository.save(faculty);
    }


    public Faculty remove(long id) {

        Faculty faculty = facultyRepository.findById(id).orElseThrow(() -> new NotFoundFacultyException("Нет такого факультета"));
        facultyRepository.deleteById(id);
        return faculty;
    }

    public Faculty get(long id) {
        return facultyRepository.findById(id).orElseThrow(() -> new NotFoundFacultyException("Нет такого факультета"));
    }

    public List<Faculty> getAllStudent() {
        return facultyRepository.findAll();
    }

    public Faculty update(Faculty faculty) {

        facultyRepository.findById(faculty.getId()).orElseThrow(() -> new NotFoundFacultyException("Нет такого факультета"));
        return facultyRepository.save(faculty);
    }

    public List<Faculty> filterByColor(String color) {
        return facultyRepository.findFacultyByColor(color);
    }


    public Collection<Student> getAllStudentOfSelectedFaculty(Long id) {
        Faculty faculty = facultyRepository.findById(id).orElseThrow(() -> new NotFoundFacultyException("Факультет с id " + id + " не найден"));
        return faculty.getStudents();
    }

    public Collection<Faculty> filterByColorOrName(String search) {
        return filterByColorOrNameTwoParams(search, search);
    }

    public Collection<Faculty> filterByColorOrNameTwoParams(String name, String color) {
        return facultyRepository.findByColorContainsOrNameContains(name, color);
    }


}
