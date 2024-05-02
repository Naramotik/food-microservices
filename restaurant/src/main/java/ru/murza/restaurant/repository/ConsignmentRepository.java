package ru.murza.restaurant.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.murza.foodmodel.models.Consignment;
import ru.murza.foodmodel.models.Dish;
import ru.murza.foodmodel.models.Ingredient;

import java.util.List;
import java.util.Optional;

@Repository
public interface ConsignmentRepository extends CrudRepository<Consignment, Long> {

    Optional<Consignment> findByTitle(String title);

    List<Consignment> findAllByIngredient(Ingredient ingredient);
}
