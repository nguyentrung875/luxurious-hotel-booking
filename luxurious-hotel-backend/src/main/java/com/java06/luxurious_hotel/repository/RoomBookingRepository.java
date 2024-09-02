package com.java06.luxurious_hotel.repository;

import com.java06.luxurious_hotel.entity.BookingEntity;
import com.java06.luxurious_hotel.entity.RoomBookingEntity;

import com.java06.luxurious_hotel.entity.RoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomBookingRepository extends JpaRepository<RoomBookingEntity,Integer> {

    List<BookingEntity> findBookingByRoom(RoomEntity room);


import com.java06.luxurious_hotel.entity.keys.RoomBookingKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface RoomBookingRepository extends JpaRepository<RoomBookingEntity, RoomBookingKey> {
    List<RoomBookingEntity> findByBooking(BookingEntity booking);
    int deleteByBooking(BookingEntity bookingEntity);

    @Query("SELECT rb FROM room_booking rb " +
            "JOIN booking b " +
            "WHERE :selectDate BETWEEN b.checkIn AND b.checkOut")
    List<RoomBookingEntity> findRoomBookingsBySelectedDate(@Param("selectDate") LocalDateTime selectDate);

}
