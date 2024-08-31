package com.java06.luxurious_hotel.service.imp;

import com.java06.luxurious_hotel.dto.*;
import com.java06.luxurious_hotel.entity.BookingEntity;
import com.java06.luxurious_hotel.entity.RoomBookingEntity;
import com.java06.luxurious_hotel.entity.UserEntity;
import com.java06.luxurious_hotel.repository.BookingRepository;
import com.java06.luxurious_hotel.repository.RoomBookingRepository;
import com.java06.luxurious_hotel.repository.RoomTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class RoomServiceImp implements com.java06.luxurious_hotel.service.RoomService {

    @Autowired
    private RoomTypeRepository roomTypeRepository;

    @Autowired
    private RoomBookingRepository roomBookingRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @Override
    public List<RoomTypeByDateDTO> getRoomInfoByDate(String selectDateString) {
        LocalDateTime selectDate = LocalDate.parse(selectDateString).atStartOfDay();

        //Những booking có trong select date
        List<RoomBookingEntity> roomBookingEntityList = roomBookingRepository.findRoomBookingsBySelectedDate(selectDate);
        List<BookingEntity> bookingEntityList = bookingRepository.findBookingsBySelectedDate(selectDate);

        //Lấy tất cả các Room Type của khách sạn
        return roomTypeRepository.findAll().stream().map(roomTypeEntity -> {

            RoomTypeByDateDTO roomTypeByDateDTO = new RoomTypeByDateDTO();
            roomTypeByDateDTO.setId(roomTypeEntity.getId());
            roomTypeByDateDTO.setName(roomTypeEntity.getName());

            //Lấy tất cả các Room của khách sạn
            roomTypeByDateDTO.setRooms(roomTypeEntity.getRooms().stream().map(roomEntity -> {
                RoomByDateDTO roomByDateDTO = new RoomByDateDTO();
                roomByDateDTO.setId(roomEntity.getId());
                roomByDateDTO.setName(roomEntity.getName());

                //So sánh tất cả Room của khách sạn với roomBooking để lấy thông tin khách hàng
                roomBookingEntityList.forEach(roomBookingEntity -> {

                    if (roomBookingEntity.getRoom().getId() == roomEntity.getId()){

                        //Guest checkin
                        if (roomBookingEntity.getBooking().getCheckIn().equals(selectDate)) {
                            roomByDateDTO.setGuestCheckIn(this.getGuestByDateDTO(roomBookingEntity));
                        }

                        //Guest checkout
                        if (roomBookingEntity.getBooking().getCheckOut().equals(selectDate)) {
                            roomByDateDTO.setGuestCheckOut(this.getGuestByDateDTO(roomBookingEntity));
                        }

                        //Guest Staying
                        if (roomBookingEntity.getBooking().getBookingStatus().getId() == 3) { //Trạng thái Checked In
                            GuestByDateDTO guestByDateDTO = this.getGuestByDateDTO(roomBookingEntity);

                            List<String> otherRooms = new ArrayList<>();
                            roomBookingEntityList.forEach(roomBookingEntity1 -> {
                                if (roomBookingEntity.getBooking().equals(roomBookingEntity1.getBooking())
                                    && !roomBookingEntity.getRoom().equals(roomBookingEntity1.getRoom())){
                                    otherRooms.add(roomBookingEntity1.getRoom().getName());
                                }
                            });
                            guestByDateDTO.setOtherRooms(otherRooms);
                            roomByDateDTO.setGuestStaying(guestByDateDTO);
                        }
                    }
                });

                return roomByDateDTO;
            }).toList());

            return roomTypeByDateDTO;
        }).toList();
    }

    private GuestByDateDTO getGuestByDateDTO(RoomBookingEntity roomBookingEntity) {
        GuestByDateDTO guestDTO = new GuestByDateDTO();
        guestDTO.setIdSelectBooking(roomBookingEntity.getBooking().getId());
        guestDTO.setId(roomBookingEntity.getBooking().getGuest().getId());
        guestDTO.setFirstName(roomBookingEntity.getBooking().getGuest().getFirstName());
        guestDTO.setLastName(roomBookingEntity.getBooking().getGuest().getLastName());
        return guestDTO;
    }
}
