package ru.murza.restaurant.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.murza.foodmodel.models.Consignment;
import ru.murza.foodmodel.models.Invoice;
import ru.murza.restaurant.dto.ConsignmentDTO;
import ru.murza.restaurant.dto.InvoiceConsignmentDTO;
import ru.murza.restaurant.dto.InvoiceDTO;
import ru.murza.restaurant.service.ConsignmentService;
import ru.murza.restaurant.service.InvoiceService;
import ru.murza.restaurant.util.Mapper;

import java.util.List;
import java.util.stream.Collectors;
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/v1/invoice")
@Tag(name = "Invoice")
public class InvoiceController {
    private final InvoiceService invoiceService;

    public InvoiceController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    @Operation(
            summary = "Добавление новой накладной с товарами",
            description = "Добавление новой накладной с товарами",
            responses = @ApiResponse(description = "CREATED", responseCode = "201")
    )
    @PostMapping
    public ResponseEntity<Invoice> addInvoice(@RequestBody InvoiceConsignmentDTO invoiceConsignmentDTO){
        InvoiceDTO invoiceDTO = invoiceConsignmentDTO.getInvoiceDTO();
        List<ConsignmentDTO> consignmentDTOList = invoiceConsignmentDTO.getConsignmentDTOList();
        Invoice invoice = Mapper.modelMapper.map(invoiceDTO, Invoice.class);
        List<Consignment> consignmentList = consignmentDTOList.stream().map(e -> Mapper.modelMapper.map(e, Consignment.class)).collect(Collectors.toList());
        Long supplierId = invoiceConsignmentDTO.getSupplierId();
        return new ResponseEntity<>(invoiceService.save(invoice, consignmentList, supplierId), HttpStatus.CREATED);
    }

    @Operation(
            summary = "Изменение статуса накладной",
            description = "Изменение статуса накладной",
            responses = @ApiResponse(description = "Success", responseCode = "200")
    )
    @PostMapping("/{state}")
    public ResponseEntity<Invoice> changeStateTo(@RequestBody InvoiceDTO invoiceDTO,
                                                 @PathVariable("state") String state){
        return new ResponseEntity<>(invoiceService.changeStateTo(invoiceDTO,state), HttpStatus.OK);
    }

    @Operation(
            summary = "Вывод всех накладных",
            description = "Вывод всех накладных",
            responses = @ApiResponse(description = "Success", responseCode = "200")
    )
    @GetMapping
    public ResponseEntity<List<Invoice>> findAll(){
        return new ResponseEntity<>(invoiceService.findAll(), HttpStatus.OK);
    }

    @Operation(
            summary = "Вывод накладной",
            description = "Вывод накладной",
            responses = @ApiResponse(description = "Success", responseCode = "200")
    )
    @GetMapping("/{id}")
    public ResponseEntity<Invoice> findAll(@PathVariable("id") Long id){
        return new ResponseEntity<>(invoiceService.findOne(id), HttpStatus.OK);
    }
}
