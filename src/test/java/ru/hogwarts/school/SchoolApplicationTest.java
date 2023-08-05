package ru.hogwarts.school;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.event.annotation.BeforeTestClass;
import org.springframework.test.context.event.annotation.BeforeTestMethod;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.hogwarts.school.controller.FacultyController;
import ru.hogwarts.school.controller.StudentController;
import ru.hogwarts.school.entityDto.FacultyDTO;
import ru.hogwarts.school.exceptionHandler.NotFoundStudentException;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;
import ru.hogwarts.school.services.StudentServices;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SchoolApplicationTest {
    @LocalServerPort
    private int port;

    @Autowired
    private FacultyController facultyController;
    @Autowired
    private StudentController studentController;
    @Autowired
    private TestRestTemplate restTemplate;

    private final Student studentForAdd = new Student("add", 25);
    private final Student studentForDelete = new Student("delete", 45);
    private final Student studentForUpdate = new Student("update", 25);


    @Test
    @Order(1)
    void contextLoad() {

        Assertions.assertThat(studentController).isNotNull();
        Assertions.assertThat(facultyController).isNotNull();

        Student studentAdd = studentController.getByName("add").getBody();
        Student studentDel = studentController.getByName("delete").getBody();
        Student studentUp = studentController.getByName("update").getBody();

        if (studentAdd == null) {
            Assertions.assertThat(restTemplate.postForObject("http://localhost:" + port + "/student/", studentForAdd, String.class)).isNotNull();
        }
        if (studentUp == null) {
            Assertions.assertThat(restTemplate.postForObject("http://localhost:" + port + "/student/", studentForUpdate, String.class)).isNotNull();
        }
        if (studentDel == null) {
            Assertions.assertThat(restTemplate.postForObject("http://localhost:" + port + "/student/", studentForDelete, String.class)).isNotNull();
        }
    }


    @Test
    void StudentControllerGet() {
        Student student = studentController.getByName("add").getBody();
        Assertions.assertThat(restTemplate.getForObject("http://localhost:" + port + "/student/" + student.getId(), String.class)).contains("add");

    }

    @Test
    void StudentControllerGetAllStudent() {
        Assertions.assertThat(restTemplate.getForObject("http://localhost:" + port + "/student/", String.class)).isNotNull();
    }

    @Test
    void StudentControllerUpdate() {
        Student student = studentController.getByName("update").getBody();
        student.setAge(200);
        Assertions.assertThat(restTemplate.postForObject("http://localhost:" + port + "/student/", student, String.class)).contains("200");
    }

    @Test
    void StudentControllerRemove() {
        Student student = studentController.getByName("delete").getBody();
        restTemplate.delete("http://localhost:" + port + "/student/" + student.getId());
        assertThrows(NotFoundStudentException.class, () -> studentController.get(student.getId()));
    }


    @Test
    void publicFilterByAge() {
        Assertions.assertThat(restTemplate.getForObject("http://localhost:" + port + "/student/filter-age/25", String.class)).isNotNull();
    }

    @Test
    void filterByAgeBetween() {
        Assertions.assertThat(restTemplate.getForObject("http://localhost:" + port + "/student/filter-age-between/0,30", String.class)).contains("add");
    }

    @Test
    void getFacultyStudentToDTO() {

    }


}
