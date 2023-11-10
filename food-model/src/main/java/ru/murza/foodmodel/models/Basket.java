package ru.murza.foodmodel.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity(name = "Order")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "table_order")
public class Basket {

    @Id
    @Column(name = "id",
            nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "order_date",
            nullable = false)
    @NotNull(message = "Not empty!")
    private Date order_date;

    @Column(name = "address",
            nullable = false)
    @NotNull(message = "Not empty!")
    private String address;

    @Column(name = "total_price",
            nullable = false)
    @NotNull(message = "Not empty!")
    private Double total_price;

    @Column(name = "status",
            nullable = false)
    @NotNull(message = "Not empty!")
    private String status;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    @ManyToOne
    @JoinColumn(name = "store_id")
    private Store store;

    @ManyToMany
    @JoinTable(name = "order_dish",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "dish_id"))
    private List<Dish> dishes;
}
