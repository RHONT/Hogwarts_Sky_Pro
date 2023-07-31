package ru.hogwarts.school.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.hogwarts.school.ExceptionHandler.NotFoundStudent;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudentServicesTest {

    private final Student testStud_1 = new Student(0L, "test", 10);
    private final Student testStud_2 = new Student(0L, "test_2", 50);

    @Mock
    private StudentRepository studentRepository;
    @InjectMocks
    private StudentServices studentServices;


    @Test
    void add() {
        when(studentRepository.save(testStud_1)).thenReturn(testStud_1);
        assertEquals(testStud_1, studentServices.add(testStud_1));
    }

    @Test
    void remove() {
        when(studentRepository.findById(1L)).thenReturn(Optional.of(testStud_1));
        assertEquals(testStud_1, studentServices.remove(1L));
    }

    @Test
    void remove_throwsNotStudent() {

        assertThrows(NotFoundStudent.class, () -> studentServices.remove(100L));
    }

    @Test
    void get() {
        when(studentRepository.findById(1L)).thenReturn(Optional.of(testStud_1));

        assertEquals(testStud_1, studentServices.get(1L));
    }

    @Test
    void get_throwsNotStudent() {
        assertThrows(NotFoundStudent.class, () -> studentServices.get(100L));
    }

    // не понимаю как этот тест делать.
    @Test
    void update() {
//        testStud_2.setId(1L);
//        when(studentRepository.save(testStud_1)).thenReturn(testStud_1);
//        when(studentRepository.findById(testStud_1.getId())).thenReturn(Optional.of(testStud_1));
//        assertEquals(testStud_1, studentServices.update(testStud_1));

    }
//
//   Тоже не понимаю как...
//    @Test
//    void filterByAge() {
//        when(studentRepository.findAll()).thenReturn(new ArrayList<>(List.of(testStud_1, testStud_2)));
//        assertEquals(testStud_1, studentServices.filterByAge(0).get(0));
//    }
}