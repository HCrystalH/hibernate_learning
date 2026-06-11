package fa.training.dao.impl;

import fa.training.dao.StudentCourseDAO;
import fa.training.entities.manyToMany.Course;
import fa.training.entities.manyToMany.Student;
import fa.training.entities.manyToMany.StudentCourse;
import fa.training.entities.manyToMany.StudentCourseId;
import fa.training.utils.HibernateUtil;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Root;
import org.hibernate.*;

import java.time.LocalDate;
import java.util.List;

public class StudentCourseDAOImpl extends AbstractIDAOImpl<StudentCourse, StudentCourseId> implements StudentCourseDAO {
    protected StudentCourseDAOImpl(Class<StudentCourse> persistentClass) {
        super(persistentClass);
    }

    /*
     * ENROLL STUDENT
     */
    public void enrollStudent(Student student, Course course, double score) {
        Transaction tx = null;
        try(Session session = HibernateUtil.getSessionFactory().getCurrentSession()) {
            tx = session.beginTransaction();

            Student managedStudent = session.get(Student.class, student.getId());

            Course managedCourse = session.get(Course.class, course.getId());

            StudentCourse studentCourse = new StudentCourse(managedStudent, managedCourse, LocalDate.now(), score);

            session.persist(studentCourse);

            tx.commit();
        } catch (Exception e) {
            if(tx != null) tx.rollback();
            throw new  RuntimeException(e);
        }
    }

    /*
     * QUERY COURSES OF STUDENT
     */
    public List<Course> getCoursesOfStudent(Long studentId) {

        try(Session session = HibernateUtil.getSessionFactory().getCurrentSession()) {
            String hql = """
                SELECT sc.course
                FROM StudentCourse sc
                WHERE sc.student.id = :studentId
            """;

            return session.createQuery(hql, Course.class)
                    .setParameter("studentId", studentId)
                    .list();
        }
    }

    /*
     * QUERY STUDENTS BY COURSE
     */
    public List<Student> getStudentsByCourse(String courseName) {
        try(Session session = HibernateUtil.getSessionFactory().getCurrentSession()) {
            String hql = """
                SELECT sc.student
                FROM StudentCourse sc
                WHERE sc.course.name = :courseName
            """;

            return session.createQuery(hql,Student.class)
                    .setParameter("courseName", courseName)
                    .list();
        }
    }

    /*
     * QUERY BY SCORE
     * USING NAMED QUERY
     */
    public List<StudentCourse> findByScore(double score) {

        try(Session session = HibernateUtil.getSessionFactory().getCurrentSession()) {

            return session.createNamedQuery("StudentCourse.findByScore", StudentCourse.class)
                    .setParameter("score", score)
                    .list();
        }
    }

    /**
     * CRITERIA JOIN QUERY
     * SELECT
     *     sc.student_id,
     *     sc.course_id,
     *     sc.enroll_date,
     *     sc.score
     * FROM student_course sc
     * INNER JOIN   courses c
     * ON sc.course_id = c.id
     * WHERE c.name = ?
     */
    public List<StudentCourse> findByCourseUsingCriteria(String courseName) {

        try(Session session = HibernateUtil.getSessionFactory().getCurrentSession()) {

            CriteriaBuilder cb = session.getCriteriaBuilder();

            CriteriaQuery<StudentCourse> cq = cb.createQuery(StudentCourse.class);

            Root<StudentCourse> root = cq.from(StudentCourse.class);

            /**
             * INNER JOIN courses c
             * ON sc.course_id = c.id
             */
            Join<StudentCourse, Course> courseJoin = root.join("course");

            cq.select(root)
                .where( cb.equal(courseJoin.get("name"), courseName)    );

            return session.createQuery(cq).getResultList();
        }
    }
}
