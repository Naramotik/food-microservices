package ru.murza.restaurant.service;

import org.springframework.stereotype.Service;
import ru.murza.foodmodel.models.Consignment;
import ru.murza.foodmodel.models.Ingredient;
import ru.murza.restaurant.repository.ConsignmentRepository;
import ru.murza.restaurant.repository.IngredientRepository;

import java.util.List;

@Service
public class ConsignmentService {
    private final ConsignmentRepository consignmentRepository;

    private final IngredientRepository ingredientRepository;

    public ConsignmentService(ConsignmentRepository consignmentRepository, IngredientRepository ingredientRepository) {
        this.consignmentRepository = consignmentRepository;
        this.ingredientRepository = ingredientRepository;
    }

    public Consignment save(Consignment consignment, String ingredientTitle){
        if (consignmentRepository.findByTitle(consignment.getTitle()).isPresent()){
            Consignment consignmentAlreadyInStorage = consignmentRepository.findByTitle(consignment.getTitle()).get();
            consignmentAlreadyInStorage.setCount(consignment.getCount() + consignmentAlreadyInStorage.getCount());
            return consignmentRepository.save(consignmentAlreadyInStorage);
        } else {
            Ingredient ingredient = ingredientRepository.findByTitle(ingredientTitle).get();
            consignment.setIngredient(ingredient);
            return consignmentRepository.save(consignment);
        }
    }

    public List<Consignment> findAll(){
        return (List<Consignment>) consignmentRepository.findAll();
    }

    public void deleteById(Long id){
        consignmentRepository.deleteById(id);
    }

    public Consignment findOne(Long id) {
        return consignmentRepository.findById(id).get();
    }
}
