package ma.ismagi.orderservice.repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ma.ismagi.orderservice.entity.Order;

/**
 * OrderRepository
 */
@Repository
public interface OrderRepository extends JpaRepository<Order, UUID> {
  List<Order> findByCustomerIdOrderBySaleDateDesc(UUID customerId);

  @Query("SELECT SUM(o.total) FROM Order o WHERE CAST(o.saleDate AS date) = CAST(:date AS date)")
  BigDecimal calculateDailyTotal(@Param("date") LocalDateTime date);

  @Query("SELECT SUM(o.total) FROM Order o WHERE EXTRACT(MONTH FROM o.saleDate) = :month AND EXTRACT(YEAR FROM o.saleDate) = :year")
  BigDecimal calculateMonthlyTotal(@Param("month") int month, @Param("year") int year);

  @Query("SELECT SUM(o.total) FROM Order o WHERE EXTRACT(YEAR FROM o.saleDate) = :year")
  BigDecimal calculateYearlyTotal(@Param("year") int year);
}
