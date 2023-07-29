package ru.hogwarts.school.services;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.hogwarts.school.ExceptionHandler.NotFoundFaculty;
import ru.hogwarts.school.model.Faculty;

import static org.junit.jupiter.api.Assertions.*;


class FacultyServicesTest {

    private FacultyServices facultyServices;

    private final Faculty test1 = new Faculty(0, "Test", "Blue");
    private final Faculty test2 = new Faculty(0, "Test2", "Green");

    @BeforeEach
    void init() {
        facultyServices = new FacultyServices();
    }

    @Test
    void add() {
        int currentSize = facultyServices.getAllStudent().size();
        facultyServices.add(test1);
        assertEquals(++currentSize, facultyServices.getAllStudent().size());
    }

    @Test
    void remove() {
        facultyServices.add(test1);
        facultyServices.add(test2);
        facultyServices.remove(1);
        assertEquals(1, facultyServices.getAllStudent().size());
    }

    @Test
    void remove_throwsNotFaculty() {
        facultyServices.add(test1);
        facultyServices.add(test2);
        assertThrows(NotFoundFaculty.class, () -> facultyServices.remove(100));
    }

    @Test
    void get() {
        facultyServices.add(test1);
        facultyServices.add(test2);
        int currentSize = facultyServices.getAllStudent().size();

        assertEquals(test2, facultyServices.get(test2.getId()));
    }

    @Test
    void get_throwsNotFaculty() {
        assertThrows(NotFoundFaculty.class, () -> facultyServices.get(100));
    }


    @Test
    void update() {
        Faculty faculty = facultyServices.add(test1);
        Faculty faculty1Change = new Faculty(faculty.getId(), "1", "2");
        assertEquals(faculty1Change, facultyServices.update(faculty1Change));

    }

    @Test
    void filterByColor() {
        facultyServices.add(test1);
        facultyServices.add(test2);
        assertEquals(test1, facultyServices.filterByColor(test1.getColor()).get(0));
    }
}