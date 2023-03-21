package com.learn.jpaindepth.repository;

import com.learn.jpaindepth.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseJPARepository extends JpaRepository<Course, Integer> {
}
