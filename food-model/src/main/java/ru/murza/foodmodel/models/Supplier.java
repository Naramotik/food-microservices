package ru.murza.foodmodel.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity(name = "Supplier")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "supplier", schema = "restaurant_schema", catalog = "postgres")
public class Supplier {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(
            name = "phone",
            nullable = false
    )
    @NotEmpty(message = "Not empty title!")
    private String phone;

    @Column(
            name = "name",
            nullable = false
    )
    @NotEmpty(message = "Not empty title!")
    private String name;

}
