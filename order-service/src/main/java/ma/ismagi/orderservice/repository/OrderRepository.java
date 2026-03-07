package ma.ismagi.orderservice.repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import ma.ismagi.orderservice.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/** OrderRepository */
@Repository
public interface OrderRepository extends JpaRepository<Order, UUID> {
  List<Order> findByCustomerIdOrderByCreatedAtDesc(UUID customerId);

  @Query("SELECT SUM(o.total) FROM Order o WHERE CAST(o.createdAt AS date) = CAST(:date AS date)")
  BigDecimal calculateDailyTotal(@Param("date") LocalDateTime date);

  @Query(
      "SELECT SUM(o.total) FROM Order o WHERE EXTRACT(MONTH FROM o.createdAt) = :month AND EXTRACT(YEAR FROM o.createdAt) = :year")
  BigDecimal calculateMonthlyTotal(@Param("month") int month, @Param("year") int year);

  @Query("SELECT SUM(o.total) FROM Order o WHERE EXTRACT(YEAR FROM o.createdAt) = :year")
  BigDecimal calculateYearlyTotal(@Param("year") int year);
}
