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

    @Column(
            name = "transaction_date",
            nullable = false
    )
    @NotEmpty(message = "Not empty title!")
    private Date transaction_date;

    @Column(
            name = "delivery_date",
            nullable = false
    )
    @NotEmpty(message = "Not empty title!")
    private Date delivery_date;

    @Column(
            name = "delivery_address",
            nullable = false
    )
    @NotEmpty(message = "Not empty title!")
    private String delivery_address;


    @ManyToMany(mappedBy = "invoices", fetch = FetchType.LAZY)
    private List<Consignment> consignments;

    @ManyToOne
    @JoinColumn(name = "supplier_id")
    private Supplier supplier;

    @Enumerated(EnumType.STRING)
    private InvoiceStatus invoiceStatus;
//    @ManyToOne
//    @JoinColumn(name = "state_id")
//    private State state;
}
