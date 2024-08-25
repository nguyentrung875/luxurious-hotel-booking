package com.java06.luxurious_hotel.service;

import com.java06.luxurious_hotel.dto.GuestDTO;
import com.java06.luxurious_hotel.entity.UserEntity;

import java.util.List;

public interface UserService {

    List<GuestDTO> getListGuest(int idRole);
}
