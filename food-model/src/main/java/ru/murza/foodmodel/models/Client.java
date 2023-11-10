package ru.murza.foodmodel.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Objects;

@Entity(name = "Client")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "client")
public class Client {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name",
            nullable = false)
    @NotNull(message = "Not empty!")
    private String name;

    @Column(name = "number",
            nullable = false,
            unique = true)
    @NotNull(message = "Not empty!")
    private String number;

    @NotNull(message = "Not empty!")
    @Column(name = "password",
            nullable = false)
    private String password;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "secondName", column = @Column(name = "manager_second_name")),
            @AttributeOverride(name = "address", column = @Column(name = "manager_address")),
            @AttributeOverride(name = "itn", column = @Column(name = "manager_itn"))
    })
    private ManagerInfo managerInfo;


    @OneToMany(mappedBy = "client")
    private List<Basket> baskets;


    @ManyToOne
    @JoinColumn(name = "role_id")
    private Roles role;

    public String getRoles() {
        return role.toString();
    }

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
