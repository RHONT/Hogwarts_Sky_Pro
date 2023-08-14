package ru.hogwarts.school.services;

import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.web.config.QuerydslWebConfiguration;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.exceptionHandler.NotFoundStudentException;
import ru.hogwarts.school.entityDto.FacultyDTO;
import ru.hogwarts.school.exceptionHandler.NotSupportedClassException;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

@Service
public class StudentServices {
    private final Logger log = LoggerFactory.getLogger(StudentServices.class);

    private final StudentRepository studentRepository;


    public StudentServices(StudentRepository studentRepository, FacultyRepository facultyRepository) {
        this.studentRepository = studentRepository;
        log.info("@Bean StudentServices is created");
    }


    public Student add(Student student) {
        log.info("method add is run");
        log.debug("Student input: " + student);

        Student createStudent = null;

        try {
            createStudent = studentRepository.save(student);
        } catch (RuntimeException e) {
            log.error("Not can upload new student. Reason: " + e.getMessage());
            throw new NotSupportedClassException("JSON not valid");
        }

        log.debug("Student " + createStudent + " upload to db");
        return createStudent;
    }

    public Student remove(Long id) {
        log.info("method remove is run");

        Student student = studentRepository.findById(id).orElse(null);

        log.debug("Method Remove find: " + student);

        if (student != null) {
            studentRepository.deleteById(id);
            log.debug("student deleted");
        } else {
            log.warn("Student for delete not found with id " + id);
            throw new NotFoundStudentException("Student for delete not found");
        }

        return student;
    }

    public Student get(Long id) {
        log.info("method get is run");
        Student finnStudent = studentRepository.findById(id).orElseThrow(() -> {
            log.warn("method get cannot find student with id " + id);
            return new NotFoundStudentException("Нет такого студента с id " + id);
        });
        log.debug("findStudent = " + finnStudent);
        return finnStudent;
    }

    public List<Student> getAllStudent() {
        log.info("method getAllStudent is run");
        List<Student> all = studentRepository.findAll();
        log.debug("Collection<Student> = " + all);
        return all;
    }

    public Student update(Student student) {
        log.info("method getAllStudent is run");

        Student finnStudent = studentRepository.findById(student.getId()).orElseThrow(() -> {
            log.warn("method update cannot find student " + student);
            return new NotFoundStudentException("Нет такого студента : " + student);
        });
        log.debug("finnStudent = " + finnStudent);
        return studentRepository.save(student);
    }

    public List<Student> filterByAge(Integer age) {
        log.info("method filterByAge is run");
        List<Student> studentByAge = studentRepository.findStudentByAge(age);
        log.debug("Collection<Student> =" + studentByAge);
        return studentByAge;
    }

    public List<Student> filterByAgeBetween(Integer min, Integer max) {
        log.info("method filterByAgeBetween is run");
        List<Student> byAgeBetween = studentRepository.findByAgeBetween(min, max);
        log.debug("Collection<Student> =" + byAgeBetween);
        return byAgeBetween;
    }

    public FacultyDTO getFacultyStudentToDTO(Long student_id) {
        log.info("method getFacultyStudentToDTO is run");
        Student student = studentRepository.findById(student_id).orElseThrow(() -> {
            log.warn("method getFacultyStudentToDTO cannot find student ");
            return new NotFoundStudentException("Студент с id " + student_id + " не найден");
        });
        log.debug("Student faculty is " + student.getFaculty());
        return new FacultyDTO(student.getFaculty());
    }

    // Метод не входил в программу, логировать нет смысла.
    public Student getStudentByName(String name) {
        return studentRepository.findStudentByName(name).orElse(null);
    }


    public Integer getAmountStudent() {
        log.info("method getAmountStudent is run");
        Integer allAmountStudents = studentRepository.getAllAmountStudents();
        log.debug("Amount students is  " + allAmountStudents);
        return allAmountStudents;
    }

    public Float getAVGAeStudent() {
        log.info("method getAVGAeStudent is run");
        Float avgAgeStudent = studentRepository.getAVGAgeStudent();
        log.debug("AVGAgeStudent students is  " + avgAgeStudent);
        return avgAgeStudent;
    }

    public List<Student> getFiveLastIdStudent() {
        log.info("method getFiveLastIdStudent is run");
        List<Student> fiveLastIdStudent = studentRepository.getFiveLastIdStudent();
        log.debug("Collection<Student> =" + fiveLastIdStudent);
        return fiveLastIdStudent;
    }

    public List<Student> getStudentNameStartWithA() {
        log.info("method getStudentNameStartWithA is run");
        List<Student> all = studentRepository.findAll();
        log.debug("Collection<Student> = {}", all);

        Supplier<List<Student>> supplier = ArrayList::new;

        BiConsumer<List<Student>, Student> accumulator = List::add;

        BinaryOperator<List<Student>> combiner = (listLeft, listRight) -> {
            List<Student> result = Collections.synchronizedList(new ArrayList<>());
            result.addAll(listLeft);
            result.addAll(listRight);
            return result;
        };

        Function<List<Student>, List<Student>> finisher = Function.identity();

        List<Student> result = all.parallelStream()
                .filter(e -> e.getName().startsWith("А"))
                .collect(Collector.of(supplier, accumulator, combiner, finisher, Collector.Characteristics.CONCURRENT));

        log.debug("result Collection<Student> = {}", result);

        return result;
    }

    public Double getAVGAgeWithStream() {
        log.info("method getStudentNameStartWithA is run");
        List<Student> all = studentRepository.findAll();
        OptionalDouble average = all.parallelStream().mapToInt(Student::getAge).average();
        log.debug("avg result = {}", average.orElse(0d));
        return average.orElse(0d);
    }

    public void nonSynchronizePrintSixName() {
        log.info("method nonSynchronizePrintSixName is run");
        Queue<Student> all = new ArrayDeque<>(studentRepository.findAll());
        printTwoNamesNoSynchro(all, 2);
        Thread t1 = new Thread(() -> printTwoNamesNoSynchro(all, 2));
        Thread t2 = new Thread(() -> printTwoNamesNoSynchro(all, 2));
        t1.start();
        t2.start();

    }

    public void synchronizePrintSixName() {
        log.info("method synchronizePrintSixName is run");
        Queue<Student> all = new ArrayDeque<>(studentRepository.findAll());
        printTwoNamesSynchro(all, 2);
        Thread t1 = new Thread(() -> printTwoNamesSynchro(all, 2));
        Thread t2 = new Thread(() -> printTwoNamesSynchro(all, 2));
        t1.start();
        t2.start();
    }

    private synchronized void printTwoNamesSynchro(Queue<Student> queue, int amount) {
        for (int i = 0; i < amount; i++) {
            System.out.println(queue.poll());
        }
    }

    private void printTwoNamesNoSynchro(Queue<Student> queue, int amount) {
        for (int i = 0; i < amount; i++) {
            System.out.println(queue.poll());
        }
    }

}
