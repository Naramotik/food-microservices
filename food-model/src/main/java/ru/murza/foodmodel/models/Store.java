package ru.murza.foodmodel.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity(name = "Store")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "store", schema = "restaurant_schema", catalog = "postgres")
public class Store {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "address",
            nullable = false)
    @NotNull(message = "Not empty!")
    private String address;

    @OneToMany(mappedBy = "store")
    private List<Basket> baskets;

    @ManyToMany
    @JoinTable(
            name = "store_dish", schema = "restaurant_schema", catalog = "postgres",
            joinColumns = @JoinColumn(name = "store_id"),
            inverseJoinColumns = @JoinColumn(name = "dish_id")
    )
    private List<Dish> dishes;

//    @OneToMany(mappedBy = "store")
//    private List<ManagerInfo> managerInfos;
}
