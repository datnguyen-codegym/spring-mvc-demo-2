package codegym.vn.jpa_repo;

import codegym.vn.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerJpaRepository extends JpaRepository<Customer, Long>, CustomerRepositoryCustom{
}
