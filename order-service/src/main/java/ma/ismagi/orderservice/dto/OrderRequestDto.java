package ma.ismagi.orderservice.dto;

import java.util.List;
import java.util.UUID;

/**
 * OrderRequestDto
 */
public record OrderRequestDto(
  UUID customerId,
  List<OrderLineItemDto> items) {}
