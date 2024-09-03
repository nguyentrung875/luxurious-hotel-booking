package com.java06.luxurious_hotel.repository;

import com.java06.luxurious_hotel.entity.BookingEntity;
import com.java06.luxurious_hotel.entity.RoomBookingEntity;
import com.java06.luxurious_hotel.entity.keys.RoomBookingKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomBookingRepository extends JpaRepository<RoomBookingEntity, RoomBookingKey> {
    List<RoomBookingEntity> findByBooking(BookingEntity booking);
    int deleteByBooking(BookingEntity bookingEntity);
    void deleteAllByBooking_Guest_Id(int id);
}
