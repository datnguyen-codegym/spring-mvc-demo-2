package codegym.vn.repository;

import codegym.vn.entity.Customer;

import java.util.List;

public interface CustomerRepository {
    List<Customer> findAll();

    void save(Customer customer);

    Customer findById(Long id);
}
