package ru.hogwarts.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.hogwarts.school.model.Faculty;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public interface FacultyRepository extends JpaRepository<Faculty, Long> {
    List<Faculty> findFacultyByColor(String color);

    List<Faculty> findByColorContainsOrNameContains(String name, String color);

    Optional<Faculty> findByName(String name);


}
