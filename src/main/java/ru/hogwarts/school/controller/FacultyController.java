package ru.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.services.FacultyServices;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("faculty/")
public class FacultyController {

    private final FacultyServices facultyServices;

    public FacultyController(FacultyServices facultyServices) {
        this.facultyServices = facultyServices;
    }

    @PostMapping
    public ResponseEntity<Faculty> add(@RequestBody Faculty faculty) {

        return ResponseEntity.ok(facultyServices.add(faculty));
    }

    @GetMapping("{id}")
    public ResponseEntity<Faculty> get(@PathVariable long id) {
        return ResponseEntity.ok(facultyServices.get(id));
    }

    @GetMapping
    public List<Faculty> getAllStudent() {
        return facultyServices.getAllStudent();
    }

    @PutMapping
    public ResponseEntity<Faculty> update(@RequestBody Faculty faculty) {
        return ResponseEntity.ok(facultyServices.update(faculty));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Faculty> remove(@PathVariable long id) {
        return ResponseEntity.ok(facultyServices.remove(id));
    }

    @GetMapping("/filter-color/{color}")
    public List<Faculty> filterByColor(@PathVariable String color) {
        return facultyServices.filterByColor(color);
    }

    @GetMapping("/filter-color-or-name/{nameOrColor}")
    public Collection<Faculty> filterByColorOrName(@PathVariable String nameOrColor) {
        return facultyServices.filterByColorOrName(nameOrColor);
    }

    @GetMapping("/get-all-student-by-id-faculty/{id_faculty}")
    public Collection<Student> filterByColorOrName(@PathVariable Long id_faculty) {
        return facultyServices.getAllStudentOfSelectedFaculty(id_faculty);
    }


}
