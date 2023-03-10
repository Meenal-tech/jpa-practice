package com.learn.jpaindepth.entity;

import jakarta.persistence.*;

@Entity
@NamedQueries(value = {
        @NamedQuery(name = "query_for_selecting_100_steps",
                query = "select c from Course c where name like '%100 steps'"),
        @NamedQuery(name = "query_all_courses", query = "select c from Course c")
})
public class Course {

    @Id
    @GeneratedValue
    private int id;
    private String name;

    protected Course() {
    }

    public Course(int id, String name) {
        this.name = name;
        this.id = id;
    }

    public Course(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
