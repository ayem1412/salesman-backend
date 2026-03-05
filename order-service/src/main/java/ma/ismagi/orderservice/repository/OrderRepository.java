package ma.ismagi.orderservice.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ma.ismagi.orderservice.entity.Order;

/**
 * OrderRepository
 */
@Repository
public interface OrderRepository extends JpaRepository<Order, UUID> {}
