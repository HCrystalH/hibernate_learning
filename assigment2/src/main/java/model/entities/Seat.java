package model.entities;

import jakarta.persistence.*;
import model.enums.SeatStatus;
import model.enums.SeatType;

/**
 * Instead of ID , every fields can be null
 */
@Entity
@Table(
        name="seat",
        uniqueConstraints = @UniqueConstraint(
                columnNames = {"cinema_room_id","seat_row","seat_column"}
        )
)
public class Seat {
    @Id
    @Column(name="seat_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="seat_column")
    private String seatColumn;

    @Column(name="seat_row")
    private int seatRow;

    @Enumerated(EnumType.STRING)
    @Column(name="seat_status")
    private SeatStatus seatStatus;

    @Enumerated(EnumType.STRING)
    @Column(name="seat_type")
    private SeatType seatType;

    @ManyToOne
    @JoinColumn(name="cinema_room_id")
    private Room room;

    public Seat() {}

    public Seat(String seatColumn, int seatRow, SeatStatus seatStatus, SeatType seatType, Room room) {
        this.seatColumn = seatColumn;
        this.seatRow = seatRow;
        this.seatStatus = seatStatus;
        this.seatType = seatType;
        this.room = room;
    }

    // Setters
    public void setSeatColumn(String seatColumn) {
        this.seatColumn = seatColumn;
    }

    public void setSeatRow(int seatRow) {
        this.seatRow = seatRow;
    }

    public void setSeatStatus(SeatStatus seatStatus) {
        this.seatStatus = seatStatus;
    }

    public void setSeatType(SeatType seatType) {
        this.seatType = seatType;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getSeatColumn() {
        return seatColumn;
    }

    public int getSeatRow() {
        return seatRow;
    }

    public SeatStatus getSeatStatus() {
        return seatStatus;
    }

    public SeatType getSeatType() {
        return seatType;
    }

    public Room getRoom() {
        return room;
    }
}
