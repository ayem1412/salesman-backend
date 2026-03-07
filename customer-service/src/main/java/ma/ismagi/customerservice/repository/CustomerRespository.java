package ma.ismagi.customerservice.repository;

import java.util.UUID;
import ma.ismagi.customerservice.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/** CustomerRespository */
@Repository
public interface CustomerRespository
    extends JpaRepository<Customer, UUID>, JpaSpecificationExecutor<Customer> {}
