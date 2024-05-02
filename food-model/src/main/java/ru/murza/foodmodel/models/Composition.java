package ru.murza.foodmodel.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "Composition")
@Table(name = "composition", schema = "restaurant_schema", catalog = "postgres")
public class Composition {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "count",
            nullable = false)
    @NotNull(message = "Not empty!")
    @Min(value = 1, message = "Count should be > 0!")
    private Double count;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "dish_id")
    private Dish dish;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ingredient_id")
    private Ingredient ingredient;

    @Override
    public String toString() {
        return "Composition{" +
                "id=" + id +
                ", count=" + count +
                ", ingredient=" + ingredient +
                '}';
    }
}
