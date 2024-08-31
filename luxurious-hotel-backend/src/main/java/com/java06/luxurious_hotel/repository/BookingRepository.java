package com.java06.luxurious_hotel.repository;

import com.java06.luxurious_hotel.entity.BookingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

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
}
