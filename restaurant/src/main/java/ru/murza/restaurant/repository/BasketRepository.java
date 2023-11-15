package ru.murza.restaurant.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.murza.foodmodel.models.Basket;

import java.util.Optional;

@Repository
public interface BasketRepository extends CrudRepository<Basket, Long> {
    Optional<Basket> findByClientId(Long client_id);
}
