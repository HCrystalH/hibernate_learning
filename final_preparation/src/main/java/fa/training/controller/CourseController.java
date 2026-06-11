package fa.training.controller;

import fa.training.dao.CourseDAO;
import fa.training.dao.impl.CourseDAOImpl;
import fa.training.entities.Course;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class CourseController {
    private final CourseDAO courseDAO = new CourseDAOImpl();

    public void create(Scanner scanner) {
        System.out.print("Enter course name: ");
        String name = scanner.nextLine();
        System.out.print("Enter description: ");
        String description = scanner.nextLine();
        System.out.print("Enter credits: ");
        int credits = Integer.parseInt(scanner.nextLine());
        System.out.print("Enter duration (hours): ");
        int hours = Integer.parseInt(scanner.nextLine());
        System.out.print("Enter fee: ");
        BigDecimal fee = new BigDecimal(scanner.nextLine());

        Course course = new Course(name, description, credits, hours, fee);
        courseDAO.save(course);
        System.out.println("Course created with ID: " + course.getId());
    }

    public void update(Scanner scanner) {
        System.out.print("Enter course ID to update: ");
        int id = Integer.parseInt(scanner.nextLine());
        Optional<Course> opt = courseDAO.findById(id);
        if (opt.isEmpty()) {
            System.out.println("Course not found!");
            return;
        }
        Course course = opt.get();
        System.out.print("Enter new name (" + course.getName() + "): ");
        String name = scanner.nextLine();
        if (!name.isBlank()) course.setName(name);
        System.out.print("Enter new description (" + course.getDescription() + "): ");
        String desc = scanner.nextLine();
        if (!desc.isBlank()) course.setDescription(desc);
        System.out.print("Enter new credits (" + course.getCredits() + "): ");
        String credits = scanner.nextLine();
        if (!credits.isBlank()) course.setCredits(Integer.parseInt(credits));
        System.out.print("Enter new duration hours (" + course.getDurationHours() + "): ");
        String hours = scanner.nextLine();
        if (!hours.isBlank()) course.setDurationHours(Integer.parseInt(hours));
        System.out.print("Enter new fee (" + course.getFee() + "): ");
        String fee = scanner.nextLine();
        if (!fee.isBlank()) course.setFee(new BigDecimal(fee));

        courseDAO.update(course);
        System.out.println("Course updated!");
    }

    public void delete(Scanner scanner) {
        System.out.print("Enter course ID to delete: ");
        int id = Integer.parseInt(scanner.nextLine());
        Optional<Course> opt = courseDAO.findById(id);
        if (opt.isEmpty()) {
            System.out.println("Course not found!");
            return;
        }
        courseDAO.delete(opt.get());
        System.out.println("Course deleted!");
    }

    public void findById(Scanner scanner) {
        System.out.print("Enter course ID: ");
        int id = Integer.parseInt(scanner.nextLine());
        Optional<Course> opt = courseDAO.findById(id);
        if (opt.isPresent()) {
            System.out.println(opt.get());
        } else {
            System.out.println("Course not found!");
        }
    }

    public void listAll() {
        List<Course> courses = courseDAO.findAll();
        if (courses.isEmpty()) {
            System.out.println("No courses found.");
            return;
        }
        courses.forEach(System.out::println);
    }
}
