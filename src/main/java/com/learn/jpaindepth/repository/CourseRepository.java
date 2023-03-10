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

    public void playingWithEntityManager() {
        Course c1 = new Course("Angular in 100 steps");
        save(c1);
        em.flush();
        Course c2 = new Course("React in 100 steps");
        save(c2);

        // detach make the em stop tracking any changes which we make.
        em.detach(c1);

        // this will make the em stop tracking all the objects / instance which it is tracking.
        em.clear();

        c1.setName("angular-updated");
        c2.setName("react-updated");
    }

}
