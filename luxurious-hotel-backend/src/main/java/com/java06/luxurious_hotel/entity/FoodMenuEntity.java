package com.java06.luxurious_hotel.entity;

import com.java06.luxurious_hotel.entity.keys.FoodMenuKey;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity(name = "food_menu")
public class FoodMenuEntity {
    @EmbeddedId
    private FoodMenuKey foodMenuKey;

    @ManyToOne
    @MapsId("idFood")
    @JoinColumn(name = "id_food")
    private FoodEntity food;

    @ManyToOne
    @MapsId("idMenu")
    @JoinColumn(name = "id_menu")
    private MenuEntity menu;
}
