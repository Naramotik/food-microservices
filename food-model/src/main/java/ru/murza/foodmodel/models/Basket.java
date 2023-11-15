package ru.murza.foodmodel.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity(name = "Basket")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "basket", schema = "restaurant_schema", catalog = "postgres")
public class Basket {

    @Id
    @Column(name = "id",
            nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "client_id",
            nullable = false)
    @NotNull(message = "Not empty")
    private Long clientId;

    @Column(name = "order_id")
    private Long orderId;

    @Column(name = "total_price",
            nullable = false)
    @NotNull(message = "Not empty!")
    private Double total_price;

    @ManyToOne
    @JoinColumn(name = "store_id")
    private Store store;

    @ManyToMany
    @JoinTable(name = "basket_dish",
            joinColumns = @JoinColumn(name = "basket_id"),
            inverseJoinColumns = @JoinColumn(name = "dish_id"))
    private List<Dish> dishes;
}
