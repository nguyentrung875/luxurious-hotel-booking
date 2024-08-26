package com.java06.luxurious_hotel.repository;

import com.java06.luxurious_hotel.entity.RoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepository extends JpaRepository<RoomEntity, Integer> {
    RoomEntity findRoomEntityById(int roomId);
}
