package ru.murza.restaurant.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.murza.foodmodel.models.Measure;
import ru.murza.foodmodel.models.Supplier;

@Repository
public interface SupplierRepository extends CrudRepository<Supplier, Long> {
}
