package ma.ismagi.orderservice.feignclient;

import java.util.UUID;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

/** CustomerClient */
@FeignClient(name = "customer-service")
public interface CustomerClient {
  @GetMapping("/api/v1/customers/{id}/exists")
  boolean checkCustomerExists(@PathVariable UUID id);

  @PutMapping("/api/v1/customers/{id}/add-to-history")
  void updateClientHistory(@PathVariable("id") UUID id, @RequestParam("saleId") UUID saleId);
}
