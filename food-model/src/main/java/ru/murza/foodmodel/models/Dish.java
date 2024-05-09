package ru.murza.foodmodel.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.murza.foodmodel.enums.DishCategory;
import ru.murza.foodmodel.enums.DishStatus;

import java.util.List;

@Entity(name = "Dish")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "dish", schema = "restaurant_schema", catalog = "postgres")
public class Dish {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "cost")
    private Double cost;

    @Column(name = "title",
            nullable = false)
    @NotEmpty(message = "Not empty title!")
    private String title;

    @Column(name = "calorie")
    private Double calorie;

    @Column(name = "technologicalMap")
    private String technologicalMap;

    @Column(name = "discount")
    private Long discount;

    @OneToMany(mappedBy = "dish", cascade = CascadeType.ALL)
    private List<Composition> compositions;

//    @JsonIgnore
//    @ManyToMany(mappedBy = "dishes", fetch = FetchType.LAZY)
//    private List<Store> stores;

    @JsonIgnore
    @ManyToMany(mappedBy = "dishes", fetch = FetchType.LAZY)
    private List<Basket> baskets;

    @Enumerated(EnumType.STRING)
    private DishCategory dishCategory;

    @Enumerated(EnumType.STRING)
    private DishStatus dishStatus;
}
