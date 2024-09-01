package com.java06.luxurious_hotel.repository;

import com.java06.luxurious_hotel.entity.RoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<RoomEntity, Integer> {
    RoomEntity findRoomEntityById(int roomId);

    @Query("SELECT rb.room FROM room_booking rb " +
            "JOIN rb.booking b " +
            "WHERE b.checkOut > :checkInDate " +
            "AND b.checkIn < :checkOutDate AND b.bookingStatus.id <> 5")
    List<RoomEntity> findBookedRoomsByDateRange(@Param("checkInDate") LocalDateTime checkInDate,
                                          @Param("checkOutDate") LocalDateTime checkOutDate
                                            );
}
