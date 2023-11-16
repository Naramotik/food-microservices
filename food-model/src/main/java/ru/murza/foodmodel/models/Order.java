package ru.murza.foodmodel.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity(name = "Order")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "ord", schema = "order_schema", catalog = "postgres")
public class Order {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "basket_id")
    private Long basket_id;

    @Column(name = "client_id")
    private Long clientId;

    @Column(name = "store_id")
    private Long storeId;

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

    @ManyToOne
    @JoinColumn(name = "status_id")
    private Status status;

    @ElementCollection(targetClass = String.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "dishes", schema = "order_schema", catalog = "postgres", joinColumns = @JoinColumn(name = "order_id"))
    @Column(name = "dish", nullable = false)
    private List<String> dishes = new ArrayList<>();


}
