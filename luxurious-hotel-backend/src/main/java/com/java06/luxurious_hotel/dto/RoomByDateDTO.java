package com.java06.luxurious_hotel.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RoomByDateDTO {
    private int id;
    private String name;
    private GuestDTO guestCheckIn;
    private GuestDTO guestCheckOut;
    private GuestDTO guestStaying;
    private int adultStaying;
    private int childrenStaying;
    private String otherRooms;
}
