package fa.training.dao;

import fa.training.dao.impl.StudentDAOImpl;
import fa.training.entities.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class StudentDAOTest extends AbstractDAOTest {

    private StudentDAO studentDAO;

    @BeforeEach
    void setUp() {
        studentDAO = new StudentDAOImpl();
    }

    @Test
    void testSaveAndFindById() {
        Student student = new Student("John Doe", "john@example.com", "123456789",
                LocalDate.of(2000, 1, 15), "123 Main St");
        studentDAO.save(student);
        assertNotNull(student.getId());

        Optional<Student> found = studentDAO.findById(student.getId());
        assertTrue(found.isPresent());
        assertEquals("John Doe", found.get().getFullName());
        assertEquals("john@example.com", found.get().getEmail());
    }

    @Test
    void testFindAll() {
        studentDAO.save(new Student("Alice", "alice@example.com", "111",
                LocalDate.of(1999, 5, 10), "Addr 1"));
        studentDAO.save(new Student("Bob", "bob@example.com", "222",
                LocalDate.of(2001, 3, 20), "Addr 2"));

        List<Student> students = studentDAO.findAll();
        assertTrue(students.size() >= 2);
    }

    @Test
    void testUpdate() {
        Student student = new Student("Original", "orig@example.com", "333",
                LocalDate.of(1998, 7, 5), "Old Addr");
        studentDAO.save(student);

        student.setFullName("Updated");
        studentDAO.update(student);

        Optional<Student> found = studentDAO.findById(student.getId());
        assertTrue(found.isPresent());
        assertEquals("Updated", found.get().getFullName());
    }

    @Test
    void testDelete() {
        Student student = new Student("ToDelete", "delete@example.com", "444",
                LocalDate.of(1997, 12, 25), "Delete Addr");
        studentDAO.save(student);
        int id = student.getId();

        studentDAO.delete(student);

        Optional<Student> found = studentDAO.findById(id);
        assertFalse(found.isPresent());
    }

    @Test
    void testFindByProperty() {
        studentDAO.save(new Student("FindMe", "findme@example.com", "555",
                LocalDate.of(2002, 8, 30), "Find Addr"));

        List<Student> result = studentDAO.findByProperty("email", "findme@example.com");
        assertEquals(1, result.size());
        assertEquals("FindMe", result.get(0).getFullName());
    }

    @Test
    void testFindByIdNotFound() {
        Optional<Student> found = studentDAO.findById(-1);
        assertFalse(found.isPresent());
    }
}
