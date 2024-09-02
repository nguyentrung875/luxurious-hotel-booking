package com.java06.luxurious_hotel.repository;


import com.java06.luxurious_hotel.dto.RoomAvailableInfo;
import com.java06.luxurious_hotel.dto.RoomBookingInfo;
import com.java06.luxurious_hotel.entity.RoomEntity;
import com.java06.luxurious_hotel.entity.RoomTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<RoomEntity, Integer> {


    List<RoomEntity> findRoomEntityByRoomType(RoomTypeEntity roomTypeEntity);


    @Query("SELECT new com.java06.luxurious_hotel.dto.RoomAvailableInfo(r, rt) " +
            "FROM room r join r.roomType rt " +
            "WHERE r.id NOT IN (SELECT rb.room.id FROM booking b " +
            "JOIN b.roomBookings rb WHERE b.checkOut > :checkin AND b.checkIn < :checkout) ")
    List<RoomAvailableInfo> findAvailableRoom(@Param("checkin") LocalDateTime checkin, @Param("checkout") LocalDateTime checkout);
}
