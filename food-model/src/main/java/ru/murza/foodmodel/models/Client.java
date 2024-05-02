package ru.murza.foodmodel.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.util.List;
import java.util.Objects;

@Entity(name = "Client")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "client", schema = "client_schema", catalog = "postgres")
public class Client {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "number",
            nullable = false,
            unique = true)
    @Length(min = 6, max = 10, message = "Wrong number length!")
    @NotEmpty(message = "Not empty number!")
    private String number;

    @Column(name = "bonus")
    private Long bonus;

    @OneToMany(mappedBy = "client")
    private List<Schedule> schedules;

    @OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(name = "worker_info_id", referencedColumnName = "id")
    private WorkerInfo workerInfo;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Roles role;


    //    public void setRoles(Roles role){
//        this.role = role;
//        role.getClients().add(this);
//    }
}
