package com.learn.jpaindepth.repository;

import com.learn.jpaindepth.entity.Course;
import com.learn.jpaindepth.entity.Review;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Queue;

@Repository
@Transactional
public class CourseRepository {

    Logger LOGGER = LoggerFactory.getLogger(this.getClass());

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

    // playing with entity manager

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

    // using name query / queries

    public List<Course> usingNamedQuery() {
        return em.createNamedQuery("query_all_courses", Course.class).getResultList();
    }

    public List<Course> usingNameQueries() {
        return em.createNamedQuery("query_for_selecting_100_steps", Course.class).getResultList();
    }

    // using native queries
    public List<Course> usingNativeQuery() {
        return em.createNativeQuery("select * from course", Course.class).getResultList();
    }

    public List<Course> usingNativeQueryWithParameters() {
        Query query = em.createNativeQuery("select 8 from course where id = ?", Course.class);
        // we are setting the value of first "?" parameter
        query.setParameter(1, 10);
        return query.getResultList();
    }

    public void addReviewForCourses(int id, List<Review> reviews) {
        // getting the course
        Course course = findById(id);

        for (Review review: reviews) {
            // getting the reviews
            course.addReview(review);

            // setting the relation with the course
            review.setCourse(course);

            // saving into the database
            em.persist(review);
        }

    }
}
