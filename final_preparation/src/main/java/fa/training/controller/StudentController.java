package fa.training.controller;

import fa.training.dao.StudentDAO;
import fa.training.dao.impl.StudentDAOImpl;
import fa.training.entities.Student;
import fa.training.utils.Validator;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class StudentController {
    private final StudentDAO studentDAO = new StudentDAOImpl();

    public void create(Scanner scanner) {
        System.out.print("Enter full name: ");
        String name = scanner.nextLine();
        System.out.print("Enter email: ");
        String email = scanner.nextLine();
        if (!Validator.isValidEmail(email)) {
            System.out.println("Invalid email format!");
            return;
        }
        System.out.print("Enter phone (10 digits): ");
        String phone = scanner.nextLine();
        if (!Validator.isValidPhone(phone)) {
            System.out.println("Invalid phone format!");
            return;
        }
        System.out.print("Enter date of birth (yyyy-MM-dd): ");
        LocalDate dob = null;
        String dobStr = scanner.nextLine();
        if (!dobStr.isBlank()) {
            if (!Validator.isValidLocalDate(dobStr, "yyyy-MM-dd")) {
                System.out.println("Invalid date format!");
                return;
            }
            dob = LocalDate.parse(dobStr);
        }
        System.out.print("Enter address: ");
        String address = scanner.nextLine();

        Student student = new Student(name, email, phone, dob, address);
        studentDAO.save(student);
        System.out.println("Student created with ID: " + student.getId());
    }

    public void update(Scanner scanner) {
        System.out.print("Enter student ID to update: ");
        int id = Integer.parseInt(scanner.nextLine());
        Optional<Student> opt = studentDAO.findById(id);
        if (opt.isEmpty()) {
            System.out.println("Student not found!");
            return;
        }
        Student student = opt.get();
        System.out.print("Enter new name (" + student.getFullName() + "): ");
        String name = scanner.nextLine();
        if (!name.isBlank()) student.setFullName(name);
        System.out.print("Enter new email (" + student.getEmail() + "): ");
        String email = scanner.nextLine();
        if (!email.isBlank()) {
            if (!Validator.isValidEmail(email)) {
                System.out.println("Invalid email format!");
                return;
            }
            student.setEmail(email);
        }
        System.out.print("Enter new phone (" + student.getPhone() + "): ");
        String phone = scanner.nextLine();
        if (!phone.isBlank()) {
            if (!Validator.isValidPhone(phone)) {
                System.out.println("Invalid phone format!");
                return;
            }
            student.setPhone(phone);
        }
        System.out.print("Enter new address (" + student.getAddress() + "): ");
        String address = scanner.nextLine();
        if (!address.isBlank()) student.setAddress(address);

        studentDAO.update(student);
        System.out.println("Student updated!");
    }

    public void delete(Scanner scanner) {
        System.out.print("Enter student ID to delete: ");
        int id = Integer.parseInt(scanner.nextLine());
        Optional<Student> opt = studentDAO.findById(id);
        if (opt.isEmpty()) {
            System.out.println("Student not found!");
            return;
        }
        studentDAO.delete(opt.get());
        System.out.println("Student deleted!");
    }

    public void findById(Scanner scanner) {
        System.out.print("Enter student ID: ");
        int id = Integer.parseInt(scanner.nextLine());
        Optional<Student> opt = studentDAO.findById(id);
        if (opt.isPresent()) {
            System.out.println(opt.get());
        } else {
            System.out.println("Student not found!");
        }
    }

    public void listAll() {
        List<Student> students = studentDAO.findAll();
        if (students.isEmpty()) {
            System.out.println("No students found.");
            return;
        }
        students.forEach(System.out::println);
    }
}
