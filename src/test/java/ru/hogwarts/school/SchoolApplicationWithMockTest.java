package ru.hogwarts.school;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.hogwarts.school.controller.AvatarController;
import ru.hogwarts.school.controller.FacultyController;
import ru.hogwarts.school.controller.InfoController;
import ru.hogwarts.school.controller.StudentController;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.AvatarRepository;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.repository.StudentRepository;
import ru.hogwarts.school.services.AvatarService;
import ru.hogwarts.school.services.FacultyServices;
import ru.hogwarts.school.services.StudentServices;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest
@ExtendWith(MockitoExtension.class)
public class SchoolApplicationWithMockTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentRepository studentRepository;

    @MockBean
    private FacultyRepository facultyRepository;

    @MockBean
    private AvatarRepository avatarRepository;

    @SpyBean
    private StudentServices studentServices;

    @SpyBean
    private FacultyServices facultyServices;

    @SpyBean
    private AvatarService avatarService;

    @InjectMocks
    private StudentController studentController;

    @InjectMocks
    private FacultyController facultyController;

    @InjectMocks
    private AvatarController avatarController;

    @SpyBean
    private InfoController infoController;

    private Student testStudent;

    private Faculty testFaculty;

    private final JSONObject studentObject = new JSONObject();

    private final JSONObject facultyObject = new JSONObject();

    @BeforeEach
    void init() throws JSONException {
        Long id = 1000L;
        String name = "Bob";
        int age = 25;

        String name_fac = "Grif";
        String color = "red";


        testStudent = new Student(id, name, age);

        studentObject.put("id", testStudent.getId());
        studentObject.put("name", testStudent.getName());
        studentObject.put("age", testStudent.getAge());

        testFaculty = new Faculty(1L, name_fac, color);

        facultyObject.put("id", testFaculty.getId());
        facultyObject.put("name", testFaculty.getName());
        facultyObject.put("color", testFaculty.getColor());

        testStudent.setFaculty(testFaculty);
    }


    @Test
    void addStudent() throws Exception {

        when(studentRepository.save(any(Student.class))).thenReturn(testStudent);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/student/")
                        .content(studentObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(testStudent.getName()))
                .andExpect(jsonPath("$.age").value(testStudent.getAge()));
    }

    @Test
    void getStudent() throws Exception {
        when(studentRepository.findById(any(Long.class))).thenReturn(Optional.of(testStudent));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/" + testStudent.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(testStudent.getName()))
                .andExpect(jsonPath("$.age").value(testStudent.getAge()));
    }

    @Test
    void getAllStudentStudent() throws Exception {
        List<Student> list = new ArrayList<>(List.of(
                new Student(1L, "1", 1),
                new Student(2L, "2", 2),
                new Student(3L, "3", 3)
        ));

        when(studentRepository.findAll()).thenReturn(list);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.size()").value(list.size()));
    }

    @Test
    void updateStudent() throws Exception {
        when(studentRepository.findById(any(Long.class))).thenReturn(Optional.of(testStudent));
        when(studentRepository.save(any(Student.class))).thenReturn(testStudent);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/student/")
                        .content(studentObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(testStudent.getName()));
    }

    @Test
    void removeStudent() throws Exception {
        when(studentRepository.findById(any(Long.class))).thenReturn(Optional.of(testStudent));

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/student/" + testStudent.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value(testStudent.getName()))
                .andExpect(jsonPath("$.age").value(testStudent.getAge()));
    }

    @Test
    void filterByAgeStudent() throws Exception {

        List<Student> list = new ArrayList<>(List.of(
                new Student(1L, "1", 1),
                new Student(2L, "2", 1),
                new Student(3L, "3", 1)
        ));

        when(studentRepository.findStudentByAge(1)).thenReturn(list);
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/filter-age/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(list.size()));

    }

    @Test
    void filterByAgeBetweenStudent() throws Exception {

        List<Student> list = new ArrayList<>(List.of(
                new Student(1L, "1", 1),
                new Student(2L, "2", 1),
                new Student(3L, "3", 1)
        ));

        when(studentRepository.findByAgeBetween(0, 2)).thenReturn(list);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/filter-age-between/0,2")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(list.size()));
    }

    @Test
    void getFiveLastIdStudent() throws Exception {
        List<Student> list = new ArrayList<>(List.of(
                new Student(1L, "1", 1),
                new Student(2L, "2", 1),
                new Student(3L, "3", 1),
                new Student(4L, "4", 1),
                new Student(5L, "3", 1)
        ));
        when(studentRepository.getFiveLastIdStudent()).thenReturn(list);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/get-five-last-student")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();
        assertEquals(result.getResponse().getContentAsString(), new ObjectMapper().writeValueAsString(list));

    }

    @Test
    void getAVGAeStudent() throws Exception {
        when(studentRepository.getAVGAgeStudent()).thenReturn(4.2f);
        String result = mockMvc.perform(MockMvcRequestBuilders
                .get("/student/get-avg-age-student")).andReturn().getResponse().getContentAsString();
        assertEquals("4.2", result);
    }


    @Test
    void getAmountStudent() throws Exception {
        when(studentRepository.getAllAmountStudents()).thenReturn(25);
        String result = mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/get-amount-student"))
                .andExpect(status().isOk())
                .andReturn().getResponse()
                .getContentAsString();
        assertEquals("25", result);
    }


    @Test
    void getFacultyStudentToDTOStudent() throws Exception {
        when(studentRepository.findById(any(Long.class))).thenReturn(Optional.of(testStudent));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/find-faqulty-dto/" + testStudent.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(testFaculty.getName()));

    }


    @Test
    void addFaculty() throws Exception {

        when(facultyRepository.save(any(Faculty.class))).thenReturn(testFaculty);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/faculty/")
                        .content(facultyObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(testFaculty.getName()))
                .andExpect(jsonPath("$.color").value(testFaculty.getColor()));
    }

    @Test
    void getFaculty() throws Exception {
        when(facultyRepository.findById(any(Long.class))).thenReturn(Optional.of(testFaculty));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty/" + testFaculty.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(testFaculty.getName()))
                .andExpect(jsonPath("$.color").value(testFaculty.getColor()));
    }

    @Test
    void getAllStudentFaculty() throws Exception {
        List<Faculty> list = new ArrayList<>(List.of(
                new Faculty(1L, "1", "red"),
                new Faculty(2L, "2", "red"),
                new Faculty(3L, "3", "red")
        ));

        when(facultyRepository.findAll()).thenReturn(list);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty/")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.size()").value(list.size()));
    }

    @Test
    void updateFaculty() throws Exception {
        when(facultyRepository.findById(any(Long.class))).thenReturn(Optional.of(testFaculty));
        when(facultyRepository.save(any(Faculty.class))).thenReturn(testFaculty);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/faculty/")
                        .content(facultyObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(testFaculty.getName()));
    }

    @Test
    void removeFaculty() throws Exception {
        when(facultyRepository.findById(any(Long.class))).thenReturn(Optional.of(testFaculty));

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/faculty/" + testFaculty.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value(testFaculty.getName()))
                .andExpect(jsonPath("$.color").value(testFaculty.getColor()));
    }

    @Test
    void filterByColorFaculty() throws Exception {
        List<Faculty> list = new ArrayList<>(List.of(
                new Faculty(1L, "1", "red"),
                new Faculty(2L, "2", "red"),
                new Faculty(3L, "3", "red")
        ));

        when(facultyRepository.findFacultyByColor("red")).thenReturn(list);
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty/filter-color/red")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(list.size()));
    }

    @Test
    void filterByColorOrNameFaculty() throws Exception {
        List<Faculty> list = new ArrayList<>(List.of(
                new Faculty(1L, "red", "green"),
                new Faculty(2L, "yellow", "red"),
                new Faculty(3L, "3", "red")
        ));

        when(facultyRepository.findByColorContainsOrNameContains(anyString(), anyString())).thenReturn(list);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty/filter-color-or-name/red")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(list.size()));
    }

    @Test
    void getAllStudentByFacultyIdFaculty() throws Exception {
        List<Student> list = new ArrayList<>(List.of(
                new Student(1L, "1", 1),
                new Student(2L, "2", 1),
                new Student(3L, "3", 1)
        ));

        testFaculty.setStudents(list);

        when(facultyRepository.findById(any(Long.class))).thenReturn(Optional.of(testFaculty));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty/get-all-student-by-id-faculty/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(testFaculty.getStudents().size()));
    }

    @Test
    void getPort() throws Exception {

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("http://localhost:8080/get-port")).andExpect(status().isOk()).andReturn();
        assertEquals("8080", result.getResponse().getContentAsString());
    }

}
