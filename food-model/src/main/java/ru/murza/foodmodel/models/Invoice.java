package ru.murza.foodmodel.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.murza.foodmodel.enums.InvoiceStatus;

import java.util.Date;
import java.util.List;

@Entity(name = "Invoice")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "invoice", schema = "restaurant_schema", catalog = "postgres")
public class Invoice {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "totalPrice")
    private Double totalPrice;

    @Column(
            name = "transaction_date",
            nullable = false
    )
    private Date transaction_date;

    @Column(
            name = "delivery_date",
            nullable = false
    )
    private Date delivery_date;

    @Column(
            name = "delivery_address",
            nullable = false
    )
    @NotEmpty(message = "Not empty title!")
    private String delivery_address;


    @OneToMany(mappedBy = "invoice", cascade = CascadeType.ALL)
    private List<Consignment> consignments;

    @ManyToOne
    @JoinColumn(name = "supplier_id")
    private Supplier supplier;

    @Enumerated(EnumType.STRING)
    private InvoiceStatus invoiceStatus;

}
