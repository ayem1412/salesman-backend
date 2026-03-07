package ma.ismagi.orderservice.dto;

import java.math.BigDecimal;
import java.util.UUID;

/** OrderLineItemDto */
public record OrderLineItemDto(UUID productId, Integer quantity, BigDecimal price) {}
