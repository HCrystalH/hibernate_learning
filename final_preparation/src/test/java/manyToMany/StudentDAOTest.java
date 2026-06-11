package manyToMany;

import fa.training.dao.StudentDAO;
import fa.training.dao.impl.StudentDAOImpl;
import fa.training.entities.manyToMany.Student;
import org.junit.jupiter.api.*;

public class StudentDAOTest
        extends AbstractDAOTest {

    private StudentDAO studentDAO;

    @BeforeEach
    void init() {

        studentDAO = new StudentDAOImpl();
    }

    @Test
    void shouldSaveStudent() {

        Student student =
                TestDataFactory.createStudent();

        studentDAO.save(student);

        assertNotNull(student.getId());
    }

    @Test
    void shouldFindStudentById() {

        Student student =
                TestDataFactory.createStudent();

        studentDAO.save(student);

        Student found =
                studentDAO.findById(student.getId());

        assertNotNull(found);

        assertEquals(
                "John",
                found.getName()
        );
    }

    @Test
    void shouldUpdateStudent() {

        Student student =
                TestDataFactory.createStudent();

        studentDAO.save(student);

        student.setName("Alice");

        studentDAO.update(student);

        Student updated =
                studentDAO.findById(student.getId());

        assertEquals(
                "Alice",
                updated.getName()
        );
    }

    @Test
    void shouldDeleteStudent() {

        Student student =
                TestDataFactory.createStudent();

        studentDAO.save(student);

        studentDAO.delete(student);

        Student deleted =
                studentDAO.findById(student.getId());

        assertNull(deleted);
    }
}