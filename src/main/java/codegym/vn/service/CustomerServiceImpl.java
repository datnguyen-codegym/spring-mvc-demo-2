package codegym.vn.service;

import codegym.vn.entity.Customer;
import codegym.vn.jpa_repo.CustomerJpaRepository;
import codegym.vn.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService{

    private final CustomerRepository customerRepository;

    private final CustomerJpaRepository customerJpaRepository;

    @Override
    public List<Customer> getAll() {
//        return customerRepository.findAll();
        return customerJpaRepository.findAll();
    }



    @Override
    public void create(Customer customer) {
         customerRepository.save(customer);
    }

    @Override
    public List<Customer> findByName(String username) {
        return customerJpaRepository.findByUsernameForSpecific(username + "%");
    }
}
