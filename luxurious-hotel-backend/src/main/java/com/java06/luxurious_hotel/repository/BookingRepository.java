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

    @Query("SELECT b,rt FROM booking  b " +
            "JOIN b.guest u " +
            "JOIN b.roomBookings rb " +
            "JOIN rb.room r " +
            "JOIN r.roomType rt " +
            "WHERE b.guest.id = :userId")
    List<Object[]> findByGuest_Id(@Param("userId") int userId);
//    List<Object[]> findByGuest_Id(int userId);

    List<BookingEntity> findByCheckOutAfterAndCheckInBefore(LocalDateTime inDate, LocalDateTime outDate);
//    List<BookingEntity> findByUser_Id(int userId);

<<<<<<< HEAD
    @Query("SELECT b FROM booking b " +
            "WHERE :selectDate BETWEEN b.checkIn AND b.checkOut")
    List<BookingEntity> findBookingsBySelectedDate(@Param("selectDate") LocalDateTime selectDate);
=======
>>>>>>> ea3213ae195b4eaf4d5aa5660ce7a17ba4b9f0fd
}
