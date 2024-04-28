package ru.murza.restaurant.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.murza.foodmodel.models.Composition;
import ru.murza.foodmodel.models.Ingredient;
import ru.murza.foodmodel.models.Measure;
import ru.murza.restaurant.exception.MeasureNotFoundException;
import ru.murza.restaurant.repository.MeasureRepository;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class MeasureTest {

    @InjectMocks
    private MeasureService measureService;

    @Mock
    private MeasureRepository measureRepository;

    @Test
    public void getMeasures_test() {
        Measure measure1 = new Measure(1L, "g", List.of(new Ingredient()));
        Measure measure2 = new Measure(2L, "ml", List.of(new Ingredient()));
        Mockito.when(measureRepository.findAll()).thenReturn(List.of(measure1, measure2));
        Assertions.assertEquals(2, measureService.getMeasures().size());
    }

    @Test
    public void save_test(){
        Measure measure = new Measure(1L, "g", List.of(new Ingredient()));
        Mockito.when(measureRepository.save(measure)).thenReturn(measure);
        Assertions.assertEquals(measure, measureService.save(measure));
    }

    @Test
    public void putMeasure_MeasureNotFoundException_Test() throws MeasureNotFoundException {
        Measure measure = new Measure(1L, "g", List.of(new Ingredient()));
        Optional<Measure> empty = Optional.empty();
        Mockito.when(measureRepository.findById(measure.getId())).thenReturn(empty);
        Assertions.assertThrows(MeasureNotFoundException.class, ()-> measureService.putMeasure(1L, measure));
    }

    @Test
    public void putMeasure_Test() throws MeasureNotFoundException {
        Measure measure = new Measure(1L, "g", List.of(new Ingredient()));
        Mockito.when(measureRepository.findById(measure.getId())).thenReturn(Optional.of(measure));
        Measure changedMeasure = new Measure(1L, "ml", List.of(new Ingredient()));
        Mockito.when(measureRepository.save(measure)).thenReturn(changedMeasure);
        Assertions.assertEquals(changedMeasure, measureService.putMeasure(1L, measure));
    }

    @Test
    public void deleteById_Test() {
        Measure measure = new Measure(1L, "g", List.of(new Ingredient()));
        measureService.deleteById(measure.getId());
        Mockito.verify(measureRepository, Mockito.times(1)).deleteById(measure.getId());
    }

}
