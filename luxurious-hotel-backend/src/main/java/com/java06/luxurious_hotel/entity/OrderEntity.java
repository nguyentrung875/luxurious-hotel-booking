package com.java06.luxurious_hotel.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity(name = "orders")
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "id_food")
    private FoodEntity food;

    @ManyToOne
    @JoinColumn(name = "id_reservation")
    private ReservationEntity reservation;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "order_time")
    private LocalDateTime orderTime;

    @ManyToOne
    @JoinColumn(name = "id_status")
    private OrderStatusEntity orderStatus;
}
