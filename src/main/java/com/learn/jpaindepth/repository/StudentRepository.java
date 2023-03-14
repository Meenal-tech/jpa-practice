package com.learn.jpaindepth.repository;

import com.learn.jpaindepth.entity.Course;
import com.learn.jpaindepth.entity.Passport;
import com.learn.jpaindepth.entity.Student;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public class StudentRepository {

    @Autowired
    EntityManager em;


   /* @PersistenceContext(type = PersistenceContextType.EXTENDED)
    EntityManager entityManager;
     This entity manager is shared across the whole application
	 so we can't write in an transaction because in between some other operation
	 can be performed over the shared entity manager although we can use type = PersistenceContext.Extended
	 which allows us to persist the data to the PersistentContext without the transaction but
	 for flushing it to the PersistentStorage we have to wrap up it into an transaction.
*/
    public Student findById(int id) {
        return em.find(Student.class, id);
    }

    public void deleteById(int id) {
        Student c = findById(id);
        em.remove(c);
    }

    public Student save(Student c) {
        if (c.getId() == 0) {
            em.persist(c);
            System.out.println("id = 0 code got executed");
        } else {
            em.merge(c);
            System.out.println("#save else condition got executed");
        }
        return c;
    }

    public void saveStudentWithPassport() {
        Passport passport = new Passport("Z12345");
        em.persist(passport);
        // saving the passport object so that it gets saved into database, and then student is persisted.
        Student student = new Student("Mike");
        student.setPassport(passport);
        em.persist(student);
    }


    public Passport getPassportDetailsByStudent() {
        Student student = em.find(Student.class, 1);
        TypedQuery<Student> query = em.createQuery("select s from Student s left join fetch s.passport where s.id = 1", Student.class);
        List<Student> resultList = query.getResultList();
        return resultList.get(0).getPassport();
    }

    public Student getStudentDetailsFromPassport() {
        Passport passport  = em.find(Passport.class, 201);
        Student student = passport.getStudent();
        return student;
    }

    // @ManyToMany relation b/w Student and Course
    public void getCoursesFromStudent() {
        Student student = em.find(Student.class, 301);
        System.out.println("#### -> " + student.getCourses());
    }

    public void saveStudentAndCourse(Student student, Course course) {
        student.addCourse(course);
        course.addStudent(student);
        em.persist(student);
        em.persist(course);
    }
}
