package ru.murza.foodmodel.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity(name = "Measure")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "measure", schema = "restaurant_schema", catalog = "postgres")
public class Measure {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(
            name = "type",
            nullable = false
    )
    @NotNull(message = "Not empty!")
    private String type;

    @JsonIgnore
    @OneToMany(mappedBy = "measure")
    private List<Ingredient> ingredients;
}
