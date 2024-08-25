package com.java06.luxurious_hotel.service.imp;

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
    public List<GuestDTO> getListGuest(int idRole) {
        List<UserEntity> listGuest = userRepository.findByRole_Id(idRole);
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
