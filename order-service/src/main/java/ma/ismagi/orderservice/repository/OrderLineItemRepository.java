package ma.ismagi.orderservice.repository;

import jakarta.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;
import ma.ismagi.orderservice.entity.OrderLineItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/** OrderLineItemRepository */
@Repository
public interface OrderLineItemRepository extends JpaRepository<OrderLineItem, UUID> {
  List<OrderLineItem> findByOrderId(@NotNull UUID orderId);
}
