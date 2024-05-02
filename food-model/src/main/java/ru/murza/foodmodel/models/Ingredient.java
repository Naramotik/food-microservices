package ru.murza.foodmodel.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import java.util.List;
import java.util.Objects;

@Entity(name = "Ingredient")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "ingredient", schema = "restaurant_schema", catalog = "postgres")
public class Ingredient{

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(
            name = "title",
            nullable = false
    )
    @NotEmpty(message = "Not empty title!")
    private String title;

    @JsonIgnore
    @OneToMany(mappedBy = "ingredient")
    private List<Composition> compositions;

    @ManyToOne
    @JoinColumn(name = "measure_id")
    private Measure measure;

    @JsonIgnore
    @OneToMany(mappedBy = "ingredient")
    private List<Consignment> consignments;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ingredient that = (Ingredient) o;
        return id.equals(that.id) && title.equals(that.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title);
    }
}
