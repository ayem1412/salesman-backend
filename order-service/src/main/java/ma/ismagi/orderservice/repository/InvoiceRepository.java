package ma.ismagi.orderservice.repository;

import java.util.UUID;
import ma.ismagi.orderservice.entity.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/** InvoiceRepository */
@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, UUID> {}
