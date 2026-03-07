package ma.ismagi.inventoryservice.repository;

import java.util.List;
import java.util.UUID;
import ma.ismagi.inventoryservice.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/** ProductRepository */
@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {
  @Query("SELECT p FROM Product p WHERE p.quantity <= :threshold")
  List<Product> findLowStockProducts(@Param("threshold") int threshold);
}
