import controller.EmployeeController;
import model.utils.Validator;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
   Main - acts as VIEW IN MVC

 */
public class Main {
    private static final EmployeeController employeeController = new EmployeeController();
    private static final Scanner scanner = new Scanner(System.in);
    private static final Map<Integer,Runnable> actions = new HashMap<>();

    public static void main(String[] args) {
        init();
        while(true){
            displayMenu();
            String input = scanner.nextLine().trim();

            int option;
            if(!Validator.isValidNumber(input)){
                System.out.println("Please enter a number");
                continue;
            }
            option = Integer.parseInt(input);

            actions.getOrDefault(
                    option,
                    () -> System.out.println("Invalid Option! Please chose the valid one")
            ).run();
        }
    }

    private static void displayMenu(){
        System.out.println("====== EMPLOYEE MANAGEMENT SYSTEM ======");
        System.out.println("1. Add an employee");
        System.out.println("2. List workers");
        System.out.println("3. Update employee by ID");
        System.out.println("4. Delete employee by ID");
        System.out.println("5. Exit");
        System.out.print("Please choose function you'd like to do: ");
    }

    private static void init(){
        // Init function
        actions.put(1,Main::addEmployee);
        actions.put(2,Main::listEmployees);
        actions.put(3,Main::updateEmployee);
        actions.put(4,Main::deleteEmployee);
        actions.put(5,() ->System.exit(0));
    }

    private static void addEmployee()  {
        System.out.println("------Enter Worker Information------");
        System.out.print("Enter First Name: ");
        String firstName = scanner.nextLine().trim();

        System.out.print("Enter Last Name: ");
        String lastName = scanner.nextLine().trim();

        employeeController.addEmployee(firstName, lastName);
    }

    private static void listEmployees()  {
        System.out.println("------ List Employee ------");
        employeeController.listEmployees();
    }

    private static void updateEmployee()  {
        System.out.println("------Enter Worker Information------");
        System.out.print("Enter Employee ID: ");
        String id = scanner.nextLine().trim();

        System.out.print("Enter New First Name: ");
        String firstName = scanner.nextLine().trim();
        System.out.print("Enter New Last Name: ");
        String lastName = scanner.nextLine().trim();

        if(!Validator.isValidNumber(id)){
            System.out.println("Please enter a number");
            return;
        }
        employeeController.updateEmployee(Integer.parseInt(id), firstName, lastName);
    }

    private static void deleteEmployee()  {
        System.out.print("Enter Employee ID: ");
        String id = scanner.nextLine().trim();

        if(!Validator.isValidNumber(id)){
            System.out.println("Please enter a number");
            return;
        }
        employeeController.deleteEmployee(Integer.parseInt(id));
    }
}
