package ru.murza.foodmodel.models;

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
@Table(name = "measure")
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

    @OneToMany(mappedBy = "measure")
    private List<Ingredient> ingredients;
}
