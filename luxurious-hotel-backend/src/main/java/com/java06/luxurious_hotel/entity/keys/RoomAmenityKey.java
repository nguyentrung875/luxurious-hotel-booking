package com.java06.luxurious_hotel.entity.keys;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;

@Data
@Embeddable
public class RoomAmenityKey implements Serializable {

    @Column(name = "id_room_type")
    private int idRoomType;

    @Column(name = "id_amenity")
    private int idAmenity;
}
