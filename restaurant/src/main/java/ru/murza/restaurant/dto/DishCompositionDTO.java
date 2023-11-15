package ru.murza.restaurant.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.murza.foodmodel.models.Composition;
import ru.murza.foodmodel.models.Dish;


import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DishCompositionDTO {

    Dish dish;

    Composition composition;
}

