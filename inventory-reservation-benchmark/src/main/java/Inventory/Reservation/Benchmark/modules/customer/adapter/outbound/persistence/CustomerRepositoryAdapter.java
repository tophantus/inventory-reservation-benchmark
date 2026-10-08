package Inventory.Reservation.Benchmark.modules.customer.adapter.outbound.persistence;

import Inventory.Reservation.Benchmark.modules.customer.application.port.outbound.CustomerRepository;
import Inventory.Reservation.Benchmark.modules.customer.domain.Customer;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class CustomerRepositoryAdapter implements CustomerRepository {

    private final CustomerJpaRepository customerJpaRepository;

    public CustomerRepositoryAdapter(CustomerJpaRepository customerJpaRepository) {
        this.customerJpaRepository = customerJpaRepository;
    }

    @Override
    public Customer save(Customer customer) {
        CustomerJpaEntity entity = CustomerJpaMapper.toEntity(customer);
        return CustomerJpaMapper.toDomain(customerJpaRepository.save(entity));
    }

    @Override
    public Optional<Customer> findById(Long id) {
        return customerJpaRepository.findById(id)
                .map(CustomerJpaMapper::toDomain);
    }
}
