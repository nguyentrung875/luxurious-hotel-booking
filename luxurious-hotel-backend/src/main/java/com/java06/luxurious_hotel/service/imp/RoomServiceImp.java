package com.java06.luxurious_hotel.service.imp;

import com.java06.luxurious_hotel.dto.*;
import com.java06.luxurious_hotel.dto.searchAvaiRoom.BedTypeDTO;
import com.java06.luxurious_hotel.dto.searchAvaiRoom.RoomAvailableDTO;
import com.java06.luxurious_hotel.dto.searchAvaiRoom.RoomTypeAvailableDTO;
import com.java06.luxurious_hotel.entity.*;
import com.java06.luxurious_hotel.repository.*;
import com.java06.luxurious_hotel.request.SearchRoomRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
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
    public List<RoomTypeAvailableDTO> getAvailableRooms(SearchRoomRequest searchRoomRequest) {
        List<RoomAvailableInfo> roomAvailableInfoList = roomRepository.findAvailableRoom(
                parseDate(searchRoomRequest.checkIn()), parseDate(searchRoomRequest.checkOut()));


        // sắp xếp theo tên roomType
        roomAvailableInfoList.sort(Comparator.comparing(roomAvailableInfo -> roomAvailableInfo.getRoomEntity().getRoomType().getName()));

        // tạo 1 map để để chứ các room type:
        Map<String, RoomTypeAvailableDTO> roomTypeMap = new HashMap<>();

        int num =1;
        boolean checkIn = false;
        for (RoomAvailableInfo roomAvailableInfo : roomAvailableInfoList) {
            String roomTypeName = roomAvailableInfo.getRoomEntity().getRoomType().getName();


            //Mục đích: chỉ Khởi tạo RoomTypeAvailableDTO 1 lần thôi
            if (!roomTypeMap.containsKey(roomTypeName)) {
                checkIn = true;

                RoomTypeAvailableDTO roomTypeAvailableDTO = new RoomTypeAvailableDTO();
                roomTypeAvailableDTO.setRoomTypeName(roomTypeName);
                roomTypeAvailableDTO.setPrice(roomAvailableInfo.getRoomEntity().getRoomType().getPrice());
                roomTypeAvailableDTO.setCapacity(roomAvailableInfo.getRoomEntity().getRoomType().getCapacity());

                String imagesRoomType = roomAvailableInfo.getRoomEntity().getRoomType().getImage();
                if (imagesRoomType != null && !imagesRoomType.isEmpty()) {
                    List<String> images = Arrays.stream(imagesRoomType.split(","))//
                            .collect(Collectors.toList()).stream()
                            .map(item -> ("http://localhost:9999/roomType/file/"+item)).toList();
                    roomTypeAvailableDTO.setImage(images);
                }

                roomTypeAvailableDTO.setRoomAvailableDTOList(new ArrayList<>());

                // Create BedTypeDTO
                BedTypeDTO bedTypeDTO = new BedTypeDTO();
                bedTypeDTO.setId(roomAvailableInfo.getRoomEntity().getRoomType().getBedType().getId());
                bedTypeDTO.setName(roomAvailableInfo.getRoomEntity().getRoomType().getBedType().getName());
                roomTypeAvailableDTO.setBedType(bedTypeDTO);


                roomTypeMap.put(roomTypeName, roomTypeAvailableDTO);

            }
            if (checkIn==false){
                num++;
                roomTypeMap.get(roomTypeName).setNumberAvailable(num);
            }
            if (checkIn==true){

                num = 1;
                checkIn = false;
            }

            System.out.println("number is "+ num);

            // Add available room to the corresponding room type
            RoomAvailableDTO roomAvailableDTO = new RoomAvailableDTO();
            roomAvailableDTO.setRoomId(roomAvailableInfo.getRoomEntity().getId());
            roomAvailableDTO.setRoomName(roomAvailableInfo.getRoomEntity().getName());

            roomTypeMap.get(roomTypeName).getRoomAvailableDTOList().add(roomAvailableDTO);
        }


        return new ArrayList<>(roomTypeMap.values());
    }






}


