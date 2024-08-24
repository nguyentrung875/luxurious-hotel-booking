package com.java06.luxurious_hotel.entity;

import com.java06.luxurious_hotel.entity.keys.RoomAmenityKey;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity(name = "room_amenity")
public class RoomAmenityEntity {

    @EmbeddedId
    private RoomAmenityKey roomAmenityKey;

    @ManyToOne
    @MapsId("idRoomType")
    @JoinColumn(name = "id_room_type")
    private RoomTypeEntity roomType;

    @ManyToOne
    @MapsId("idAmenity")
    @JoinColumn(name = "id_amenity")
    private AmenityEntity amenity;
}
