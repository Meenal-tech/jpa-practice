package com.learn.jpaindepth;

import com.learn.jpaindepth.entity.Course;
import com.learn.jpaindepth.entity.Review;
import com.learn.jpaindepth.entity.Student;
import com.learn.jpaindepth.repository.CourseRepository;
import com.learn.jpaindepth.repository.StudentRepository;
import jakarta.transaction.Transactional;
import org.apache.juli.logging.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;

@SpringBootApplication
public class JpaInDepthApplication implements CommandLineRunner {

    Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    CourseRepository courseRepository;

    @Autowired
    StudentRepository studentRepository;

    public static void main(String[] args) {
        SpringApplication.run(JpaInDepthApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
       LOGGER.info("findById -> " + courseRepository.findById(1));
//        courseRepository.deleteById(3);
       LOGGER.info("save -> " + courseRepository.save(new Course(20, "JPA")));
        courseRepository.save(new Course("save-springboot"));
        courseRepository.playingWithEntityManager();
       LOGGER.info("using named query -> " + courseRepository.usingNamedQuery().toString());
       LOGGER.info("using named queries -> " + courseRepository.usingNameQueries().toString());
       LOGGER.info("using native queries -> " + courseRepository.usingNativeQuery().toString());
       LOGGER.info("using native queries with parameters -> " + courseRepository.usingNativeQuery().toString());

       // @OneToOne : Student and Passport
        studentRepository.saveStudentWithPassport();
        LOGGER.info("findById -> " + studentRepository.findById(301));
        LOGGER.info("passport -> " + studentRepository.getPassportDetailsByStudent());
        LOGGER.info("student -> " + studentRepository.getStudentDetailsFromPassport());

        // @OneToMany : Course and Reviews
        courseRepository.addReviewForCourses(20, Arrays.asList((new Review("Great Hands-on Stuff", "4")),
                (new Review("Hats-Off!", "5"))));

        // @ManyToMany : Course and Student
        studentRepository.getCoursesFromStudent();
        studentRepository.saveStudentAndCourse(new Student("lisa"), new Course("lisa-course"));
        studentRepository.jpqlCoursesWithoutStudents();
        studentRepository.jpqlCoursesWithAtleast2Students();
        studentRepository.jpqlCoursesOrderedByStudents();
        studentRepository.jpqlStudentsPassportWithSpecificPattern();
        studentRepository.join();
        studentRepository.leftJoin();

        studentRepository.getCoursesWithNoStudents();
        studentRepository.joinUsingCriteriaQuery();

    }
}
