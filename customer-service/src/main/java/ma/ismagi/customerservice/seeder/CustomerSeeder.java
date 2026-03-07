package ma.ismagi.customerservice.seeder;

import com.github.javafaker.Faker;
import lombok.RequiredArgsConstructor;
import ma.ismagi.customerservice.entity.Customer;
import ma.ismagi.customerservice.repository.CustomerRespository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomerSeeder implements CommandLineRunner {
  private final CustomerRespository customerRespository;

  private Customer generateFakeCustomer() {
    Faker faker = new Faker();

    return Customer.builder()
        .name(faker.name().fullName())
        .email(faker.internet().safeEmailAddress())
        .phone(faker.phoneNumber().cellPhone())
        .address(faker.address().fullAddress())
        .build();
  }

  @Override
  public void run(String... args) throws Exception {
    for (int i = 0; i < 10; i++) {
      var customer = generateFakeCustomer();

      customerRespository.save(customer);
    }
  }
}
