package ru.hogwarts.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.hogwarts.school.model.Faculty;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public interface FacultyRepository extends JpaRepository<Faculty, Long> {
    List<Faculty> findFacultyByColor(String color);

    List<Faculty> findByNameContainsIgnoreCase(String name);

    List<Faculty> findByColorContainsIgnoreCase(String color);

    List<Faculty> findByColorContainsOrNameContains(String name, String color);

//    Faculty findById(Integer id);


}
