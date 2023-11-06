package ru.murza.foodmodel.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity(name = "Ingredient")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "ingredient")
public class Ingredient {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(
            name = "title",
            nullable = false
    )
    @NotNull(message = "Not empty!")
    private String title;

    @OneToMany(mappedBy = "ingredients")
    private List<Composition> composition;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "measure_id")
    private Measure measure;
}
