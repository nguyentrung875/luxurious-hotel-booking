package com.java06.luxurious_hotel.service.imp;

import com.java06.luxurious_hotel.entity.BookingEntity;
import com.java06.luxurious_hotel.entity.RoomBookingEntity;
import com.java06.luxurious_hotel.entity.RoomEntity;
import com.java06.luxurious_hotel.entity.UserEntity;
import com.java06.luxurious_hotel.payload.request.AddBookingRequest;
import com.java06.luxurious_hotel.repository.BookingRepository;
import com.java06.luxurious_hotel.repository.UserRepository;
import com.java06.luxurious_hotel.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class BookingServiceImp implements BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    @Override
    public void addNewBooking(AddBookingRequest request) {
        //Thêm khách booking vào table user
        UserEntity userEntity = new UserEntity();
        userEntity.setFirstName(request.firstName());
        userEntity.setLastName(request.lastName());
        userEntity.setPhone(request.phone());
        userEntity.setEmail(request.email());

        

        userRepository.save(userEntity);

        //Thêm booking
        BookingEntity newBooking = new BookingEntity();

        newBooking.setCheckIn(LocalDate.parse(request.checkInDate()).atStartOfDay());
        newBooking.setCheckOut(LocalDate.parse(request.checkOutDate()).atStartOfDay());
        newBooking.setRoomNumber(request.roomNumber());

        //Lấy danh sách room từ chuỗi roomId
        String[] listRoomId = request.rooms().split(",");
        List<RoomEntity> rooms = Arrays.stream(listRoomId).map(item -> {
            int roomId = Integer.parseInt(item);
            RoomEntity roomEntity = new RoomEntity();
            roomEntity.setId(roomId);
            return roomEntity;
        }).toList();


        RoomBookingEntity roomBookingEntity = new RoomBookingEntity();
    }
}
