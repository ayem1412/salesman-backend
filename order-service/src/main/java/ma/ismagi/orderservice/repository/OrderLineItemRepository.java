package ma.ismagi.orderservice.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import jakarta.validation.constraints.NotNull;
import ma.ismagi.orderservice.entity.OrderLineItem;

/**
 * OrderLineItemRepository
 */
@Repository
public interface OrderLineItemRepository extends JpaRepository<OrderLineItem, UUID> {
  List<OrderLineItem> findByOrderId(@NotNull UUID orderId);
}
