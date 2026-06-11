package fa.training.entities;

import fa.training.enums.SeatStatus;
import fa.training.enums.SeatType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "seats")
public class Seat {
    @Id
    @Column(length = 20)    // Model Airplane 6 + _ + A + number: HJB112_D1
    private String id;  // ID Varchar

    // FK
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "airplane_id", nullable = false)
    @NotNull(message = "Airplane is required")
    private Airplane airplane;

    @Enumerated(EnumType.STRING)
    @Column(length = 10)
    private SeatType type = SeatType.ECONOMY;   // default : ECONOMY

    @Enumerated(EnumType.STRING)
    @Column(length = 10)
    private SeatStatus status = SeatStatus.AVAILABLE;   // default: AVAILABLE

    @Column(name = "created_date",updatable = false)
    private LocalDateTime createdDate;

    @Column(name = "updated_date")
    private LocalDateTime updatedDate;

    public Seat() {}

    public Seat(Airplane airplane, SeatType type, SeatStatus status) {
        this.airplane = airplane;
        this.type = type;
        this.status = status;
    }

    /*
            created date should be required
            and can't be updated
         */
    @PrePersist
    public void prePersist() {
        createdDate = LocalDateTime.now();
        updatedDate = LocalDateTime.now();
    }

    // when update a record th updated date should be required
    @PreUpdate
    public void preUpdate() {
        updatedDate = LocalDateTime.now();
    }


    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Airplane getAirplane() {
        return airplane;
    }

    public void setAirplane(Airplane airplane) {
        this.airplane = airplane;
    }

    public SeatType getType() {
        return type;
    }

    public void setType(SeatType type) {
        this.type = type;
    }

    public SeatStatus getStatus() {
        return status;
    }

    public void setStatus(SeatStatus status) {
        this.status = status;
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
    public String toString() {
        return "Seat{" +
                "id='" + id + '\'' +
                ", airplane=" + airplane +
                ", type=" + type +
                ", status=" + status +
                ", createdDate=" + createdDate +
                ", updatedDate=" + updatedDate +
                '}';
    }
}
