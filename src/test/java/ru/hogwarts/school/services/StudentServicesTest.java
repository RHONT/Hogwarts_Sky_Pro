package ru.hogwarts.school.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.hogwarts.school.exceptionHandler.NotFoundStudentException;
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
    private final Student testStud_3 = new Student(0L, "test_3", 50);

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

        assertThrows(NotFoundStudentException.class, () -> studentServices.remove(100L));
    }

    @Test
    void get() {
        when(studentRepository.findById(1L)).thenReturn(Optional.of(testStud_1));

        assertEquals(testStud_1, studentServices.get(1L));
    }

    @Test
    void get_throwsNotStudent() {
        assertThrows(NotFoundStudentException.class, () -> studentServices.get(100L));
    }

    @Test
    void update() {
        testStud_2.setId(1L);
        testStud_1.setId(1L);

        when(studentRepository.findById(1L)).thenReturn(Optional.of(testStud_1));
        when(studentRepository.save(eq(testStud_2))).thenReturn(testStud_2);
        assertEquals(testStud_2, studentServices.update(testStud_2));
    }

    @Test
    void filterByAge() {
        when(studentRepository.findStudentByAge(50)).thenReturn(new ArrayList<>(List.of(testStud_2, testStud_3)));
        assertEquals(2, studentServices.filterByAge(50).size());
    }


    // не понимаю есть ли смысл в моем тесте
    // обозначаю поведение и тут же выкидываю готовый результат.
    @Test
    void filterByAgeBetween() {
        when(studentRepository.findByAgeBetween(9, 20)).thenReturn(new ArrayList<>(List.of(testStud_1)));
        assertEquals(1, studentServices.filterByAgeBetween(9, 20).size());
    }
}