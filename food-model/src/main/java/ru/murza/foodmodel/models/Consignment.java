package ru.murza.foodmodel.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity(name = "Consignment")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "consignment", schema = "restaurant_schema", catalog = "postgres")
public class Consignment {

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


    @Column(name = "expiration_date")
    private Date expiration_date;

    @Column(name = "count")
    private Double count;

    @Column(name = "price")
    private Double price;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "invoice_id")
    private Invoice invoice;


    @ManyToOne
    @JoinColumn(name = "ingredient_id")
    private Ingredient ingredient;
}
