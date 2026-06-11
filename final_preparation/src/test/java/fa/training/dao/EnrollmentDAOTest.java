package fa.training.dao;

import fa.training.dao.impl.CourseDAOImpl;
import fa.training.dao.impl.EnrollmentDAOImpl;
import fa.training.dao.impl.StudentDAOImpl;
import fa.training.entities.Course;
import fa.training.entities.Enrollment;
import fa.training.entities.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class EnrollmentDAOTest extends AbstractDAOTest {

    private EnrollmentDAO enrollmentDAO;
    private Student testStudent;
    private Course testCourse;

    @BeforeEach
    void setUp() {
        cleanTable("enrollments");
        cleanTable("students");
        cleanTable("courses");

        enrollmentDAO = new EnrollmentDAOImpl();

        StudentDAO studentDAO = new StudentDAOImpl();
        testStudent = new Student("EnrollStudent", "enroll@example.com", "777",
                LocalDate.of(2000, 1, 1), "Addr");
        studentDAO.save(testStudent);

        CourseDAO courseDAO = new CourseDAOImpl();
        testCourse = new Course("EnrollCourse", "Desc", 3, 40, new BigDecimal("499"));
        courseDAO.save(testCourse);
    }

    @Test
    void testSaveAndFindById() {
        Enrollment enrollment = new Enrollment(testStudent, testCourse, LocalDate.now(), "ACTIVE");
        enrollmentDAO.save(enrollment);
        assertNotNull(enrollment.getId());

        Optional<Enrollment> found = enrollmentDAO.findById(enrollment.getId());
        assertTrue(found.isPresent());
        assertEquals("ACTIVE", found.get().getStatus());
        assertEquals(testStudent.getId(), found.get().getStudent().getId());
        assertEquals(testCourse.getId(), found.get().getCourse().getId());
    }

    @Test
    void testFindAll() {
        enrollmentDAO.save(new Enrollment(testStudent, testCourse, LocalDate.now(), "ACTIVE"));

        List<Enrollment> enrollments = enrollmentDAO.findAll();
        assertTrue(enrollments.size() >= 1);
    }

    @Test
    void testUpdate() {
        Enrollment enrollment = new Enrollment(testStudent, testCourse, LocalDate.now(), "PENDING");
        enrollmentDAO.save(enrollment);

        enrollment.setStatus("COMPLETED");
        enrollmentDAO.update(enrollment);

        Optional<Enrollment> found = enrollmentDAO.findById(enrollment.getId());
        assertTrue(found.isPresent());
        assertEquals("COMPLETED", found.get().getStatus());
    }

    @Test
    void testDelete() {
        Enrollment enrollment = new Enrollment(testStudent, testCourse, LocalDate.now(), "ACTIVE");
        enrollmentDAO.save(enrollment);
        int id = enrollment.getId();

        enrollmentDAO.delete(enrollment);

        Optional<Enrollment> found = enrollmentDAO.findById(id);
        assertFalse(found.isPresent());
    }

    @Test
    void testFindByProperty() {
        enrollmentDAO.save(new Enrollment(testStudent, testCourse, LocalDate.now(), "DROPPED"));

        List<Enrollment> result = enrollmentDAO.findByProperty("status", "DROPPED");
        assertEquals(1, result.size());
    }

    @Test
    void testFindByIdWithFetch() {
        Enrollment enrollment = new Enrollment(testStudent, testCourse, LocalDate.now(), "ACTIVE");
        enrollmentDAO.save(enrollment);

        Optional<Enrollment> found = enrollmentDAO.findByIdWithFetch(enrollment.getId(), "student", "course");
        assertTrue(found.isPresent());
        assertNotNull(found.get().getStudent().getFullName());
        assertNotNull(found.get().getCourse().getName());
    }

    @Test
    void testFindAllWithFetch() {
        enrollmentDAO.save(new Enrollment(testStudent, testCourse, LocalDate.now(), "ACTIVE"));

        List<Enrollment> enrollments = enrollmentDAO.findAllWithFetch("student", "course");
        assertFalse(enrollments.isEmpty());
        assertNotNull(enrollments.get(0).getStudent().getFullName());
        assertNotNull(enrollments.get(0).getCourse().getName());
    }

    @Test
    void testFindByIdNotFound() {
        Optional<Enrollment> found = enrollmentDAO.findById(-1);
        assertFalse(found.isPresent());
    }
}
