package manyToMany;

import fa.training.dao.StudentCourseDAO;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

public class StudentCourseDAOTest
        extends AbstractDAOTest {

    private StudentDAO studentDAO;

    private CourseDAO courseDAO;

    private StudentCourseDAO studentCourseDAO;

    @BeforeEach
    void init() {

        studentDAO = new StudentDAO();

        courseDAO = new CourseDAO();

        studentCourseDAO = new StudentCourseDAO();
    }

    @Test
    void shouldEnrollStudent() {

        Student student = new Student("John");

        Course course = new Course("Java");

        studentDAO.save(student);

        courseDAO.save(course);

        studentCourseDAO.enrollStudent(student, course, 95);

        StudentCourseId id = new StudentCourseId(student.getId(), course.getId());

        StudentCourse result = studentCourseDAO.findById(id);

        assertNotNull(result);

        assertEquals(95, result.getScore());
    }
}