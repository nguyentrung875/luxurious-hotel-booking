package com.java06.luxurious_hotel.service.imp;

import com.java06.luxurious_hotel.dto.GuestDTO;
import com.java06.luxurious_hotel.entity.*;
import com.java06.luxurious_hotel.exception.role.RoleNotFoundException;
import com.java06.luxurious_hotel.exception.user.DuplicateMailOrPhoneException;
import com.java06.luxurious_hotel.exception.user.UserNotFoundException;
import com.java06.luxurious_hotel.repository.*;
import com.java06.luxurious_hotel.request.AddGuestRequest;
import com.java06.luxurious_hotel.request.UpdateGuestRequest;
import com.java06.luxurious_hotel.service.UserService;
import com.java06.luxurious_hotel.supportmethod.ParseName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceIMP implements UserService {

    @Autowired
    private FilesStorageServiceImpl filesStorageService;

    @Value("${root.path}")
    private String root;

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

    @Override
    public Boolean checkEmail(String email) {
        UserEntity userEntity = userRepository.findUserEntityByEmail(email);
        if (userEntity  != null && userEntity.getId() > 0) {
            return userEntity.getId() > 0;
        } else {
            return false;
        }
    }

    @Override
    public GuestDTO getUser(int idUser) {

        UserEntity userEntity = userRepository.findById(idUser);
        GuestDTO guestDTO = new GuestDTO();
        if (userEntity != null) {
            guestDTO.setFullName(userEntity.getFirstName() + " " + userEntity.getLastName());
            guestDTO.setEmail(userEntity.getEmail());
            guestDTO.setPhone(userEntity.getPhone());
            guestDTO.setId(userEntity.getId());
            guestDTO.setDob(userEntity.getDob());
            guestDTO.setAddress(userEntity.getAddress());
            guestDTO.setSummary(userEntity.getSummary());
        }
        System.out.println("GuestDTO: " + userEntity);
        return guestDTO;
    }


    @Override
    public Boolean checkPhone(String phone) {

        Optional<UserEntity> userEntity = userRepository.findUserEntityByPhone(phone);
        if (userEntity.isPresent()) {
            UserEntity userEntity1 = userEntity.get();

            return userEntity1.getId() > 0;
        }

        return false;
    }
    @Transactional // đảm bảo phương thức được thực hiện trong 1 giao dịch
    @Override
    public Boolean updateUser(UpdateGuestRequest updateGuestRequest) {
        UserEntity userEntity = new UserEntity();

        UserEntity updatedUser;

        userEntity.setId(updateGuestRequest.idGuest());
        String[] names = parseName.parseName(updateGuestRequest.fullName());
        userEntity.setFirstName(names[0]);
        userEntity.setLastName(names[1]);

        userEntity.setDob(updateGuestRequest.dob());
        userEntity.setPhone(updateGuestRequest.phone());
        userEntity.setEmail(updateGuestRequest.email());
        userEntity.setAddress(updateGuestRequest.address());
        userEntity.setSummary(updateGuestRequest.summary());
        userEntity.setRole(roleRepository.findRoleEntitiesById(2));

//        UserEntity updatedUser = userRepository.save(userEntity);
        try {
            // Lưu người dùng và xử lý trùng lặp
            updatedUser = userRepository.save(userEntity);
        } catch (DataIntegrityViolationException e) {
            // Kiểm tra nguyên nhân gốc của lỗi bằng getCause()
            Throwable cause = e.getCause();

            // Log lỗi để kiểm tra nguyên nhân chi tiết
            System.out.println("Cause: " + (cause != null ? cause.getMessage() : "null"));

            if (cause != null && cause.getMessage().contains("Duplicate entry")) {
                // Nếu nguyên nhân là lỗi duplicate entry
                throw new DuplicateMailOrPhoneException("Duplicate mail or phone number");
            }

            // Bắt thêm lỗi khác nếu có
            throw e;  // Ném lại lỗi nếu không phải do vi phạm ràng buộc
        }

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

        // Kiểm tra và xóa trong RoomBooking nếu tồn tại
        if (roomBookingRepository.existsByBooking_Guest_Id(idUser)) {
            // Xóa booking và room booking tương ứng với guest
            roomBookingRepository.deleteAllByBooking_Guest_Id(idUser);
        }

        // Kiểm tra và xóa trong Booking nếu tồn tại
        if (bookingRepository.existsByGuest_Id(idUser)) {
            bookingRepository.deleteAllByGuest_Id(idUser);
        }

        // Kiểm tra và xóa trong Orders nếu tồn tại
        if (ordersRepository.existsByReservation_Guest_Id(idUser)) {
            ordersRepository.deleteAllByReservation_Guest_Id(idUser);
        }

        // Kiểm tra và xóa trong Orders nếu tồn tại
        if (reservationRepository.existsByGuestId(idUser)) {
            reservationRepository.deleteAllByGuestId(idUser);
        }

        // xóa guest
        userRepository.deleteById(idUser);

        if (!userRepository.existsById(idUser)) {
            check = true;
        }

        return check;
    }


    @Override
    public GuestDTO getMyInfo() {
        String myUsername = SecurityContextHolder.getContext().getAuthentication().getName();

        return userRepository.findByUsername(myUsername).stream().map(userEntity -> {
            GuestDTO guestDTO = new GuestDTO();
            guestDTO.setId(userEntity.getId());
            guestDTO.setUsername(userEntity.getUsername());
            guestDTO.setFirstName(userEntity.getFirstName());
            guestDTO.setLastName(userEntity.getLastName());
            guestDTO.setPhone(userEntity.getPhone());
            guestDTO.setAddress(userEntity.getAddress());
            guestDTO.setEmail(userEntity.getEmail());
            return guestDTO;
        }).findFirst().orElseThrow(UserNotFoundException::new);

    }

    @Override
    public GuestDTO getGuestInfoByPhone(String phone) {
        return userRepository.findUserEntityByPhone(phone).stream().map(userEntity -> {
            GuestDTO guestDTO = new GuestDTO();
            guestDTO.setFirstName(userEntity.getFirstName());
            guestDTO.setLastName(userEntity.getLastName());
            guestDTO.setPhone(userEntity.getPhone());
            guestDTO.setAddress(userEntity.getAddress());
            guestDTO.setEmail(userEntity.getEmail());
            return guestDTO;
        }).findFirst().orElseThrow(UserNotFoundException::new);
    }

    @Override
    public List<GuestDTO> getListGuest() {

        List<UserEntity> listGuest = userRepository.findByRole_Name("ROLE_GUEST");
        List<GuestDTO> guestDTOS = new ArrayList<>();
        for (UserEntity user : listGuest) {
            GuestDTO guestDTO = new GuestDTO();
            guestDTO.setId(user.getId());
            guestDTO.setFullName(user.getFirstName() + " " + user.getLastName());
            guestDTO.setPhone(user.getPhone());
            guestDTO.setEmail(user.getEmail());
            guestDTO.setAddress(user.getAddress());
            guestDTO.setSummary(user.getSummary());

            if (user.getImage() != null){
                guestDTO.setLinkImage("http://localhost:9999/file/hauchuc/" + user.getImage());
            }else {
                guestDTO.setLinkImage("");
            }

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

        // Định dạng theo kiểu ngày trong chuỗi
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        userEntity.setDob(LocalDate.parse(addGuestRequest.dob(),formatter));

        RoleEntity guestRole = roleRepository.findByName("ROLE_GUEST");
        if (guestRole == null){
            throw new RoleNotFoundException();
        }
        userEntity.setRole(guestRole);

        try {
            userRepository.save(userEntity);
        }catch (Exception e){
            throw new DuplicateMailOrPhoneException("duplicate mail or phone number");
        }

        return userEntity.getId() > 0;
    }




}
