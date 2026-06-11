package fa.training.controller;

import fa.training.dao.*;
import fa.training.dao.impl.*;
import fa.training.entities.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class EnrollmentController {
    private final EnrollmentDAO enrollmentDAO = new EnrollmentDAOImpl();
    private final StudentDAO studentDAO = new StudentDAOImpl();
    private final CourseDAO courseDAO = new CourseDAOImpl();

    public void create(Scanner scanner) {
        System.out.print("Enter student ID: ");
        int studentId = Integer.parseInt(scanner.nextLine());
        Optional<Student> studentOpt = studentDAO.findById(studentId);
        if (studentOpt.isEmpty()) {
            System.out.println("Student not found!");
            return;
        }
        System.out.print("Enter course ID: ");
        int courseId = Integer.parseInt(scanner.nextLine());
        Optional<Course> courseOpt = courseDAO.findById(courseId);
        if (courseOpt.isEmpty()) {
            System.out.println("Course not found!");
            return;
        }
        System.out.print("Enter enrollment date (yyyy-MM-dd) or leave blank for today: ");
        String dateStr = scanner.nextLine();
        LocalDate date = dateStr.isBlank() ? LocalDate.now() : LocalDate.parse(dateStr);
        System.out.print("Enter status (ACTIVE/DROPPED/COMPLETED): ");
        String status = scanner.nextLine();

        Enrollment enrollment = new Enrollment(studentOpt.get(), courseOpt.get(), date, status);
        enrollmentDAO.save(enrollment);
        System.out.println("Enrollment created with ID: " + enrollment.getId());
    }

    public void delete(Scanner scanner) {
        System.out.print("Enter enrollment ID to delete: ");
        int id = Integer.parseInt(scanner.nextLine());
        Optional<Enrollment> opt = enrollmentDAO.findById(id);
        if (opt.isEmpty()) {
            System.out.println("Enrollment not found!");
            return;
        }
        enrollmentDAO.delete(opt.get());
        System.out.println("Enrollment deleted!");
    }

    public void findById(Scanner scanner) {
        System.out.print("Enter enrollment ID: ");
        int id = Integer.parseInt(scanner.nextLine());
        Optional<Enrollment> opt = enrollmentDAO.findById(id);
        if (opt.isPresent()) {
            System.out.println(opt.get());
        } else {
            System.out.println("Enrollment not found!");
        }
    }

    public void listAll() {
        List<Enrollment> enrollments = enrollmentDAO.findAll();
        if (enrollments.isEmpty()) {
            System.out.println("No enrollments found.");
            return;
        }
        enrollments.forEach(System.out::println);
    }

    public void listByStudent(Scanner scanner) {
        System.out.print("Enter student ID: ");
        int studentId = Integer.parseInt(scanner.nextLine());
        List<Enrollment> enrollments = enrollmentDAO.findByProperty("student.id", studentId);
        if (enrollments.isEmpty()) {
            System.out.println("No enrollments found for student ID " + studentId);
            return;
        }
        enrollments.forEach(System.out::println);
    }

    public void listByCourse(Scanner scanner) {
        System.out.print("Enter course ID: ");
        int courseId = Integer.parseInt(scanner.nextLine());
        List<Enrollment> enrollments = enrollmentDAO.findByProperty("course.id", courseId);
        if (enrollments.isEmpty()) {
            System.out.println("No enrollments found for course ID " + courseId);
            return;
        }
        enrollments.forEach(System.out::println);
    }
}
