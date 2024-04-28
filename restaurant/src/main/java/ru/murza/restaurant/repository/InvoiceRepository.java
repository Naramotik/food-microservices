package ru.murza.restaurant.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.murza.foodmodel.models.Invoice;
import ru.murza.foodmodel.models.Measure;

@Repository
public interface InvoiceRepository extends CrudRepository<Invoice, Long> {
}
