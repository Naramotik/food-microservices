package ru.murza.restaurant.dto;


import jakarta.validation.constraints.NotNull;
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

    @NotNull(message = "Dish is null")
    Dish dish;
    @NotNull(message = "Composition is null")
    Composition composition;

}

