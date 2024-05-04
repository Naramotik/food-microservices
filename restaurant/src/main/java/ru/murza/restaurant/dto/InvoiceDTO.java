package ru.murza.restaurant.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceDTO {

    private Long id;

    private Date transaction_date;

    private Date delivery_date;

    private String delivery_address;

    private Double totalPrice;

}
