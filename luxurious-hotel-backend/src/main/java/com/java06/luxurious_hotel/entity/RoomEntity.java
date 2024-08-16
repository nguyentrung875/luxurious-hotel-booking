package com.java06.luxurious_hotel.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity(name = "room")
public class RoomEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "id_room_type")
    private RoomTypeEntity roomType;

    @OneToMany(mappedBy = "room")
    private List<RoomAmenityEntity> roomAmenities;

    @OneToMany(mappedBy = "room")
    private List<RoomBookingEntity> roomBookings;

}
