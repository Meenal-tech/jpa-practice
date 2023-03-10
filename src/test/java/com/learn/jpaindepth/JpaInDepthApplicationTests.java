package com.learn.jpaindepth;

import com.learn.jpaindepth.entity.Course;
import com.learn.jpaindepth.repository.CourseRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class JpaInDepthApplicationTests {

    @Autowired
    CourseRepository repository;

    @Test
    void contextLoads() {
    }

    @Test
    @DirtiesContext
    public void findById_basic() {
        Course c = repository.findById(10);
        assertEquals("Spring", c.getName());
    }

    @Test
    @DirtiesContext
    public void deleteById_basic() {
        repository.deleteById(10);
        Course c = repository.findById(10);
        assertNull(c);
    }


    @DirtiesContext
    @Test
    public void save_basic() {

        // get a course
        Course course1 = repository.findById(20);
        assertEquals("springboot", course1.getName());

        // update the details
        course1.setName("updated-springboot");
        repository.save(course1);

        //check the value
        Course course2 = repository.findById(20);
        assertEquals("updated-springboot", course2.getName());

    }

}
