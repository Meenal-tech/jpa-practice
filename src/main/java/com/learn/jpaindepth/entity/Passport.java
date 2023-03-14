package com.learn.jpaindepth.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "passport")
public class Passport {

    @Id
    @GeneratedValue
    private int id;
    private String number;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "passport")
    private Student student;

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    protected Passport() {
    }

    public Passport(String name) {
        this.number = name;
    }

    public int getId() {
        return id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String name) {
        this.number = name;
    }

    @Override
    public String toString() {
        return "Passport{" +
                "id=" + id +
                ", number='" + number + '\'' +
                '}';
    }
}
