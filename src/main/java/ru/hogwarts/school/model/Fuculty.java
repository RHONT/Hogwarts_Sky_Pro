package ru.hogwarts.school.model;

import java.util.Objects;

public class Fuculty {
    private long id;
    private String namr;
    private String color;

    public Fuculty(long id, String namr, String color) {
        this.id = id;
        this.namr = namr;
        this.color = color;
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

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Fuculty)) return false;
        Fuculty fuculty = (Fuculty) o;
        return id == fuculty.id && Objects.equals(namr, fuculty.namr) && Objects.equals(color, fuculty.color);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, namr, color);
    }
}
