package ru.hogwarts.school.services;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.ExceptionHandler.NotFoundFaculty;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repository.FacultyRepository;

import java.util.*;

@Service
public class FacultyServices {
    private final FacultyRepository facultyRepository;

    public FacultyServices(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    public Faculty add(Faculty faculty) {
        return facultyRepository.save(faculty);
    }


    public Faculty remove(long id) {

        Faculty faculty = facultyRepository.findById(id).orElseThrow(() -> new NotFoundFaculty("Нет такого факультета"));
        facultyRepository.deleteById(id);
        return faculty;
    }

    public Faculty get(long id) {
        return facultyRepository.findById(id).orElseThrow(() -> new NotFoundFaculty("Нет такого факультета"));
    }

    public List<Faculty> getAllStudent() {
        return facultyRepository.findAll();
    }

    public Faculty update(Faculty faculty) {

        facultyRepository.findById(faculty.getId()).orElseThrow(() -> new NotFoundFaculty("Нет такого факультета"));
        return facultyRepository.save(faculty);
    }

    public List<Faculty> filterByColor(String color) {
        return facultyRepository.findFacultyByColor(color);
    }

    private List<Faculty> filterByColorIgnoreCase(String color) {
        return facultyRepository.findByColorContainsIgnoreCase(color);
    }

    private List<Faculty> filterByNameIgnoreCase(String name) {
        return facultyRepository.findByNameContainsIgnoreCase(name);
    }

    public Collection<Faculty> filterByColorOrName(String strSearch) {
        Set<Faculty> setByName = new HashSet<>(filterByNameIgnoreCase(strSearch));
        setByName.addAll(filterByColorIgnoreCase(strSearch));
        return setByName;
    }


}
