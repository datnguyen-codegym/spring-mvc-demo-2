package codegym.vn.jpa_repo;

import codegym.vn.entity.Customer;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class CustomerRepositoryCustomImpl implements CustomerRepositoryCustom{

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Customer> findByUsernameForSpecific(String name) {
        return entityManager.createQuery("select c from Customer c", Customer.class).getResultList();
    }
}
