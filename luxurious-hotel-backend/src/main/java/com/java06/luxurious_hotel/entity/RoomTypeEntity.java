package com.java06.luxurious_hotel.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Data
@Entity(name = "room_type")
public class RoomTypeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "overview")
    private String overview;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "area")
    private float area;

    @Column(name = "capacity")
    private int capacity;

    @ManyToOne
    @JoinColumn(name = "id_bed_type")
    private BedTypeEntity bedType;

    @Column(name = "image")
    private String image;

    @OneToMany(mappedBy = "roomType")
    private Set<RoomEntity> rooms;


}
