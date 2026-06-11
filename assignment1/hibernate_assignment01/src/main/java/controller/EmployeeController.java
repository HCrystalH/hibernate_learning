package controller;

import model.entities.Employee;
import model.services.EmployeeService;

public class EmployeeController {
    private final EmployeeService employeeService = new EmployeeService();

    public EmployeeController() {
    }

    public void addEmployee(String firstName, String lastName){
        employeeService.addEmployee(new Employee(firstName, lastName));
    }

    public void listEmployees(){
        employeeService.getAllEmployees().forEach(System.out::println);
    }

    public void updateEmployee(Integer employeeId, String firstName, String lastName){
        employeeService.updateEmployee(employeeId, firstName, lastName);
    }

    public void deleteEmployee(Integer employeeId){
        employeeService.deleteEmployee(employeeId);
    }

}
