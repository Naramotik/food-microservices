package ru.murza.foodmodel.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
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
    @NotEmpty(message = "Not empty status name!")
    private String name;

    @OneToMany(mappedBy = "status")
    @JsonIgnore
    private List<Order> orders;
}
