package com.learn.jpaindepth;

import com.learn.jpaindepth.entity.Course;
import com.learn.jpaindepth.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class JpaInDepthApplication implements CommandLineRunner {

    @Autowired
    CourseRepository repository;

    public static void main(String[] args) {
        SpringApplication.run(JpaInDepthApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        repository.findById(1);
//        repository.deleteById(3);
        repository.save(new Course(20, "JPA"));
        repository.save(new Course("save-springboot"));
    }
}
