package ru.murza.restaurant.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.murza.saledelivery.models.Measure;

@Repository
public interface MeasureRepository extends CrudRepository<Measure, Long> {
}
