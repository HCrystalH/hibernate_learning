package fa.training.dao.impl;

import fa.training.dao.EnrollmentDAO;
import fa.training.entities.Enrollment;

public class EnrollmentDAOImpl extends AbstractIDAOImpl<Enrollment, Integer> implements EnrollmentDAO {
    public EnrollmentDAOImpl() {
        super(Enrollment.class);
    }
}
