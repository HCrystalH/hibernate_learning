package model.entities;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name="cinema_room_detail")
public class RoomDetail {
    @Id
    @Column(name="cinema_room_detail_id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;

    @Column(name="room_rate")
    private int rate;

    @Column(name="active_date")
    private LocalDate activeDate;

    @Column(name="room_description",length = 250)
    private String description;

    @OneToOne
    @JoinColumn(name="cinema_room_id")
    private Room room;

    public RoomDetail() {
    }

    public RoomDetail(int rate, LocalDate activeDate, String description, Room room) {
        this.rate = rate;
        this.activeDate = activeDate;
        this.description = description;
        setRoom(room);
    }

    public int getId() {
        return id;
    }

    public int getRate() {
        return rate;
    }

    public LocalDate getActiveDate() {
        return activeDate;
    }

    public String getDescription() {
        return description;
    }

    public Room getRoom() {
        return room;
    }

    // setters
    public void setRate(int rate) {
        this.rate = rate;
    }

    public void setActiveDate(LocalDate activeDate) {
        this.activeDate = activeDate;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setRoom(Room room) {
        this.room = room;

        // Helper for deletion
        if(room != null && room.getRoomDetail() != this) room.setRoomDetail(this);
    }

}
