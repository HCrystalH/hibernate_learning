package manyToMany;

import fa.training.entities.manyToMany.Course;
import fa.training.entities.manyToMany.Student;

public class TestDataFactory {

    public static Student createStudent() {

        return new Student("John");
    }

    public static Course createCourse() {

        return new Course("Java");
    }
}
