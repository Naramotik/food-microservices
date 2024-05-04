package ru.murza.restaurant.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceConsignmentDTO {
    Long supplierId;

    InvoiceDTO invoiceDTO;

    List<ConsignmentDTO> consignmentDTOList;


}
