package ru.murza.restaurant.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.murza.foodmodel.models.Ingredient;

@Repository
public interface IngredientRepository extends CrudRepository<Ingredient, Long> {
}
