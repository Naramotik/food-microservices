package ru.murza.restaurant.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.murza.foodmodel.models.Basket;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderToSave {

    Basket basket;

    String address;

}
