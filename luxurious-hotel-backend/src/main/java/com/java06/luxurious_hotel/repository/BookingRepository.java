package com.java06.luxurious_hotel.repository;

import com.java06.luxurious_hotel.entity.BookingEntity;
import com.java06.luxurious_hotel.entity.RoomBookingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<BookingEntity, Integer> {
    List<BookingEntity> findByCheckOutAfterAndCheckInBefore(LocalDateTime inDate, LocalDateTime outDate);
//    List<BookingEntity> findByUser_Id(int userId);

    @Query("SELECT b FROM booking b " +
            "WHERE :selectDate BETWEEN b.checkIn AND b.checkOut")
    List<BookingEntity> findBookingsBySelectedDate(@Param("selectDate") LocalDateTime selectDate);
}
