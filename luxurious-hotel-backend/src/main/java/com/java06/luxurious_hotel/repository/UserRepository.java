package com.java06.luxurious_hotel.repository;

import com.java06.luxurious_hotel.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    List<UserEntity> findByRole_Name(String roleName);
    List<UserEntity> findUserEntityById(int id);
    Optional<UserEntity> findUserEntityByPhone(String phone);
    Optional<UserEntity> findByUsername(String username);
    Optional<UserEntity> findByEmail(String email);

}
