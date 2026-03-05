package ma.ismagi.orderservice.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ma.ismagi.orderservice.entity.Invoice;

/**
 * InvoiceRepository
 */
@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, UUID> {}
