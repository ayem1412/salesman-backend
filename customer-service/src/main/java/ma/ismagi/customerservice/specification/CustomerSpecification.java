package ma.ismagi.customerservice.specification;

import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import ma.ismagi.customerservice.entity.Customer;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

/** CustomerSpecification */
public class CustomerSpecification {
  public static Specification<Customer> filterCustomers(
      String name, String email, String phone, String address) {
    return (root, query, criteriaBuilder) -> {
      List<Predicate> predicates = new ArrayList<>();

      if (StringUtils.hasText(name))
        predicates.add(
            criteriaBuilder.like(
                criteriaBuilder.upper(root.get("name")), "%" + name.toUpperCase() + "%"));

      if (StringUtils.hasText(email))
        predicates.add(
            criteriaBuilder.like(
                criteriaBuilder.upper(root.get("email")), "%" + email.toUpperCase() + "%"));

      if (StringUtils.hasText(phone))
        predicates.add(criteriaBuilder.equal(root.get("phone"), phone));

      if (StringUtils.hasText(address))
        predicates.add(
            criteriaBuilder.like(
                criteriaBuilder.upper(root.get("address")), "%" + address.toUpperCase() + "%"));

      return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    };
  }
}
