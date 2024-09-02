package com.java06.luxurious_hotel.service.imp;

import com.java06.luxurious_hotel.dto.*;
import com.java06.luxurious_hotel.entity.BookingEntity;
import com.java06.luxurious_hotel.entity.RoomAmenityEntity;
import com.java06.luxurious_hotel.entity.RoomBookingEntity;
import com.java06.luxurious_hotel.entity.UserEntity;
import com.java06.luxurious_hotel.repository.*;
import com.java06.luxurious_hotel.request.SearchRoomRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoomServiceImp implements com.java06.luxurious_hotel.service.RoomService {

    @Autowired
    private RoomTypeRepository roomTypeRepository;

    @Autowired
    private RoomBookingRepository roomBookingRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private RoomAmenityRepository roomAmenityRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Override
    public List<RoomTypeByDateDTO> getRoomInfoByDate(String selectDateString) {
        LocalDateTime selectDate = LocalDate.parse(selectDateString).atStartOfDay();

        //Những booking có trong select date
        List<RoomBookingEntity> roomBookingEntityList = roomBookingRepository.findRoomBookingsBySelectedDate(selectDate);

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

    public LocalDateTime parseDate(String date) {
        String data = date.trim() + "T00:00:00";


        return LocalDateTime.parse(data);

    }

    @Override
    public List<RoomVailableDTO> getAvailableRooms(SearchRoomRequest searchRoomRequest) {


        List<RoomAvailableInfo> roomAvailableInfoList = roomRepository.findAvailableRoom(parseDate(searchRoomRequest.checkIn()), parseDate(searchRoomRequest.checkOut()));
        List<RoomVailableDTO> roomVailableDTOList = new ArrayList<>();
        for (RoomAvailableInfo item : roomAvailableInfoList) {
            RoomVailableDTO roomVailableDTO = new RoomVailableDTO();
            roomVailableDTO.setRoomNumber(item.getRoomEntity().getName());
            roomVailableDTO.setRoomTypeName(item.getRoomTypeEntity().getName());
            roomVailableDTO.setArea(item.getRoomTypeEntity().getArea());
            roomVailableDTO.setPrice(item.getRoomTypeEntity().getPrice());
            roomVailableDTO.setOverview(item.getRoomTypeEntity().getOverview());
            //String bedName = bedTypeRepository.findById(item.getRoomTypeEntity().getBedType().getName());
            roomVailableDTO.setBedName(item.getRoomTypeEntity().getBedType().getName());
            roomVailableDTO.setQltGuest(item.getRoomTypeEntity().getCapacity());
            String imagesString = item.getRoomTypeEntity().getImage();
            if (imagesString != null && !imagesString.isEmpty()) {
                List<String> imagesList = Arrays.stream(imagesString.split(","))
                        .collect(Collectors.toList());
                roomVailableDTO.setImage(imagesList);
            }
            List<RoomAmenityEntity> list = roomAmenityRepository.findByRoomTypeId(item.getRoomTypeEntity().getId());
            String amenityStr = list.stream().
                    map(roomAmenityEntity -> roomAmenityEntity.getAmenity().getDescription()).
                    collect(Collectors.joining(", "));

            roomVailableDTO.setAmanity(amenityStr);


            roomVailableDTOList.add(roomVailableDTO);
        }

        System.out.println("oke");
        return roomVailableDTOList;
    }
}
