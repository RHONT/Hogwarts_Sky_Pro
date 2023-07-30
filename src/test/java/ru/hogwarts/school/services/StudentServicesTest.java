package ru.hogwarts.school.services;

import org.junit.jupiter.api.Test;
import ru.hogwarts.school.ExceptionHandler.NotFoundStudent;
import ru.hogwarts.school.model.Student;


import static org.junit.jupiter.api.Assertions.*;

class StudentServicesTest {
//
//    private final StudentServices studentServices = new StudentServices(studentRepository);
//
//    private final Student test1 = new Student(0, "Test", 10);
//    private final Student test2 = new Student(0, "Test2", 12);
//
//    @Test
//    void add() {
//        int currentSize = studentServices.getAllStudent().size();
//        studentServices.add(test1);
//        assertEquals(++currentSize, studentServices.getAllStudent().size());
//    }
//
//    @Test
//    void remove() {
//        studentServices.add(test1);
//        studentServices.add(test2);
//        studentServices.remove(1);
//        assertEquals(1, studentServices.getAllStudent().size());
//    }
//
//    @Test
//    void remove_throwsNotStudent() {
//        studentServices.add(test1);
//        studentServices.add(test2);
//        assertThrows(NotFoundStudent.class, () -> studentServices.remove(100));
//    }
//
//    @Test
//    void get() {
//        studentServices.add(test1);
//        studentServices.add(test2);
//        int currentSize = studentServices.getAllStudent().size();
//
//        assertEquals(test2, studentServices.get(test2.getId()));
//    }
//
//    @Test
//    void get_throwsNotStudent() {
//        assertThrows(NotFoundStudent.class, () -> studentServices.get(100));
//    }
//
//
//    @Test
//    void update() {
//        Student student = studentServices.add(test1);
//        Student student1Change = new Student(student.getId(), "1", 10);
//        assertEquals(student1Change, studentServices.update(student1Change));
//
//    }
//
//
//    @Test
//    void filterByAge() {
//        studentServices.add(test1);
//        studentServices.add(test2);
//        assertEquals(test1, studentServices.filterByAge(test1.getAge()).get(0));
//    }
}