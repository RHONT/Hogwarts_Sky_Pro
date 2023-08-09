package ru.hogwarts.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ru.hogwarts.school.model.Student;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {


    @Query(value = "SELECT count(id) " +
            "FROM student",
            nativeQuery = true)
    Integer getAllAmountStudents();

    @Query(value = "select cast(avg(age) as decimal(3,1))" +
            "from student",
            nativeQuery = true)
    Float getAVGAgeStudent();


    @Query(value = "select *" +
            "    from student" +
            "    order by id desc" +
            "    limit 5",
            nativeQuery = true)
    List<Student> getFiveLastIdStudent();

    List<Student> findStudentByAge(int age);

    List<Student> findByAgeBetween(int min, int max);

    Optional<Student> findStudentByName(String name);


}
