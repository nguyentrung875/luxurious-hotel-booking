package com.java06.luxurious_hotel.service.imp;


import com.java06.luxurious_hotel.dto.*;
import com.java06.luxurious_hotel.dto.coverdto.RoomsDTO;

import com.java06.luxurious_hotel.dto.searchAvaiRoom.RoomAvailableDTO;
import com.java06.luxurious_hotel.dto.searchAvaiRoom.RoomTypeAvailableDTO;
import com.java06.luxurious_hotel.entity.*;
import com.java06.luxurious_hotel.exception.booking.BookingNotFoundException;
import com.java06.luxurious_hotel.exception.room.RoomNotAvailableException;
import com.java06.luxurious_hotel.exception.room.RoomNotFoundException;
import com.java06.luxurious_hotel.repository.BookingRepository;
import com.java06.luxurious_hotel.repository.RoomBookingRepository;
import com.java06.luxurious_hotel.repository.RoomRepository;
import com.java06.luxurious_hotel.repository.UserRepository;
import com.java06.luxurious_hotel.request.AddBookingRequest;
import com.java06.luxurious_hotel.request.UpdateBookingRequest;
import com.java06.luxurious_hotel.service.BookingService;
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

    @Override
    public List<BookingDTO> getAllBooking() {
        return bookingRepository.findAll().stream().map(booking -> this.bookingEntityToBookingDTO(booking)).toList();
    }

    @Override
    public BookingDTO getDetailBooking(int idBooking) {
        return bookingRepository.findById(idBooking).stream().map(booking -> this.bookingEntityToBookingDTO(booking))
                .findFirst().orElseThrow(BookingNotFoundException::new);
    }

    @Transactional
    @Override
    public void addNewBooking(AddBookingRequest request) {
            /*
        1. Kiểm tra các phòng có available trong khoảng tgian checkIn checkOut hay không
        2. Kiểm tra thông xem khách đó đã book trước đó hay chưa thông qua sdt
        1. Thêm khách booking vào table user
        2. Thêm booking
        3. Thêm dữ liệu vào bảng room_booking
     * */

        //KIỂM TRA PHÒNG CÓ AVAILABLE
        LocalDateTime inDate = LocalDate.parse(request.checkInDate()).atStartOfDay();
        LocalDateTime outDate = LocalDate.parse(request.checkOutDate()).atStartOfDay();

        var notAvailableRoom = this.checkAvailableRoom(inDate, outDate, request.rooms());
        if (notAvailableRoom.size() > 0) {
            throw new RoomNotAvailableException("Rooms are not available " + notAvailableRoom);
        }

        //THÊM KHÁCH HÀNG VÀO TABLE USER
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
        userEntity.setAddress(request.address());
        RoleEntity role = new RoleEntity();
        role.setId(1);
        userEntity.setRole(role);

        UserEntity newGuest = userRepository.save(userEntity);

        //2. Thêm MỚI BOOKING
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
        if (request.idBookingStatus() == 0) {
            bookingStatus.setId(1);
        } else {
            bookingStatus.setId(request.idBookingStatus());
        }

        newBooking.setBookingStatus(bookingStatus);

        newBooking.setCreateDate(LocalDateTime.now());

        newBooking.setPaidAmount(request.paidAmount());
        newBooking.setTotal(request.total());

        BookingEntity insertedBooking = bookingRepository.save(newBooking);

        //THÊM DỮ LIỆU VÀO BẢNG ROOM_BOOKING
        this.insertRoomBooking(request.rooms(), insertedBooking);
    }

    @Transactional
    @Override
    public void updateBooking(UpdateBookingRequest request) {

        BookingEntity updateBooking = bookingRepository.findById(request.idBooking())
                .orElseThrow(BookingNotFoundException::new);

        //CẬP NHẬT BẢNG ROOM_BOOKING
        //1. Xóa các dòng có id_booking == updateBooking
        roomBookingRepository.deleteByBooking(updateBooking);

        //KIỂM TRA PHÒNG CÓ ĐANG AVAILABLE
        LocalDateTime inDate = LocalDate.parse(request.checkInDate()).atStartOfDay();
        LocalDateTime outDate = LocalDate.parse(request.checkOutDate()).atStartOfDay();

        var notAvailableRoom = this.checkAvailableRoom(inDate, outDate, request.rooms());
        if (notAvailableRoom.size() > 0) {
            throw new RoomNotAvailableException("Rooms are not available " + notAvailableRoom);
        }

        //Update lại các phòng mới
        this.insertRoomBooking(request.rooms(), updateBooking);

        //CẬP NHẬT BẢNG BOOKING
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


    }

    @Transactional
    @Override
    public void deleteBooking(int idBooking) {
        BookingEntity delBooking = new BookingEntity();
        delBooking.setId(idBooking);
        if (roomBookingRepository.deleteByBooking(delBooking) <= 0) {
            throw new BookingNotFoundException();
        } else {
            bookingRepository.delete(delBooking);
        }

    }

    private void insertRoomBooking(List<String> strRooms, BookingEntity booking) {
        //Lấy danh sách room từ list roomId
        List<RoomBookingEntity> rooms = strRooms.stream().map(item -> {
            RoomEntity roomEntity = new RoomEntity();
            roomEntity.setId(Integer.parseInt(item));

            RoomBookingEntity roomBooking = new RoomBookingEntity();
            roomBooking.setRoom(roomEntity);
            roomBooking.setBooking(booking);

            return roomBooking;
        }).toList();
        try {
            roomBookingRepository.saveAll(rooms);
        } catch (Exception e) {
            throw new RoomNotFoundException(e.getCause().getMessage());
        }
    }


    @Override
    public List<BookingGuestDTO> getListBooking(int idGuest) {

        // lấy danh sách trả về từ câu qr
        List<Object[]> results = bookingRepository.findByGuest_Id(idGuest);

        // tạo list DTO trả ra
        List<BookingGuestDTO> bookingGuestDTOS = new ArrayList<>();

        // chạy vòng for qua tất cả các luồng Objects được trả ra từ câu qr
        for (Object[] result : results) {

            // khởi tạo BookingEntity để nhận dữ liệu trả ra từ Object
            BookingEntity booking = (BookingEntity) result[0];

            // khởi tạo đối tượng DTO để nhận giá trị
            BookingGuestDTO bookingGuestDTO = new BookingGuestDTO();


            // Add phần tử UserEntity vào đối tượng GuestDTO của BookingGuestDTO
            GuestDTO guestDTO = new GuestDTO();

            guestDTO.setId(booking.getGuest().getId());

            guestDTO.setFullName(booking.getGuest().getFirstName() + " " + booking.getGuest().getLastName());
            guestDTO.setEmail(booking.getGuest().getEmail());
            guestDTO.setPhone(booking.getGuest().getPhone());
            guestDTO.setAddress(booking.getGuest().getAddress());
            guestDTO.setSummary(booking.getGuest().getSummary());

            bookingGuestDTO.setGuestDTO(guestDTO);

            // Add các phần tử khác vào BookingGuestDTO
            bookingGuestDTO.setIdBooking(booking.getId());
            bookingGuestDTO.setCheckIn(booking.getCheckIn());
            bookingGuestDTO.setCheckOut(booking.getCheckOut());
            bookingGuestDTO.setPaymentStatus(booking.getPaymentStatus().getName());
            bookingGuestDTO.setPaymentMethod(booking.getPaymentMethod().getName());
            bookingGuestDTO.setMember(booking.getAdultNumber() + booking.getChildrenNumber());
            bookingGuestDTO.setQuantilyRoom(booking.getRoomNumber());

            // add dữ liệu cho đối tượng RoomDTO của BookingGuestDTO

            // khởi tạo 1 map để nhóm các phòng theo loại phòng
            Map<String, RoomsDTO> roomTypeMap = new HashMap<>();

            // Lặp qua các RoomBookingEntity để nhóm phòng lại theo loại phòng
            for (RoomBookingEntity roomBooking : booking.getRoomBookings()) {

                // nhận name của roomtype
                String roomTypeName = roomBooking.getRoom().getRoomType().getName();

                // tìm roomsDTO theo name roomtype được lấy ở trên
                RoomsDTO roomsDTO = roomTypeMap.get(roomTypeName);

                // kiểm tra giá trị roomsDTO có tồn tại hay không nếu null thì khởi tạo
                if (roomsDTO == null) {
                    roomsDTO = new RoomsDTO();
                    roomsDTO.setNameRoomType(roomTypeName);
                    roomsDTO.setRoomNumber(new ArrayList<>());
                    roomTypeMap.put(roomTypeName, roomsDTO);
                }

                roomsDTO.getRoomNumber().add(roomBooking.getRoom().getName());
            }

            bookingGuestDTO.setRoomsDTO(new ArrayList<>(roomTypeMap.values()));


            bookingGuestDTOS.add(bookingGuestDTO);
        }
        return bookingGuestDTOS;
    }

    private List<String> checkAvailableRoom(LocalDateTime inDate, LocalDateTime outDate, List<String> bookRoomId) {

        List<String> notAvalableRooms = new ArrayList<>();

        //Lấy ra những room đã được booking trong khoảng tgian khách chọn
        List<RoomEntity> bookedRooms = roomRepository.findBookedRoomsByDateRange(inDate, outDate);
        for (RoomEntity bookedRoom : bookedRooms) {
            if (bookRoomId.contains("" + bookedRoom.getId())) {
                notAvalableRooms.add(bookedRoom.getName());
            }
        }
        return notAvalableRooms;
    }

    private BookingDTO bookingEntityToBookingDTO(BookingEntity booking) {

        if (booking == null) return null;

        BookingDTO bookingDTO = new BookingDTO();
        bookingDTO.setId(booking.getId());
        bookingDTO.setFirstName(booking.getGuest().getFirstName());
        bookingDTO.setLastName(booking.getGuest().getLastName());
        bookingDTO.setEmail(booking.getGuest().getEmail());
        bookingDTO.setPhone(booking.getGuest().getPhone());
        bookingDTO.setAddress(booking.getGuest().getAddress());
        bookingDTO.setCheckIn(booking.getCheckIn().toLocalDate());
        bookingDTO.setCheckOut(booking.getCheckOut().toLocalDate());

        PaymentMethodDTO paymentMethodDTO = new PaymentMethodDTO();
        paymentMethodDTO.setId(booking.getPaymentMethod().getId());
        paymentMethodDTO.setName(booking.getPaymentMethod().getName());
        bookingDTO.setPaymentMethod(paymentMethodDTO);

        PaymentStatusDTO paymentStatusDTO = new PaymentStatusDTO();
        paymentStatusDTO.setId(booking.getPaymentStatus().getId());
        paymentStatusDTO.setName(booking.getPaymentStatus().getName());
        bookingDTO.setPaymentStatus(paymentStatusDTO);

        BookingStatusDTO bookingStatusDTO = new BookingStatusDTO();
        bookingStatusDTO.setId(booking.getBookingStatus().getId());
        bookingStatusDTO.setName(booking.getBookingStatus().getName());
        bookingDTO.setBookingStatus(bookingStatusDTO);

        bookingDTO.setPaidAmount(booking.getPaidAmount());
        bookingDTO.setTotal(booking.getTotal());
        bookingDTO.setAdultNo(booking.getAdultNumber());
        bookingDTO.setChildrenNo(booking.getChildrenNumber());

        List<RoomEntity> rooms = roomBookingRepository.findByBooking(booking).stream().map(RoomBookingEntity::getRoom).toList();

        Map<String, List<String>> roomMap = rooms.stream().collect(Collectors.groupingBy(
                room -> room.getRoomType().getName()
                , Collectors.mapping(RoomEntity::getName, Collectors.toList())
        ));

        bookingDTO.setRoomNo((HashMap<String, List<String>>) roomMap);
        return bookingDTO;
    }

}
