package ru.hogwarts.school.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.exceptionHandler.NotFoundFacultyException;
import ru.hogwarts.school.exceptionHandler.NotSupportedClassException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.IntBinaryOperator;
import java.util.function.LongBinaryOperator;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

@Service
public class FacultyServices {
    private final FacultyRepository facultyRepository;
    private final Logger log = LoggerFactory.getLogger(FacultyServices.class);

    public FacultyServices(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
        log.info("@Bean FacultyServices is created");
    }

    public Faculty add(Faculty faculty) {
        log.info("method add is run");
        log.debug("Faculty input: " + faculty);

        Faculty createFaculty = null;

        try {
            createFaculty = facultyRepository.save(faculty);
        } catch (RuntimeException e) {
            log.error("Not can upload new Faculty. Reason: " + e.getMessage());
            throw new NotSupportedClassException("JSON not valid");
        }

        log.info("Student " + createFaculty + " upload to db");
        return createFaculty;
    }


    public Faculty remove(long id) {
        log.info("method remove is run");

        Faculty faculty = facultyRepository.findById(id).orElseThrow(() -> {
            log.warn("Faculty is not found");
            return new NotFoundFacultyException("Нет такого факультета");
        });

        facultyRepository.deleteById(id);
        log.debug("faculty deleted " + faculty);

        return faculty;
    }

    public Faculty get(long id) {
        log.info("method get is run");
        Faculty findFaculty = facultyRepository.findById(id).orElseThrow(() -> {
            log.warn("method get cannot find Faculty id = " + id);
            return new NotFoundFacultyException("Нет такого факультета с id " + id);
        });
        log.debug("findFaculty = " + findFaculty);
        return findFaculty;
    }

    public List<Faculty> getAllFaculty() {
        log.info("method getAllFaculty is run");
        List<Faculty> all = facultyRepository.findAll();
        log.debug("Collection<Faculty> = " + all);
        return all;
    }

    public Faculty update(Faculty faculty) {
        log.info("method update is run");
        facultyRepository.findById(faculty.getId()).orElseThrow(() -> {
            log.warn("method update cannot find faculty " + faculty);
            return new NotFoundFacultyException("Нет такого факультета");
        });
        log.debug("find faculty for update = " + faculty);
        return facultyRepository.save(faculty);
    }

    public List<Faculty> filterByColor(String color) {
        log.info("method filterByColor is run");
        List<Faculty> facultyByColor = facultyRepository.findFacultyByColor(color);
        log.debug("Collection<Faculty> =" + facultyByColor);
        return facultyByColor;
    }


    public Collection<Student> getAllStudentOfSelectedFaculty(Long id) {
        log.info("method getAllStudentOfSelectedFaculty is run");
        Faculty faculty = facultyRepository.findById(id).orElseThrow(() -> {
            log.warn("method getAllStudentOfSelectedFaculty cannot find faculty with id " + id);
            return new NotFoundFacultyException("Факультет с id " + id + " не найден");
        });
        log.debug("Collection<Student> =" + faculty.getStudents());
        return faculty.getStudents();
    }

    public Collection<Faculty> filterByColorOrName(String search) {
        log.info("method filterByColorOrName is run");
        Collection<Faculty> faculties = filterByColorOrNameTwoParams(search, search);
        log.debug("Collection<Faculty> =" + faculties);
        return faculties;
    }

    public Collection<Faculty> filterByColorOrNameTwoParams(String name, String color) {
        return facultyRepository.findByColorContainsOrNameContains(name, color);
    }


    public String getLongestFacultyName() {
        log.info("method getLongestFacultyName is run");
        List<Faculty> all = facultyRepository.findAll();
        Optional<String> reduce = all.stream().parallel().map(Faculty::getName).reduce((s1, s2) -> {
            if (s1.length() > s2.length())
                return s1;
            else
                return s2;
        });

        log.debug("Longest name is {}", reduce.isPresent());
        return reduce.orElse("");
    }


    public Long getSomeDigit() {
        log.info("method getSomeDigit is run");
        int limitDigit = 1_000_000;
        LongBinaryOperator bip = Long::sum;
        Long reduce = LongStream.iterate(1, a -> a + 1).parallel().limit(limitDigit).reduce(0, bip);
        log.debug("Sum {} element = {}", limitDigit, reduce);
        return reduce;
    }

    
}
