package Inventory.Reservation.Benchmark.modules.customer.application.port.outbound;

import Inventory.Reservation.Benchmark.modules.customer.domain.Customer;

import java.util.Optional;

public interface CustomerRepository {

    Customer save(Customer customer);

    Optional<Customer> findById(Long id);
}
