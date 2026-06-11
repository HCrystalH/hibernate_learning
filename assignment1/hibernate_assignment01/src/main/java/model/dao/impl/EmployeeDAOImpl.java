package model.dao.impl;

import model.dao.EmployeeDAO;
import model.dao.AbstractGenericDAOImpl;
import model.entities.Employee;

public class EmployeeDAOImpl extends AbstractGenericDAOImpl<Employee, Integer> implements EmployeeDAO {
    public EmployeeDAOImpl(Class<Employee> entityClass) {
        super(entityClass);
    }

}
