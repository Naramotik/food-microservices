package ru.murza.restaurant.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.murza.foodmodel.models.Composition;

import java.util.List;

@Repository
public interface CompositionRepository extends CrudRepository<Composition, Long> {

    @Transactional
//    @Query(value = "select * from Composition where dish_id = :dishId", nativeQuery = true)
    public List<Composition> findAllByDish_id(@Param("dish_id") Long dish_id);
}
