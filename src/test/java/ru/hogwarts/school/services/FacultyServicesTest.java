package ru.hogwarts.school.services;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.hogwarts.school.exceptionHandler.NotFoundFacultyException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repository.FacultyRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FacultyServicesTest {
    @Mock
    private FacultyRepository facultyRepository;

    @InjectMocks
    private FacultyServices facultyServices;

    private final Faculty testBlue = new Faculty(0L, "Test", "Blue");
    private final Faculty testGreen_1 = new Faculty(1L, "Test2", "Green");
    private final Faculty testGreen_2 = new Faculty(2L, "Test3", "Green");


    @Test
    void add() {
        when(facultyRepository.save(testBlue)).thenReturn(testBlue);
        assertEquals(testBlue, facultyServices.add(testBlue));
    }

    @Test
    void remove() {
        when(facultyRepository.findById(1L)).thenReturn(Optional.of(testBlue));
        assertEquals(testBlue, facultyServices.remove(1L));
    }

    @Test
    void remove_throwsNotFaculty() {

        assertThrows(NotFoundFacultyException.class, () -> facultyServices.remove(100L));
    }

    @Test
    void get() {
        when(facultyRepository.findById(1L)).thenReturn(Optional.of(testBlue));

        assertEquals(testBlue, facultyServices.get(1L));
    }

    @Test
    void get_throwsNotFaculty() {
        assertThrows(NotFoundFacultyException.class, () -> facultyServices.get(100L));
    }


    @Test
    void update() {
        testGreen_1.setId(1L);
        testBlue.setId(1L);

        when(facultyRepository.findById(1L)).thenReturn(Optional.of(testBlue));
        when(facultyRepository.save(eq(testGreen_1))).thenReturn(testGreen_1);
        assertEquals(testGreen_1, facultyServices.update(testGreen_1));

    }

    @Test
    void filterByColor() {
        when(facultyRepository.findFacultyByColor("Green")).thenReturn(new ArrayList<>(List.of(testGreen_1, testGreen_2)));
        assertEquals(2, facultyServices.filterByColor("Green").size());
    }

    // надо подумать как протестировать грамотно этот кейс
//    @Test
//    void filterByColorOrName() {
//        when(facultyRepository.findByColorContainsIgnoreCase(any())).thenReturn(new ArrayList<>(List.of(testGreen_2, testGreen_1)));
//        when(facultyRepository.findByNameContainsIgnoreCase(any())).thenReturn(new ArrayList<>(List.of(testBlue)));
//        assertEquals(3, facultyServices.filterByColorOrName("z").size());
//    }


}