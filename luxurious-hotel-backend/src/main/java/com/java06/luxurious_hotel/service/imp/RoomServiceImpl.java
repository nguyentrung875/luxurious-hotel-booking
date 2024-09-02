package com.java06.luxurious_hotel.service.imp;

import com.java06.luxurious_hotel.dto.RoomAvailableInfo;
import com.java06.luxurious_hotel.dto.RoomVailableDTO;
import com.java06.luxurious_hotel.entity.RoomAmenityEntity;
import com.java06.luxurious_hotel.repository.*;
import com.java06.luxurious_hotel.request.SearchRoomRequest;
import com.java06.luxurious_hotel.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoomServiceImpl implements RoomService {


    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private RoomTypeRepository roomTypeRepository;

    @Autowired
    private AmenityRepository amenityRepository;

    @Autowired
    private RoomAmenityRepository roomAmenityRepository;


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
