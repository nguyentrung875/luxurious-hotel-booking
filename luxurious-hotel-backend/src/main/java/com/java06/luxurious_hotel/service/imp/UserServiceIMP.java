package com.java06.luxurious_hotel.service.imp;

import com.java06.luxurious_hotel.dto.BookingGuestDTO;
import com.java06.luxurious_hotel.dto.GuestDTO;
import com.java06.luxurious_hotel.entity.BookingEntity;
import com.java06.luxurious_hotel.entity.RoomBookingEntity;
import com.java06.luxurious_hotel.entity.UserEntity;
import com.java06.luxurious_hotel.repository.BookingRepository;
import com.java06.luxurious_hotel.repository.UserRepository;
import com.java06.luxurious_hotel.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceIMP implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookingRepository bookingRepository;


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
