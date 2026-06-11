package fa.training.entities.manyToMany;

import jakarta.persistence.*;

import java.time.LocalDate;

/**
 *  đây là bản trung gian
 */
@Entity
@Table(name = "student_course")
public class StudentCourse {

    @EmbeddedId
    private StudentCourseId id;

    @ManyToOne
    @MapsId("studentId")    // đúng name trong StudentCourseId
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne
    @MapsId("courseId")     // đúng name trong StudentCourseId
    @JoinColumn(name = "course_id")
    private Course course;

    private LocalDate enrollmentDate;

    private Double score;

    public StudentCourse(Student student, Course course, LocalDate enrollmentDate, Double score) {
        this.student = student;
        this.course = course;
        this.enrollmentDate = enrollmentDate;
        this.score = score;
    }
}
