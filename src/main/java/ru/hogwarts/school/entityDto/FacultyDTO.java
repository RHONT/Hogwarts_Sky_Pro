package ru.hogwarts.school.entityDto;


import ru.hogwarts.school.model.Faculty;

import java.util.Objects;


public class FacultyDTO {
    private Long id;
    private String name;
    private String color;


    public FacultyDTO(Faculty faculty) {
        id = faculty.getId();
        name = faculty.getName();
        color = faculty.getColor();
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FacultyDTO)) return false;
        FacultyDTO faculty = (FacultyDTO) o;
        return id == faculty.id && Objects.equals(name, faculty.name) && Objects.equals(color, faculty.color);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, color);
    }

    @Override
    public String toString() {
        return "Faculty{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", color='" + color + '\'' +
                '}';
    }
}
