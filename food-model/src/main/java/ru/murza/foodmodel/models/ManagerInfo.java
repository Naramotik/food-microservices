package ru.murza.foodmodel.models;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class ManagerInfo implements Serializable {

    @Column(name = "second_name")
    private String secondName;

    @Column(name = "address")
    private String address;

    @Length(max = 12)
    @Column(name = "itn", length = 12)
    private String itn;

//    @ManyToOne
//    @JoinColumn(name = "store_id")
//    private Store store;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ManagerInfo that = (ManagerInfo) o;
        return Objects.equals(secondName, that.secondName) && Objects.equals(address, that.address) && Objects.equals(itn, that.itn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(secondName, address, itn);
    }
}
