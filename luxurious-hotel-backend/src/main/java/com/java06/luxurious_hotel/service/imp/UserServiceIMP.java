package com.java06.luxurious_hotel.service.imp;

import com.java06.luxurious_hotel.dto.BookingGuestDTO;
import com.java06.luxurious_hotel.dto.GuestDTO;
import com.java06.luxurious_hotel.entity.*;
import com.java06.luxurious_hotel.exception.role.RoleNotFoundException;
import com.java06.luxurious_hotel.repository.*;
import com.java06.luxurious_hotel.request.AddGuestRequest;
import com.java06.luxurious_hotel.request.UpdateGuestRequest;
import com.java06.luxurious_hotel.service.UserService;
import com.java06.luxurious_hotel.supportmethod.ParseName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceIMP implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookingRepository bookingRepository;

    private ParseName parseName;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private RoomBookingRepository roomBookingRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private OrdersRepository ordersRepository;

    @Transactional // đảm bảo phương thức được thực hiện trong 1 giao dịch
    @Override
    public Boolean updateUser(UpdateGuestRequest updateGuestRequest) {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(updateGuestRequest.idGuest());
        String[] names = parseName.parseName(updateGuestRequest.fullName());
        userEntity.setFirstName(names[0]);
        userEntity.setLastName(names[1]);

        // set ngày khởi tạo
        userEntity.setDob(LocalDate.now());

        userEntity.setPhone(updateGuestRequest.phone());
        userEntity.setEmail(updateGuestRequest.email());

        UserEntity updatedUser = userRepository.save(userEntity);

        if (updatedUser != null && updatedUser.getId() > 0) {
            return true;
        } else {
            return false;
        }
    }

    @Transactional
    @Override
    public Boolean deleteUser(int idUser) {
        Boolean check = false;

        // xóa booking và roombooking tương ứng guest
        roomBookingRepository.deleteAllByBooking_Guest_Id(idUser);
        bookingRepository.deleteAllByGuest_Id(idUser);

        // xóa reservation và order tương ứng guest
        ordersRepository.deleteAllByReservation_Guest_Id(idUser);
        reservationRepository.deleteAllByGuestId(idUser);

        // xóa guest
        userRepository.deleteById(idUser);

        if (!userRepository.existsById(idUser)) {
            check = true;
        }

        return check;
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

    @Override
    public Boolean addGuest(AddGuestRequest addGuestRequest) {

        UserEntity userEntity = new UserEntity();

        // tách fullname
        String[] names = parseName.parseName(addGuestRequest.fullName());
        userEntity.setFirstName(names[0]);
        userEntity.setLastName(names[1]);

        // set ngày khởi tạo
        userEntity.setDob(LocalDate.now());

        userEntity.setEmail(addGuestRequest.email());
        userEntity.setPhone(addGuestRequest.phone());
        userEntity.setAddress(addGuestRequest.address());
        userEntity.setSummary(addGuestRequest.summary());

        RoleEntity guestRole = roleRepository.findByName("ROLE_GUEST");
        if (guestRole == null){
            throw new RoleNotFoundException();
        }
        userEntity.setRole(guestRole);

        userRepository.save(userEntity);



        return userEntity.getId() > 0;
    }




}
