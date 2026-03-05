package ma.ismagi.customerservice.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ma.ismagi.customerservice.entity.Customer;

/**
 * CustomerRespository
 */
@Repository
public interface CustomerRespository extends JpaRepository<Customer, UUID> {}
