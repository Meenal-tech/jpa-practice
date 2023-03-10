package com.learn.jpaindepth;

import com.learn.jpaindepth.entity.Course;
import com.learn.jpaindepth.repository.CourseRepository;
import org.apache.juli.logging.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class JpaInDepthApplication implements CommandLineRunner {

    Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    CourseRepository repository;

    public static void main(String[] args) {
        SpringApplication.run(JpaInDepthApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
       LOGGER.info("findById -> " + repository.findById(1));
//       repository.deleteById(3);
       LOGGER.info("save -> " + repository.save(new Course(20, "JPA")));
       repository.save(new Course("save-springboot"));
       repository.playingWithEntityManager();
       LOGGER.info("using named query -> " + repository.usingNamedQuery().toString());
       LOGGER.info("using named queries -> " + repository.usingNameQueries().toString());
       LOGGER.info("using native queries -> " + repository.usingNativeQuery().toString());
       LOGGER.info("using native queries with parameters -> " + repository.usingNativeQuery().toString());
    }
}
