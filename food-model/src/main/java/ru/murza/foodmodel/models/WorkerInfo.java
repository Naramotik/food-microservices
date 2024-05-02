package ru.murza.foodmodel.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Entity(name = "WorkerInfo")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "workerInfo", schema = "client_schema", catalog = "postgres")
public class WorkerInfo {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name",
            nullable = false)
    @Length(min = 1, max = 30)
    @NotEmpty(message = "Not empty name!")
    private String name;

    @Column(name = "second_name",
            nullable = false)
    @Length(min = 1, max = 30)
    @NotEmpty(message = "Not empty second_name!")
    private String secondName;

    @Length(max = 12)
    @Column(name = "itn", length = 12)
    private String itn;

    @Column(name = "salary")
    private Double salary;

    @Column(name = "password",
            nullable = false)
    @NotEmpty(message = "Not empty password!")
    @Length(min = 5, message = "Short password!")
    private String password;

    @JsonIgnore
    @OneToOne(mappedBy = "workerInfo", cascade=CascadeType.ALL)
    private Client client;
}
