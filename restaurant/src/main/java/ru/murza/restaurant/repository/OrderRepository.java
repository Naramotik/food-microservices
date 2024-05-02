package ru.murza.restaurant.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.murza.foodmodel.models.Order;

@Repository
public interface OrderRepository extends CrudRepository<Order, Long> {
}
