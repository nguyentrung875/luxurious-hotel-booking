package com.java06.luxurious_hotel.repository;

import com.java06.luxurious_hotel.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    List<UserEntity> findByRole_Name(String roleName);

}
