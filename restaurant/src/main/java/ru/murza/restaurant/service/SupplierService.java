package ru.murza.restaurant.service;

import org.springframework.stereotype.Service;
import ru.murza.foodmodel.models.Composition;
import ru.murza.foodmodel.models.Dish;
import ru.murza.foodmodel.models.Supplier;
import ru.murza.restaurant.dto.DishCompositionDTO;
import ru.murza.restaurant.repository.SupplierRepository;

import java.util.List;

@Service
public class SupplierService {
    private final SupplierRepository supplierRepository;

    public SupplierService(SupplierRepository supplierRepository) {
        this.supplierRepository = supplierRepository;
    }

    public List<Supplier> findAll(){
        return (List<Supplier>) supplierRepository.findAll();
    }

    public Supplier save(Supplier supplier){
        return supplierRepository.save(supplier);
    }
}
