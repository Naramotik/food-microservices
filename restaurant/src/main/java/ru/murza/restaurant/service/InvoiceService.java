package ru.murza.restaurant.service;

import org.springframework.stereotype.Service;
import ru.murza.foodmodel.enums.InvoiceStatus;
import ru.murza.foodmodel.models.Consignment;
import ru.murza.foodmodel.models.Invoice;

import ru.murza.foodmodel.models.Supplier;
import ru.murza.restaurant.dto.InvoiceDTO;
import ru.murza.restaurant.repository.ConsignmentRepository;
import ru.murza.restaurant.repository.InvoiceRepository;
import ru.murza.restaurant.repository.SupplierRepository;


import java.util.ArrayList;
import java.util.List;

@Service
public class InvoiceService {
    private final InvoiceRepository invoiceRepository;
    private final SupplierRepository supplierRepository;
    private final ConsignmentRepository consignmentRepository;


    public InvoiceService(InvoiceRepository invoiceRepository, SupplierRepository supplierRepository, ConsignmentRepository consignmentRepository) {
        this.invoiceRepository = invoiceRepository;
        this.supplierRepository = supplierRepository;
        this.consignmentRepository = consignmentRepository;
    }

    public Invoice save(Invoice invoice, List<Consignment> consignments, Long supplierId) {
        Supplier supplier = supplierRepository.findById(supplierId).get();
        List<Consignment> consignmentList = new ArrayList<>();
        System.out.println("=====================");
        System.out.println(consignments.get(0).getId());
        consignments.forEach(consignment -> consignmentList.add(consignmentRepository.findById(consignment.getId()).get()));


        invoice.setSupplier(supplier);
        invoice.setConsignments(consignmentList);
        invoice.setInvoiceStatus(InvoiceStatus.WAITING);
        consignmentList.forEach(consignment -> consignment.setInvoice(invoice));

        return invoiceRepository.save(invoice);
    }

    public List<Invoice> findAll(){
        return (List<Invoice>) invoiceRepository.findAll();
    }

    public Invoice changeStateTo(InvoiceDTO invoiceDTO, String state) {
        Invoice invoice1 = invoiceRepository.findById(invoiceDTO.getId()).get();
        if (state.equals(InvoiceStatus.WAITING.name()))
            invoice1.setInvoiceStatus(InvoiceStatus.WAITING);
        if (state.equals(InvoiceStatus.TRANSIT.name()))
            invoice1.setInvoiceStatus(InvoiceStatus.TRANSIT);
        if (state.equals(InvoiceStatus.DONE.name()))
            invoice1.setInvoiceStatus(InvoiceStatus.DONE);
        if (state.equals(InvoiceStatus.DECLINE.name()))
            invoice1.setInvoiceStatus(InvoiceStatus.DECLINE);
        return invoiceRepository.save(invoice1);
    }

    public Invoice findOne(Long id) {
        return invoiceRepository.findById(id).get();
    }
}
