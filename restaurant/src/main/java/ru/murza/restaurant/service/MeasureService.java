package ru.murza.restaurant.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.murza.foodmodel.models.Measure;
import ru.murza.restaurant.repository.MeasureRepository;


import java.util.ArrayList;
import java.util.List;

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

    public Measure putMeasure(Long measureId, Measure putMeasure){
        Measure measure = measureRepository.findById(measureId).get();
        if (putMeasure.getType() != null && !putMeasure.getType().equals(measure.getType()))
            measure.setType(putMeasure.getType());
        return measureRepository.save(measure);
    }

    public void deleteById(Long measureId){
        measureRepository.deleteById(measureId);
    }
}
