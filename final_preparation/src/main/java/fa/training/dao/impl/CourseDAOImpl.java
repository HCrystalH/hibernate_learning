package fa.training.dao.impl;

import fa.training.dao.CourseDAO;
import fa.training.entities.Course;

public class CourseDAOImpl extends AbstractIDAOImpl<Course, Integer> implements CourseDAO {
    public CourseDAOImpl() {
        super(Course.class);
    }
}
