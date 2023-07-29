package ru.hogwarts.school.model;

import java.util.Objects;

public class Student {
    private long id;
    private String namr;
    private Integer age;

    public Student(long id, String namr, Integer age) {
        this.id = id;
        this.namr = namr;
        this.age = age;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNamr() {
        return namr;
    }

    public void setNamr(String namr) {
        this.namr = namr;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Student)) return false;
        Student student = (Student) o;
        return id == student.id && Objects.equals(namr, student.namr) && Objects.equals(age, student.age);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, namr, age);
    }
}
