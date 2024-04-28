package ru.murza.restaurant.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.murza.foodmodel.models.State;

import java.util.Optional;

@Repository
public interface StateRepository extends CrudRepository<State, Long> {
    Optional<State> findByTitle(String title);
}
