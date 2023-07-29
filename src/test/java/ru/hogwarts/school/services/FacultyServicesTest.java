package ru.hogwarts.school.services;


import org.junit.jupiter.api.Test;
import ru.hogwarts.school.ExceptionHandler.NotFoundFaculty;
import ru.hogwarts.school.model.Faculty;

import static org.junit.jupiter.api.Assertions.*;


class FacultyServicesTest {

    private final FacultyServices facultyServices = new FacultyServices();

    private final Faculty test1 = new Faculty(0, "Test", "Blue");
    private final Faculty test2 = new Faculty(0, "Test2", "Green");

    @Test
    void add() {

        facultyServices.add(test1);
        assertEquals(test1, facultyServices.get(1));
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
        assertEquals(test1, facultyServices.get(1));
    }

    @Test
    void get_throwsNotFaculty() {
        assertThrows(NotFoundFaculty.class, () -> facultyServices.get(100));
    }


    @Test
    void update() {
        facultyServices.add(test1);
        test2.setId(1);
        assertEquals(test2, facultyServices.update(test2));

    }

    @Test
    void filterByColor() {
        facultyServices.add(test1);
        facultyServices.add(test2);
        assertEquals(test1, facultyServices.filterByColor(test1.getColor()).get(0));
    }
}