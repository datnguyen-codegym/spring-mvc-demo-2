package codegym.vn.jpa_repo;

import codegym.vn.entity.Customer;

import java.util.List;

public interface CustomerRepositoryCustom {
    List<Customer> findByUsernameForSpecific(String name);
}
