package ma.ismagi.orderservice.feignclient;

import java.util.UUID;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "inventory-service")
public interface InventoryClient {
  @GetMapping("/api/v1/inventory/check-stock")
  boolean isInStock(
      @RequestParam("productId") UUID productId, @RequestParam("quantity") Integer quantity);

  @PutMapping("/api/v1/inventory/reduce-stock")
  void reduceStock(
      @RequestParam("productId") UUID productId, @RequestParam("quantity") Integer quantity);
}
