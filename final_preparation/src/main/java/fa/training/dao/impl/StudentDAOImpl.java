package fa.training.dao.impl;

import fa.training.dao.StudentDAO;
import fa.training.entities.Student;

public class StudentDAOImpl extends AbstractIDAOImpl<Student, Integer> implements StudentDAO {
    public StudentDAOImpl() {
        super(Student.class);
    }
}
