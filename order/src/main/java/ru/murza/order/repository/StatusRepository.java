package ru.murza.order.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.murza.foodmodel.models.Status;

import java.util.Optional;

@Repository
public interface StatusRepository extends CrudRepository<Status, Long> {
    Optional<Status> findByName(String name);
}
