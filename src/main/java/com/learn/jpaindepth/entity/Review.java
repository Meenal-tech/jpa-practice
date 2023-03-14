package com.learn.jpaindepth.entity;

import jakarta.persistence.*;

@Entity
public class Review {

    @Id
    @GeneratedValue
    private int id;
    private String rating;
    private String description;

    @ManyToOne()
    private Course course;

    protected Review() {
    }

    public Review(String description, String rating) {
        this.rating = rating;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    @Override
    public String toString() {
        return "Review{" +
                "id=" + id +
                ", description='" + description + '\'' +
                "rating=" + rating +
                '}';
    }
}
