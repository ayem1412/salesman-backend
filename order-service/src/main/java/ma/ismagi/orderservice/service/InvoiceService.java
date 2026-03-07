package ma.ismagi.orderservice.service;

import lombok.RequiredArgsConstructor;
import ma.ismagi.orderservice.entity.Invoice;
import ma.ismagi.orderservice.repository.InvoiceRepository;
import org.springframework.stereotype.Service;

/** InvoiceService */
@Service
@RequiredArgsConstructor
public class InvoiceService {
  private final InvoiceRepository invoiceRepository;

  public Invoice createInvoice(Invoice invoice) {
    return invoiceRepository.save(invoice);
  }
}
