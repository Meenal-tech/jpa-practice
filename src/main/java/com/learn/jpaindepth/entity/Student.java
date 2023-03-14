package com.learn.jpaindepth.entity;

import jakarta.persistence.*;
import org.hibernate.FetchMode;
import org.hibernate.annotations.Fetch;
import org.springframework.data.util.Lazy;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Student {

    @Id
    @GeneratedValue
    private int id;
    private String name;

    public Passport getPassport() {
        return passport;
    }

    public void setPassport(Passport passport) {
        this.passport = passport;
    }

    @OneToOne(fetch = FetchType.LAZY)
    private Passport passport;

    @ManyToMany
    // customizing the join table below
    @JoinTable(name = "student_course",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "course_id")
    )
    List<Course> courses = new ArrayList<>();

    protected Student() {
    }

    public Student(int id, String name) {
        this.name = name;
        this.id = id;
    }

    public void addCourse(Course course) {
        this.courses.add(course);
    }

    public void removeCourse(Course course) {
        this.courses.remove(course);
    }

    public List<Course> getCourses() {
        return courses;
    }

    public Student(String name) {
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
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
