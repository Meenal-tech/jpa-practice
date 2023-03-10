package com.learn.jpaindepth.repository;

import com.learn.jpaindepth.entity.Course;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public class CourseRepository {

    @Autowired
    EntityManager em;

    public Course findById(int id) {
        return em.find(Course.class, id);
    }

    public void deleteById(int id) {
        Course c = findById(id);
        em.remove(c);
    }

    public Course save(Course c) {
        if (c.getId() == 0) {
            em.persist(c);
            System.out.println("id = 0 code got executed");
        } else {
            em.merge(c);
            System.out.println("#save else condition got executed");
        }
        return c;
    }

}
