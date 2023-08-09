package ru.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.entityDto.FacultyDTO;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.services.StudentServices;

import java.util.List;

@RestController
@RequestMapping("student/")
public class StudentController {
    private final StudentServices studentServices;

    public StudentController(StudentServices studentServices) {
        this.studentServices = studentServices;
    }

    @PostMapping
    public ResponseEntity<Student> add(@RequestBody Student student) {
        studentServices.add(student);
        return ResponseEntity.ok(student);
    }

    @GetMapping("{id}")
    public ResponseEntity<Student> get(@PathVariable long id) {
        return ResponseEntity.ok(studentServices.get(id));
    }

    @GetMapping("getByName/{name}")
    public ResponseEntity<Student> getByName(@PathVariable String name) {
        return ResponseEntity.ok(studentServices.getStudentByName(name));
    }

    @GetMapping
    public List<Student> getAllStudent() {
        return studentServices.getAllStudent();
    }

    @PutMapping
    public ResponseEntity<Student> update(@RequestBody Student student) {
        return ResponseEntity.ok(studentServices.update(student));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Student> remove(@PathVariable long id) {
        return ResponseEntity.ok(studentServices.remove(id));
    }

    @GetMapping("/filter-age/{age}")
    public List<Student> filterByAge(@PathVariable Integer age) {
        return studentServices.filterByAge(age);
    }

    @GetMapping("/filter-age-between/{min},{max}")
    public List<Student> filterByAgeBetween(@PathVariable Integer min, @PathVariable Integer max) {
        return studentServices.filterByAgeBetween(min, max);
    }

    @GetMapping("/find-faqulty-dto/{id_student}")
    public FacultyDTO getFacultyStudentToDTO(@PathVariable Long id_student) {
        return studentServices.getFacultyStudentToDTO(id_student);
    }

    @GetMapping("/get-amount-student")
    public Integer getAmountStudent() {
        return studentServices.getAmountStudent();
    }

    @GetMapping("/get-avg-age-student")
    public Float getAVGAeStudent() {
        return studentServices.getAVGAeStudent();
    }

    @GetMapping("/get-five-last-student")
    public List<Student> getFiveLastIdStudent() {
        return studentServices.getFiveLastIdStudent();
    }


}
