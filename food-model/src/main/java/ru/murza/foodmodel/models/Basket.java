package ru.murza.foodmodel.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.murza.foodmodel.enums.BasketStatus;
import ru.murza.foodmodel.enums.DishCategory;

import java.util.Date;
import java.util.List;

@Entity(name = "Basket")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
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

    @Column(name = "acceptance_date")
    private Date acceptance_date;

    @Enumerated(EnumType.STRING)
    private BasketStatus basketStatus;

//    @JsonIgnore
//    @ManyToOne
//    @JoinColumn(name = "store_id")
//    private Store store;


    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "basket_dish", schema = "restaurant_schema", catalog = "postgres",
            joinColumns = @JoinColumn(name = "basket_id"),
            inverseJoinColumns = @JoinColumn(name = "dish_id"))
    private List<Dish> dishes;

}
