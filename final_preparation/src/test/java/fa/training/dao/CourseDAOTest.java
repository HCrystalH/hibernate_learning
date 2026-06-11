package fa.training.dao;

import fa.training.dao.impl.CourseDAOImpl;
import fa.training.entities.Course;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class CourseDAOTest extends AbstractDAOTest {

    private CourseDAO courseDAO;

    @BeforeEach
    void setUp() {
        courseDAO = new CourseDAOImpl();
    }

    @Test
    void testSaveAndFindById() {
        Course course = new Course("Java 101", "Intro to Java", 3, 40, new BigDecimal("499.99"));
        courseDAO.save(course);
        assertNotNull(course.getId());

        Optional<Course> found = courseDAO.findById(course.getId());
        assertTrue(found.isPresent());
        assertEquals("Java 101", found.get().getName());
        assertEquals(3, found.get().getCredits());
    }

    @Test
    void testFindAll() {
        courseDAO.save(new Course("Course A", "Desc A", 2, 30, new BigDecimal("100")));
        courseDAO.save(new Course("Course B", "Desc B", 4, 60, new BigDecimal("200")));

        List<Course> courses = courseDAO.findAll();
        assertTrue(courses.size() >= 2);
    }

    @Test
    void testUpdate() {
        Course course = new Course("Old Name", "Old Desc", 3, 40, new BigDecimal("300"));
        courseDAO.save(course);

        course.setName("New Name");
        course.setFee(new BigDecimal("350"));
        courseDAO.update(course);

        Optional<Course> found = courseDAO.findById(course.getId());
        assertTrue(found.isPresent());
        assertEquals("New Name", found.get().getName());
        assertEquals(0, new BigDecimal("350").compareTo(found.get().getFee()));
    }

    @Test
    void testDelete() {
        Course course = new Course("Delete Me", "To be deleted", 1, 10, new BigDecimal("50"));
        courseDAO.save(course);
        int id = course.getId();

        courseDAO.delete(course);

        Optional<Course> found = courseDAO.findById(id);
        assertFalse(found.isPresent());
    }

    @Test
    void testFindByProperty() {
        courseDAO.save(new Course("Unique Course", "Unique Desc", 5, 80, new BigDecimal("999.99")));

        List<Course> result = courseDAO.findByProperty("name", "Unique Course");
        assertEquals(1, result.size());
        assertEquals("Unique Course", result.get(0).getName());
    }

    @Test
    void testFindByIdNotFound() {
        Optional<Course> found = courseDAO.findById(-1);
        assertFalse(found.isPresent());
    }
}
