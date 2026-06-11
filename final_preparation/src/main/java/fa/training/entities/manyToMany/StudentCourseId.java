package fa.training.entities.manyToMany;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class StudentCourseId implements Serializable {
    @Column(name="student_id")
    private Long studentId;

    @Column(name="course_id")
    private Long courseId;

    // equals & hashCode : must override these methods
    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        StudentCourseId that = (StudentCourseId) o;
        return Objects.equals(studentId, that.studentId) && Objects.equals(courseId, that.courseId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(studentId, courseId);
    }

    public StudentCourseId() {
    }

    public StudentCourseId(
            Long studentId,
            Long courseId
    ) {
        this.studentId = studentId;
        this.courseId = courseId;
    }
}
