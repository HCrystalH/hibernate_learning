package model.entities;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="cinema_room")
public class Room {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="cinema_room_id")
    private int id;

    @Column(name="cinema_room_name",unique = true)
    private String name;

    @Column(name="seat_quantity")
    private int seatQuantity;

    @OneToMany(mappedBy = "room")
    private Set<Seat> seats = new HashSet<>();

    @OneToOne(mappedBy = "room", cascade = CascadeType.ALL, orphanRemoval = true)
    private RoomDetail roomDetail;

    public Room() {}

    public Room(String name, int seatQuantity) {
        this.name = name;
        this.seatQuantity = seatQuantity;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSeatQuantity() {
        return seatQuantity;
    }

    public void setSeatQuantity(int seatQuantity) {
        this.seatQuantity = seatQuantity;
    }

    public Set<Seat> getSeats() {
        return seats;
    }

    public void setSeats(Set<Seat> seats) {
        this.seats = seats;
    }

    public RoomDetail getRoomDetail() {
        return roomDetail;
    }

    public void setRoomDetail(RoomDetail roomDetail) {
        this.roomDetail = roomDetail;

        // Helper for deletion
        if(roomDetail != null &&  roomDetail.getRoom() != this ) roomDetail.setRoom(this);
    }

    // Helper method for deletion
    public void removeRoomDetail() {
        if(roomDetail != null) {
            RoomDetail detail = roomDetail;
            roomDetail = null;
            detail.setRoom(null);
        }
    }
}
