package ru.murza.restaurant.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.murza.foodmodel.models.Measure;
import ru.murza.restaurant.exception.MeasureNotFoundException;
import ru.murza.restaurant.repository.MeasureRepository;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MeasureService {

    @Autowired
    private MeasureRepository measureRepository;

    public List<Measure> getMeasures(){
        List<Measure> measures = new ArrayList<>();
        measureRepository.findAll().forEach(measures::add);
        return measures;
    }

    public Measure save(Measure measure){
        return measureRepository.save(measure);
    }

    public Measure putMeasure(Long measureId, Measure putMeasure) throws MeasureNotFoundException {
        Optional<Measure> measureOptional = measureRepository.findById(measureId);
        if (measureOptional.isEmpty()){
            throw new MeasureNotFoundException("Measure not found");
        } else {
            Measure measure = measureOptional.get();
            if (putMeasure.getType() != null && !putMeasure.getType().equals(measure.getType()))
                measure.setType(putMeasure.getType());
            return measureRepository.save(measure);
        }
    }

    public void deleteById(Long measureId){
        measureRepository.deleteById(measureId);
    }
}
