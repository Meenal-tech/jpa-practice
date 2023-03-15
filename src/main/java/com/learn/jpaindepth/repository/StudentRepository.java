package com.learn.jpaindepth.repository;

import com.learn.jpaindepth.entity.Course;
import com.learn.jpaindepth.entity.Passport;
import com.learn.jpaindepth.entity.Student;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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

    // using JPQL for querying the data
    // like, is null, between 100 and 1000, upper, trim, lower, length <- these utility methods can be used in JPQL
    public void jpqlCoursesWithoutStudents() {
        Query query = em.createQuery("select c from Course c where c.students is empty");
        List<Course> list = query.getResultList();
        System.out.println("#### Courses with no Students -> " + list.toString());
    }

    public void jpqlCoursesWithAtleast2Students() {
        Query query = em.createQuery("select c from Course c where size(c.students) >= 2");
        List<Course> list = query.getResultList();
        System.out.println("#### Courses with students >= 2 " + list.toString());
    }

    public void jpqlCoursesOrderedByStudents() {
        Query query = em.createQuery("select c from Course c order by size(c.students) desc");
        List<Course> courses = query.getResultList();
        System.out.println("#### Courses ordered by number of students " + courses.toString());
    }

    public void jpqlStudentsPassportWithSpecificPattern() {
        TypedQuery<Student> query = em.createQuery("select s.name from Student s where s.passport.number like '%1234%'",
                Student.class);
        List<Student> students = query.getResultList();
        System.out.println("#### Students with specific pattern of passport " + students.toString());
    }

    public void join() {
        Query query = em.createQuery("select c, s from Course c join c.students s");
        List<Object[]> list = query.getResultList();
        for (Object[] objects : list) {
            System.out.println("#join Course = " + objects[0] + " Student = " + objects[1]);
        }
    }

    public void leftJoin() {
        Query query = em.createQuery("select c, s from Course c left join c.students s");
        List<Object[]> list = query.getResultList();
        for (Object[] objects : list) {
            System.out.println("#leftJoin Course = " + objects[0] + " Student = " + objects[1]);
        }
    }

    // Criteria Query --->>>>

    public void criteriaBuilder() {

        // 1. build the criteria builder
        CriteriaBuilder cb = em.getCriteriaBuilder();

        // 2. use the criteria builder to create the criteria query
        CriteriaQuery<Course> cq = cb.createQuery(Course.class);

        // 3. define roots for the tables which are involved in the query,
        // here we need to fetch the data from Course table
        Root<Course> courseRoot = cq.from(Course.class);

        // 4. create typed query using the root and entity manager
        TypedQuery<Course> query = em.createQuery(cq.select(courseRoot));
        List<Course> queryResultList = query.getResultList();
        System.out.println("#criteriaBuilder -> " + queryResultList);

    }

    public void getCriteriaQueryForStringPatternMatching() {

        // 1. create criteria builder
        CriteriaBuilder cb = em.getCriteriaBuilder();

        // 2. create criteria query using criteria builder
        CriteriaQuery<Course> cq = cb.createQuery(Course.class);

        // 3. create course root using cq
        Root<Course> courseRoot = cq.from(Course.class);

        // 4. define predicate using cb,
        // first parameter would be the column name of the table,
        // and second column would be the expected string for pattern matching

        Predicate like100StepsPredicate = cb.like(courseRoot.get("name"), "%100 Steps");

        // 5. add predicate etc to criteria query
        cq.where(like100StepsPredicate);

        // 6. build typed query
        em.createQuery(cq.select(courseRoot));
    }

    public void getCoursesWithNoStudents() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Course> cq = cb.createQuery(Course.class);

        Root<Course> courseRoot = cq.from(Course.class);

        Predicate coursesWithNoStudents = cb.isEmpty(courseRoot.get("students"));
        cq.where(coursesWithNoStudents);

        TypedQuery<Course> typedQuery = em.createQuery(cq.select(courseRoot));

        System.out.println("#getCoursesWithNoStudents -> " + typedQuery.getResultList().toString());
    }

    public void joinUsingCriteriaQuery() {

        CriteriaBuilder cb = em.getCriteriaBuilder();

        CriteriaQuery<Course> cq = cb.createQuery(Course.class);

        Root<Course> courseRoot = cq.from(Course.class);

        Join<Object, Object> studentsJoin = courseRoot.join("students", JoinType.LEFT);

        TypedQuery<Course> courseTypedQuery = em.createQuery(cq.select(courseRoot));

        System.out.println("#joinUsingCriteriaQuery -> " + courseTypedQuery.getResultList());
    }

}
