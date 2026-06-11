package fa.training.entities;

import fa.training.utils.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Airport can have multiple Airplane with same model,etc
 */
@Entity
@Table(name = "airplanes")
public class Airplane {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "Model is required")
    @ModelConstraint
    @Column(unique = true, length = 6, nullable = false)
    private String model;

    @NotNull(message = "Length is required")
    private Integer length;

    @NotNull(message = "Width is required")
    private Integer width;

    @OneToMany(mappedBy = "airplane", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Seat> seats = new HashSet<>();

    @NotNull(message = "Total seats is required")
    @DivisibleBySix
    @Column(name = "total_seats")
    private Integer totalSeats;

    // Created date cannot be updated
    @Column(name = "created_date",updatable = false)
    private LocalDateTime createdDate;

    @Column(name = "updated_date")
    private LocalDateTime updatedDate;

    public Airplane() {}

    public Airplane(String model, Integer length, Integer width, Set<Seat> seats, Integer totalSeats) {
        this.model = model;
        this.length = length;
        this.width = width;
        this.seats = seats;
        this.totalSeats = totalSeats;
    }

    @PrePersist
    public void prePersist() {
        createdDate = LocalDateTime.now();
        updatedDate = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        updatedDate = LocalDateTime.now();
    }

    // Getters and Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Set<Seat> getSeats() {
        return seats;
    }

    public void setSeats(Set<Seat> seats) {
        this.seats = seats;
    }

    public Integer getTotalSeats() {
        return totalSeats;
    }

    public void setTotalSeats(Integer totalSeats) {
        this.totalSeats = totalSeats;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDateTime getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(LocalDateTime updatedDate) {
        this.updatedDate = updatedDate;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Airplane airplane = (Airplane) o;
        return Objects.equals(id, airplane.id) && Objects.equals(model, airplane.model) && Objects.equals(length, airplane.length) && Objects.equals(width, airplane.width) && Objects.equals(seats, airplane.seats) && Objects.equals(totalSeats, airplane.totalSeats) && Objects.equals(createdDate, airplane.createdDate) && Objects.equals(updatedDate, airplane.updatedDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, model, length, width, seats, totalSeats, createdDate, updatedDate);
    }

    @Override
    public String toString() {
        return "Airplane{" +
                "id=" + id +
                ", model='" + model + '\'' +
                ", length=" + length +
                ", width=" + width +
                ", seats=" + seats +
                ", totalSeats=" + totalSeats +
                ", createdDate=" + createdDate +
                ", updatedDate=" + updatedDate +
                '}';
    }
}
