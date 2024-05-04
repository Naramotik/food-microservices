package ru.murza.foodmodel.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity(name = "Bonus")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "bonus", schema = "restaurant_schema", catalog = "postgres")
public class Bonus {

    @Id
    @Column(name = "id",
            nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "percent")
    private Long percent;

    @JsonIgnore
    @OneToMany(mappedBy = "bonus")
    private List<Basket> baskets;


}
