package ru.murza.foodmodel.models;

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

    @Column(name = "name",
            nullable = false)
    @Length(min = 1, max = 30)
    @NotEmpty(message = "Not empty name!")
    private String name;

    @Column(name = "number",
            nullable = false,
            unique = true)
    @Length(min = 6, max = 10, message = "Wrong number length!")
    @NotEmpty(message = "Not empty number!")
    private String number;


    @Column(name = "password",
            nullable = false)
    @NotEmpty(message = "Not empty password!")
    @Length(min = 6, message = "Short password!")
    private String password;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "secondName", column = @Column(name = "manager_second_name")),
            @AttributeOverride(name = "address", column = @Column(name = "manager_address")),
            @AttributeOverride(name = "itn", column = @Column(name = "manager_itn"))
    })
    private ManagerInfo managerInfo;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Roles role;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return Objects.equals(id, client.id) && Objects.equals(name, client.name) && Objects.equals(number, client.number) && Objects.equals(password, client.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, number, password);
    }

    //    public void setRoles(Roles role){
//        this.role = role;
//        role.getClients().add(this);
//    }
}
