package com.java06.luxurious_hotel.service.imp;

import com.java06.luxurious_hotel.dto.BookingGuestDTO;
import com.java06.luxurious_hotel.dto.GuestDTO;
import com.java06.luxurious_hotel.entity.UserEntity;
import com.java06.luxurious_hotel.repository.UserRepository;
import com.java06.luxurious_hotel.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceIMP implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<BookingGuestDTO> getListBooking(int idGuest) {
        List<UserEntity> userEntities = userRepository.findUserEntityById(idGuest);
        List<BookingGuestDTO> bookingGuestDTOs = new ArrayList<>();
        int id = 0;

        for (UserEntity userEntity : userEntities) {
            BookingGuestDTO bookingGuestDTO = new BookingGuestDTO();

            bookingGuestDTO.setIdGuest(userEntity.getId());
            bookingGuestDTO.setFullName(userEntity.getUsername());
            bookingGuestDTO.setPhone(userEntity.getPhone());
            bookingGuestDTO.setEmail(userEntity.getEmail());
            bookingGuestDTO.setAddress(userEntity.getAddress());

            bookingGuestDTO.setIdBooking(userEntity.getBookings().stream().findFirst().get().getId());

            bookingGuestDTO.setCheckIn(userEntity.getBookings().stream().findFirst().get().getCheckIn());
            bookingGuestDTO.setCheckOut(userEntity.getBookings().stream().findFirst().get().getCheckOut());
            bookingGuestDTO.setPaymentStatus(userEntity.getBookings().stream().findFirst().get().getPaymentStatus().getName());
            bookingGuestDTO.setPaymentMethod(userEntity.getBookings().stream().findFirst().get().getPaymentMethod().getName());
            bookingGuestDTO.setMember(userEntity.getBookings().stream().findFirst().get().getAdultNumber()
            + userEntity.getBookings().stream().findFirst().get().getChildrenNumber());
            bookingGuestDTO.setQuantilyRoom(userEntity.getBookings().stream().findFirst().get().getRoomNumber());

            for (int n = 0; n < userEntity.getBookings().stream().findFirst().get().getRoomNumber(); n++){
                String roomName = userEntity.getBookings().stream().skip(n).findFirst().get().getRoomBookings()
                                .stream().skip(n).findFirst().get().getRoom().getName();
                bookingGuestDTO.getRoomName().add(roomName);
                String roomtype = userEntity.getBookings().stream().skip(n).findFirst().get().getRoomBookings()
                        .stream().skip(n).findFirst().get().getRoom().getRoomType().getName();
                bookingGuestDTO.getRoomType().add(roomtype);
            }
        }

        return List.of();
    }

    @Override
    public List<GuestDTO> getListGuest(String roleName) {
        List<UserEntity> listGuest = userRepository.findByRole_Name(roleName);
        List<GuestDTO> guestDTOS = new ArrayList<>();
        for (UserEntity user : listGuest) {
            GuestDTO guestDTO = new GuestDTO();
            guestDTO.setId(user.getId());
            guestDTO.setFullName(user.getUsername());
            guestDTO.setPhone(user.getPhone());
            guestDTO.setEmail(user.getEmail());
            guestDTO.setAddress(user.getAddress());

            guestDTOS.add(guestDTO);
        }
        return guestDTOS;
    }

}
