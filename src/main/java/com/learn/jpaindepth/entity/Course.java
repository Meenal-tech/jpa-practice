package com.learn.jpaindepth.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@NamedQueries(value = {
        @NamedQuery(name = "query_for_selecting_100_steps",
                query = "select c from Course c where name like '%100 steps'"),
        @NamedQuery(name = "query_all_courses", query = "select c from Course c")
})
public class Course {

    @Id
    @GeneratedValue
    private Integer id;
    private String name;

    @OneToMany(mappedBy = "course")
    private List<Review> reviews = new ArrayList<>();

    @ManyToMany(mappedBy = "courses")
    private List<Student> students = new ArrayList<>();

    public List<Student> getStudents() {
        return students;
    }

    public void addStudent(Student student) {
        this.students.add(student);
    }

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

    public List<Review> getReviews() {
        return reviews;
    }

    public void addReview(Review review) {
        this.reviews.add(review);
    }

    public void removeReviews(Review review) {
        this.reviews.remove(review);
    }
}
