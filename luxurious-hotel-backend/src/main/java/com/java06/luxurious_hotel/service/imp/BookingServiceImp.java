package com.java06.luxurious_hotel.service.imp;

import com.java06.luxurious_hotel.dto.BookingDTO;
import com.java06.luxurious_hotel.entity.*;
import com.java06.luxurious_hotel.repository.BookingRepository;
import com.java06.luxurious_hotel.repository.RoomBookingRepository;
import com.java06.luxurious_hotel.repository.RoomRepository;
import com.java06.luxurious_hotel.repository.UserRepository;
import com.java06.luxurious_hotel.request.AddBookingRequest;
import com.java06.luxurious_hotel.request.UpdateBookingRequest;
import com.java06.luxurious_hotel.service.BookingService;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

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
    public List<BookingDTO> getAllBooking() {

        List<BookingDTO> bookingDTOList = bookingRepository.findAll().stream().map(item -> {
            BookingDTO bookingDTO = new BookingDTO();
            bookingDTO.setId(item.getId());
            bookingDTO.setFirstName(item.getGuest().getFirstName());
            bookingDTO.setLastName(item.getGuest().getLastName());
            bookingDTO.setCheckIn(item.getCheckIn().toLocalDate());
            bookingDTO.setCheckOut(item.getCheckOut().toLocalDate());
            bookingDTO.setPaymentMethod(item.getPaymentMethod().getName());
            bookingDTO.setPaymentStatus(item.getPaymentStatus().getName());
            bookingDTO.setBookingStatus(item.getBookingStatus().getName());
            bookingDTO.setPaidAmount(item.getPaidAmount());
            bookingDTO.setTotal(item.getTotal());
            bookingDTO.setAdultNo(item.getAdultNumber());
            bookingDTO.setChildrenNo(item.getChildrenNumber());

            List<RoomEntity> rooms = roomBookingRepository.getAllByBooking(item).stream().map(roomBooking -> roomBooking.getRoom()).toList();
//            List<RoomEntity> rooms = item.getRoomBookings().stream().map(roomBooking -> roomBooking.getRoom()).toList();
//            System.out.println("checkROOMS: " + item.getCheckIn() + item.getRoomBookings());

            Map<String, List<String>> roomMap = rooms.stream().collect(Collectors.groupingBy(
                    room -> room.getRoomType().getName()
                            ,Collectors.mapping(RoomEntity::getName, Collectors.toList())
            ));

            bookingDTO.setRoomNo((HashMap<String, List<String>>) roomMap);

            return bookingDTO;
        }).toList();

        return bookingDTOList;
    }

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
        if (optional.isPresent()) {
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
        this.updateRoomBooking(request.rooms(), insertedBooking);
    }

    @Transactional
    @Override
    public void updateBooking(UpdateBookingRequest request) {
        //CẬP NHẬT BẢNG BOOKING
        BookingEntity updateBooking = new BookingEntity();
        updateBooking.setId(request.idBooking());

        updateBooking.setCheckIn(LocalDate.parse(request.checkInDate()).atStartOfDay());
        updateBooking.setCheckOut(LocalDate.parse(request.checkOutDate()).atStartOfDay());
        updateBooking.setRoomNumber(request.roomNumber());
        updateBooking.setAdultNumber(request.adultNumber());
        updateBooking.setChildrenNumber(request.childrenNumber());

        PaymentMethodEntity paymentMethod = new PaymentMethodEntity();
        paymentMethod.setId(request.idPayment());
        updateBooking.setPaymentMethod(paymentMethod);

        PaymentStatusEntity paymentStatus = new PaymentStatusEntity();
        paymentStatus.setId(request.idPaymentStatus());
        updateBooking.setPaymentStatus(paymentStatus);

        BookingStatusEntity bookingStatus = new BookingStatusEntity();
        bookingStatus.setId(request.idBookingStatus());
        updateBooking.setBookingStatus(bookingStatus);

        UserEntity guest = new UserEntity();
        guest.setId(request.idGuest());
        updateBooking.setGuest(guest);

        updateBooking.setPaidAmount(request.paidAmount());
        updateBooking.setTotal(request.total());

        bookingRepository.save(updateBooking);

        //CẬP NHẬT BẢNG ROOM_BOOKING
        //1. Xóa các dòng có id_booking == updateBooking
        roomBookingRepository.deleteAllByBooking(updateBooking);

        //2. Thêm lại các phòng mới
        this.updateRoomBooking(request.rooms(), updateBooking);
    }

    @Transactional
    @Override
    public void deleteBooking(int idBooking) {
        BookingEntity delBooking = new BookingEntity();
        delBooking.setId(idBooking);
        roomBookingRepository.deleteAllByBooking(delBooking);
        bookingRepository.delete(delBooking);

    }

    private void updateRoomBooking(String strRooms, BookingEntity booking) {
        //Lấy danh sách room từ chuỗi roomId
        String[] listRoomId = strRooms.split(",");
        List<RoomBookingEntity> rooms = Arrays.stream(listRoomId).map(item -> {
            int roomId = Integer.parseInt(item);
            RoomEntity roomEntity = new RoomEntity();
            roomEntity.setId(roomId);

            RoomBookingEntity roomBooking = new RoomBookingEntity();
            roomBooking.setRoom(roomEntity);
            roomBooking.setBooking(booking);

            return roomBooking;
        }).toList();
        roomBookingRepository.saveAll(rooms);
    }
}
