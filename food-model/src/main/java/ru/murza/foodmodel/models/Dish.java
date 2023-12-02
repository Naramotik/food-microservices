package ru.murza.foodmodel.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.murza.foodmodel.enums.DishCategory;

import java.util.List;

@Entity(name = "Dish")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "dish", schema = "restaurant_schema", catalog = "postgres")
public class Dish {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "cost",
            nullable = false)
    @NotEmpty(message = "Not empty cost!")
    private Double cost;

    @Column(name = "title",
            nullable = false)
    @NotEmpty(message = "Not empty title!")
    private String title;

    @OneToMany(mappedBy = "dish")
    private List<Composition> compositions;

    @JsonIgnore
    @ManyToMany(mappedBy = "dishes", fetch = FetchType.LAZY)
    private List<Store> stores;

    @JsonIgnore
    @ManyToMany(mappedBy = "dishes", fetch = FetchType.LAZY)
    private List<Basket> baskets;

    @Enumerated(EnumType.STRING)
    private DishCategory dishCategory;
}
