package com.java06.luxurious_hotel.service.imp;

import com.java06.luxurious_hotel.entity.*;
import com.java06.luxurious_hotel.request.AddBookingRequest;
import com.java06.luxurious_hotel.repository.BookingRepository;
import com.java06.luxurious_hotel.repository.RoomBookingRepository;
import com.java06.luxurious_hotel.repository.RoomRepository;
import com.java06.luxurious_hotel.repository.UserRepository;
import com.java06.luxurious_hotel.request.UpdateBookingRequest;
import com.java06.luxurious_hotel.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Service
public class BookingServiceImp implements BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private RoomBookingRepository roomBookingRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    @Override
    public void addNewBooking(AddBookingRequest request) {
    /*
        1. Kiểm tra thông xem khách đó đã book trước đó hay chưa thông qua sdt
        1. Thêm khách booking vào table user
        2. Thêm booking
        3. Thêm dữ liệu vào bảng room_booking
     * */

        //1. Thêm khách booking vào table user
        UserEntity userEntity = new UserEntity();

        //Kiểm tra xem guest đã đặt phòng trước đó hay chưa thông qua sdt
        var optional = userRepository.findUserEntityByPhone(request.phone());
        if (optional.isPresent()){
            userEntity = optional.get();
        }

        //Cập nhật lại thông tin guest
        userEntity.setFirstName(request.firstName());
        userEntity.setLastName(request.lastName());
        userEntity.setPhone(request.phone());
        userEntity.setEmail(request.email());

        RoleEntity role = new RoleEntity();
        role.setId(1);
        userEntity.setRole(role);

        UserEntity newGuest = userRepository.save(userEntity);

        //2. Thêm booking
        BookingEntity newBooking = new BookingEntity();

        newBooking.setCheckIn(LocalDate.parse(request.checkInDate()).atStartOfDay());
        newBooking.setCheckOut(LocalDate.parse(request.checkOutDate()).atStartOfDay());
        newBooking.setRoomNumber(request.roomNumber());

        newBooking.setGuest(newGuest);
        newBooking.setAdultNumber(request.adultNumber());
        newBooking.setChildrenNumber(request.childrenNumber());

        PaymentMethodEntity paymentMethod = new PaymentMethodEntity();
        paymentMethod.setId(request.idPayment());
        newBooking.setPaymentMethod(paymentMethod);

        PaymentStatusEntity paymentStatus = new PaymentStatusEntity();
        paymentStatus.setId(request.idPaymentStatus());
        newBooking.setPaymentStatus(paymentStatus);

        BookingStatusEntity bookingStatus = new BookingStatusEntity();
        bookingStatus.setId(1);
        newBooking.setBookingStatus(bookingStatus);

        newBooking.setCreateDate(LocalDateTime.now());

        newBooking.setPaidAmount(request.paidAmount());
        newBooking.setTotal(request.total());

        BookingEntity insertedBooking = bookingRepository.save(newBooking);

        //3. Thêm dữ liệu vào bảng room_booking
        //Lấy danh sách room từ chuỗi roomId
        String[] listRoomId = request.rooms().split(",");
        List<RoomBookingEntity> rooms = Arrays.stream(listRoomId).map(item -> {

            int roomId = Integer.parseInt(item);
            RoomEntity roomEntity = new RoomEntity();
            roomEntity.setId(roomId);

            RoomBookingEntity roomBooking = new RoomBookingEntity();
            roomBooking.setRoom(roomEntity);
            roomBooking.setBooking(insertedBooking);

            return roomBooking;
        }).toList();

        roomBookingRepository.saveAll(rooms);

    }

    @Transactional
    @Override
    public void editBooking(UpdateBookingRequest request) {
        /*
        * 1. Tìm Booking thông qua id
        * 2. Update thông tin user
        * 3. Update thông tin booking
        * 4. Update bảng room_booking
        * */

        BookingEntity updateBooking = new BookingEntity();
        updateBooking.setId(request.idBooking());

        updateBooking.setCheckIn(LocalDate.parse(request.checkInDate()).atStartOfDay());
        updateBooking.setCheckOut(LocalDate.parse(request.checkOutDate()).atStartOfDay());
        updateBooking.setRoomNumber(request.roomNumber());

    }

    @Override
    public boolean deleteBooking(int idBooking) {
        return false;
    }
}
