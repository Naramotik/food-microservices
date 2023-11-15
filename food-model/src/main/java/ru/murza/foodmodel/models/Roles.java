package ru.murza.foodmodel.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "roles", schema = "client_schema", catalog = "postgres")
public class Roles {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull(message = "Not empty!")
    @Column(name = "title")
    private String title;

    @JsonIgnore
    @OneToMany(mappedBy = "role")
    private List<Client> clients;

    @Override
    public String toString() {
        return title;
    }
}
