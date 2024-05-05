package ru.murza.restaurant.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.murza.foodmodel.models.Bonus;

import java.util.Optional;

@Repository
public interface BonusRepository extends CrudRepository<Bonus, Long> {
    Bonus findFirstByOrderByIdDesc();
}
