package fa.training;

import fa.training.controller.*;

import java.util.*;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final StudentController studentController = new StudentController();
    private static final CourseController courseController = new CourseController();
    private static final EnrollmentController enrollmentController = new EnrollmentController();

    private static final Map<Integer, Runnable> mainActions = new LinkedHashMap<>();
    private static final Map<Integer, Runnable> studentActions = new LinkedHashMap<>();
    private static final Map<Integer, Runnable> courseActions = new LinkedHashMap<>();
    private static final Map<Integer, Runnable> enrollmentActions = new LinkedHashMap<>();
    private static final Map<Integer, Runnable> reportActions = new LinkedHashMap<>();

    public static void main(String[] args) {
        init();
        while (true) {
            displayMainMenu();
            int option = readOption(mainActions.size());
            mainActions.get(option).run();
        }
    }

    private static void init() {
        mainActions.put(1, Main::studentManagement);
        mainActions.put(2, Main::courseManagement);
        mainActions.put(3, Main::enrollmentManagement);
        mainActions.put(4, Main::queryAndReport);
        mainActions.put(5, () -> System.exit(0));

        studentActions.put(1, () -> studentController.create(scanner));
        studentActions.put(2, () -> studentController.update(scanner));
        studentActions.put(3, () -> studentController.delete(scanner));
        studentActions.put(4, () -> studentController.findById(scanner));
        studentActions.put(5, studentController::listAll);

        courseActions.put(1, () -> courseController.create(scanner));
        courseActions.put(2, () -> courseController.update(scanner));
        courseActions.put(3, () -> courseController.delete(scanner));
        courseActions.put(4, () -> courseController.findById(scanner));
        courseActions.put(5, courseController::listAll);

        enrollmentActions.put(1, () -> enrollmentController.create(scanner));
        enrollmentActions.put(2, () -> enrollmentController.delete(scanner));
        enrollmentActions.put(3, () -> enrollmentController.findById(scanner));
        enrollmentActions.put(4, enrollmentController::listAll);

        reportActions.put(1, () -> enrollmentController.listByCourse(scanner));
        reportActions.put(2, () -> enrollmentController.listByStudent(scanner));
    }

    private static int readOption(int max) {
        while (true) {
            try {
                int option = Integer.parseInt(scanner.nextLine().trim());
                if (option >= 1 && option <= max) return option;
            } catch (NumberFormatException ignored) {}
            System.out.print("Invalid option! Please choose again [1-" + max + "]: ");
        }
    }

    // Menu
    private static void displayMainMenu() {
        System.out.println("\n===== MAIN MENU =====");
        System.out.println("1. Student Management");
        System.out.println("2. Course Management");
        System.out.println("3. Enrollment Management");
        System.out.println("4. Query and Reports");
        System.out.println("5. Exit");
        System.out.print("Your option: ");
    }

    private static void displayStudentMenu() {
        System.out.println("\n===== Student Management =====");
        System.out.println("1. Create a new student");
        System.out.println("2. Update student information");
        System.out.println("3. Delete a student");
        System.out.println("4. View student by ID");
        System.out.println("5. List all students");
        System.out.println("6. Back to main menu");
        System.out.print("Your option: ");
    }

    private static void displayCourseMenu() {
        System.out.println("\n===== Course Management =====");
        System.out.println("1. Create a new course");
        System.out.println("2. Update course information");
        System.out.println("3. Delete a course");
        System.out.println("4. View course by ID");
        System.out.println("5. List all courses");
        System.out.println("6. Back to main menu");
        System.out.print("Your option: ");
    }

    private static void displayEnrollmentMenu() {
        System.out.println("\n===== Enrollment Management =====");
        System.out.println("1. Enroll a student");
        System.out.println("2. Drop an enrollment");
        System.out.println("3. View enrollment by ID");
        System.out.println("4. List all enrollments");
        System.out.println("5. Back to main menu");
        System.out.print("Your option: ");
    }

    private static void displayReportMenu() {
        System.out.println("\n===== Query and Reports =====");
        System.out.println("1. List enrollments by course");
        System.out.println("2. List enrollments by student");
        System.out.println("3. Back to main menu");
        System.out.print("Your option: ");
    }

    // Methods call controllers
    private static void studentManagement() {
        while (true) {
            displayStudentMenu();
            int option = readOption(studentActions.size() + 1);
            if (option == 6) break;
            studentActions.get(option).run();
        }
    }

    private static void courseManagement() {
        while (true) {
            displayCourseMenu();
            int option = readOption(courseActions.size() + 1);
            if (option == 6) break;
            courseActions.get(option).run();
        }
    }

    private static void enrollmentManagement() {
        while (true) {
            displayEnrollmentMenu();
            int option = readOption(enrollmentActions.size() + 1);
            if (option == 5) break;
            enrollmentActions.get(option).run();
        }
    }

    private static void queryAndReport() {
        while (true) {
            displayReportMenu();
            int option = readOption(reportActions.size() + 1);
            if (option == 3) break;
            reportActions.get(option).run();
        }
    }
}
