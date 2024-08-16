package com.java06.luxurious_hotel.entity.keys;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;

@Data
@Embeddable
public class FoodMenuKey implements Serializable {

    @Column(name = "id_food")
    private int idFood;

    @Column(name = "id_menu")
    private int idMenu;

}
