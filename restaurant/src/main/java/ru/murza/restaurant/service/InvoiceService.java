package ru.murza.restaurant.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.murza.foodmodel.enums.InvoiceStatus;
import ru.murza.foodmodel.models.Consignment;
import ru.murza.foodmodel.models.Invoice;

import ru.murza.restaurant.repository.InvoiceRepository;


import java.util.List;

@Service
public class InvoiceService {
    private final InvoiceRepository invoiceRepository;


    public InvoiceService(InvoiceRepository invoiceRepository) {
        this.invoiceRepository = invoiceRepository;
    }

    public Invoice save(Invoice invoice, List<Consignment> consignments) {
        invoice.setConsignments(consignments);
        invoice.setInvoiceStatus(InvoiceStatus.WAITING);
        return invoiceRepository.save(invoice);
    }

    public List<Invoice> findAll(){
        return (List<Invoice>) invoiceRepository.findAll();
    }

    public Invoice changeStateTo(Invoice invoice, String state) {
        if (state.equals(InvoiceStatus.WAITING.name()))
            invoice.setInvoiceStatus(InvoiceStatus.WAITING);
        if (state.equals(InvoiceStatus.TRANSIT.name()))
            invoice.setInvoiceStatus(InvoiceStatus.TRANSIT);
        if (state.equals(InvoiceStatus.DONE.name()))
            invoice.setInvoiceStatus(InvoiceStatus.DONE);
        if (state.equals(InvoiceStatus.DECLINE.name()))
            invoice.setInvoiceStatus(InvoiceStatus.DECLINE);
        return invoiceRepository.save(invoice);
    }
}