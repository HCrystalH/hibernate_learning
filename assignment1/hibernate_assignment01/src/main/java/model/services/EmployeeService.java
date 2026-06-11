package model.services;

import model.dao.EmployeeDAO;
import model.dao.impl.EmployeeDAOImpl;
import model.entities.Employee;
import java.util.List;

public class EmployeeService {
    EmployeeDAO employeeDAO;

    public EmployeeService() {
        employeeDAO = new EmployeeDAOImpl(Employee.class);
    }

     public void addEmployee(Employee employee){
        try {
            employeeDAO.save(employee);
        } catch (Exception e) {
            throw new RuntimeException("Failed to add employee " + employee + ": " + e.getMessage(), e);
        }
     }

     public List<Employee> getAllEmployees(){
            try {
                return employeeDAO.findAll();
            } catch (Exception e) {
                throw new RuntimeException("Failed to fetch employees", e);
            }
     }

     public Employee getEmployeeById(Integer id){
         return employeeDAO.findById(id).orElse(null);
     }

     public void updateEmployee(Integer employeeId, String firstName, String lastName){
         try {
             Employee employee = getEmployeeById(employeeId);

             if(employee == null){
                 throw new RuntimeException("Employee not found: " + employeeId);
             }

             employee.setFirstName(firstName);
             employee.setLastName(lastName);

             employeeDAO.update(employee);
         } catch (Exception e) {
             throw new RuntimeException("Failed to update employee: " + employeeId, e);
         }
     }

     public void deleteEmployee(Integer employeeId){
         try {
            Employee employee = getEmployeeById (employeeId);
            if(employee == null){
                throw new RuntimeException("Employee not found: " + employeeId);
            }
            employeeDAO.delete(employee);
         } catch (Exception e) {
             throw new RuntimeException("Failed to delete employee " + employeeId, e);
         }
     }
}
