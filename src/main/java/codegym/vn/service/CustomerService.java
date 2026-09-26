package codegym.vn.service;


import codegym.vn.entity.Customer;

import java.util.List;

public interface CustomerService {
    List<Customer> getAll();

    void create(Customer customer);
}
