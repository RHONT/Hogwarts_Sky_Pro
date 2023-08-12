package ru.hogwarts.school;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.transaction.annotation.Transactional;
import ru.hogwarts.school.controller.FacultyController;
import ru.hogwarts.school.controller.StudentController;
import ru.hogwarts.school.exceptionHandler.NotFoundStudentException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.List;
import java.util.Objects;

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

    @Autowired
    private FacultyRepository facultyRepository;

    @Autowired
    private StudentRepository studentRepository;


    private final Student studentForAdd = new Student("add", 25);
    private final Student studentForDelete = new Student("delete", 45);
    private final Student studentForUpdate = new Student("update", 25);

    private final Faculty facultytForAdd = new Faculty("add", "addColor");
    private final Faculty facultyForDelete = new Faculty("delete", "deleteColor");
    private final Faculty facultyForUpdate = new Faculty("update", "updateColor");

    private String beginStud;
    private String beginFaculty;


    @BeforeEach
    void init() {
        beginStud = "http://localhost:" + port + "/student/";
        beginFaculty = "http://localhost:" + port + "/faculty/";
    }

    @Test
    @Order(1)
    void contextLoad() {
        Assertions.assertThat(studentController).isNotNull();
        Assertions.assertThat(facultyController).isNotNull();

        {
            Student studentAdd = studentController.getByName("add").getBody();
            Student studentDel = studentController.getByName("delete").getBody();
            Student studentUp = studentController.getByName("update").getBody();

            if (studentAdd == null) {
                Assertions.assertThat(restTemplate.postForObject(beginStud, studentForAdd, String.class)).isNotNull();
            }
            if (studentUp == null) {
                Assertions.assertThat(restTemplate.postForObject(beginStud, studentForUpdate, String.class)).isNotNull();
            }
            if (studentDel == null) {
                Assertions.assertThat(restTemplate.postForObject(beginStud, studentForDelete, String.class)).isNotNull();
            }
        }

        {

            Faculty facultytAdd = facultyRepository.findByName("add").orElse(null);
            Faculty facultytDel = facultyRepository.findByName("delete").orElse(null);
            Faculty facultytUp = facultyRepository.findByName("update").orElse(null);

            if (facultytAdd == null) {
                Assertions.assertThat(restTemplate.postForObject(beginFaculty, facultytForAdd, String.class)).isNotNull();
            }
            if (facultytUp == null) {
                Assertions.assertThat(restTemplate.postForObject(beginFaculty, facultyForUpdate, String.class)).isNotNull();
            }
            if (facultytDel == null) {
                Assertions.assertThat(restTemplate.postForObject(beginFaculty, facultyForDelete, String.class)).isNotNull();
            }
        }
    }


    @Test
    void StudentControllerGet() {
        Student student = studentController.getByName("add").getBody();
        Assertions.assertThat(restTemplate.getForObject(beginStud + student.getId(), String.class)).contains("add");
    }

    @Test
    void StudentControllerGetAllStudent() {
        List<Student> list = restTemplate.getForObject(beginStud, List.class);
        assertFalse(list.isEmpty());
    }

    //поправить
    @Test
    void StudentControllerUpdate() {
        Student student = studentController.getByName("update").getBody();
        if (student != null) {
            student.setAge(300);
        } else {
            throw new NotFoundStudentException();
        }

        ResponseEntity<Student> exchange = restTemplate.exchange(beginStud, HttpMethod.PUT, new HttpEntity<>(student), Student.class);

        Assertions.assertThat(exchange.getBody()).isNotNull();

        Assertions.assertThat(exchange.getBody().getAge()).isEqualTo(student.getAge());
    }

    @Test
    void StudentControllerRemove() {
        Student student = studentController.getByName("delete").getBody();
        restTemplate.delete(beginStud + student.getId());

        assertThrows(NotFoundStudentException.class, () -> studentController.get(student.getId()));
    }

    @Test
    void publicFilterByAge() {
        Assertions.assertThat(restTemplate.getForObject(beginStud + "filter-age/25", String.class)).isNotNull();
    }

    @Test
    void filterByAgeBetween() {
        Assertions.assertThat(restTemplate.getForObject(beginStud + "filter-age-between/0,30", String.class)).contains("add");
    }

    @Test
    void getFacultyStudentToDTO() {
        Student student = studentController.getByName("Разин Венерзенович").getBody();
        Assertions.assertThat(restTemplate.getForObject(beginStud + "find-faqulty-dto/" + student.getId(), String.class)).contains("reduce");
    }


    @Test
    void FacultyControllerAddAndRemove() {
        Faculty faculty = facultyRepository.findByName("add").orElse(null);
        if (faculty != null) {
            restTemplate.delete(beginFaculty + faculty.getId());
        }
        Assertions.assertThat(restTemplate.postForObject(beginFaculty, facultytForAdd, String.class)).contains("add");
    }

    @Test
    void FacultyControllerGet() {
        Faculty faculty = facultyRepository.findByName("add").orElse(null);
        if (faculty != null) {
            Assertions.assertThat(restTemplate.getForObject(beginFaculty + faculty.getId(), String.class)).contains("add");
        }
    }

    @Test
    void FacultyControllerGetAllStudent() {
        List<Faculty> listInput = restTemplate.getForObject(beginFaculty, List.class);
        List<Faculty> listFromDb = facultyRepository.findAll();
        assertEquals(listInput.size(), listFromDb.size());
    }

    @Test
    void FacultyControllerUpdate() {
        Faculty faculty = facultyRepository.findByName("update").orElse(null);
        if (faculty != null) {
            faculty.setColor("ChangeForTest");
            ResponseEntity<Faculty> exch = restTemplate.exchange(beginFaculty, HttpMethod.PUT, new HttpEntity<>(faculty), Faculty.class);
            Assertions.assertThat(Objects.requireNonNull(exch.getBody()).getColor()).isEqualTo("ChangeForTest");
        }
    }


    @Test
    void FacultyControllerFilterByColor() {
        String colorTest = "Yellow";
        Faculty faculty_1 = new Faculty(colorTest, "Test");
        Faculty faculty_2 = new Faculty("Test", colorTest);

        restTemplate.postForEntity(beginFaculty, faculty_1, String.class);
        restTemplate.postForEntity(beginFaculty, faculty_2, String.class);

        List<Faculty> list = restTemplate.getForObject(beginFaculty + "filter-color/" + colorTest, List.class);
        assertEquals(1, list.size());

        facultyRepository.deleteById(facultyRepository.findFacultyByColor("Test").get(0).getId());
        facultyRepository.deleteById(facultyRepository.findFacultyByColor(colorTest).get(0).getId());


    }

    @Test
    void FacultyControllerFilterByColorOrName() {
        String colorTest = "Yellow";
        Faculty faculty_1 = new Faculty(colorTest, "Test");
        Faculty faculty_2 = new Faculty("Test", colorTest);

        restTemplate.postForEntity(beginFaculty, faculty_1, String.class);
        restTemplate.postForEntity(beginFaculty, faculty_2, String.class);

        List<Faculty> list = restTemplate.getForObject(beginFaculty + "/filter-color-or-name/" + colorTest, List.class);
        assertEquals(2, list.size());

        facultyRepository.deleteById(facultyRepository.findFacultyByColor("Test").get(0).getId());
        facultyRepository.deleteById(facultyRepository.findFacultyByColor(colorTest).get(0).getId());
    }

    @Test
    @Transactional
    void getAllStudentByFacultyId() {
        Faculty faculty = facultyRepository.findByName("react").get();
        int amountStudent = faculty.getStudents().size();

        List<Student> list = restTemplate.getForObject(beginFaculty + "get-all-student-by-id-faculty/" + faculty.getId(), List.class);
        assertEquals(amountStudent, list.size());
    }

    @Test
    void getPort() {
        Integer port = restTemplate.getForObject("http://localhost:8080/get-port", Integer.class);
        assertEquals(8080, port);


    }
}
