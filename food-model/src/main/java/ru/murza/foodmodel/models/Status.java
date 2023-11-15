package ru.murza.foodmodel.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity(name = "Status")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "status", schema = "order_schema", catalog = "postgres")
public class Status {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(
            name = "status",
            nullable = false
    )
    @NotNull(message = "Not empty!")
    private String name;

    @OneToMany(mappedBy = "status")
    private List<Order> orders;
}
