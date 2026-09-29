package codegym.vn.repository;

import codegym.vn.entity.Customer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Objects;

@Repository
@RequiredArgsConstructor
public class CustomerRepositoryImpl implements CustomerRepository{

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Customer> findAll() {
//        try(EntityManager entityManager = sessionFactory.createEntityManager()){
            String queryStr = "SELECT c FROM Customer AS c";
            TypedQuery<Customer> query = entityManager.createQuery(queryStr, Customer.class);
            return query.getResultList();
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
    }

    @Override
    @Transactional
    public void save(Customer customer) {
        Customer origin;
        if (Objects.nonNull(customer.getId())) {
            origin = findById(customer.getId());
        } else {
            origin = new Customer();
        }
        origin.setUsername(customer.getUsername());
        origin.setEmail(customer.getEmail());
        origin.setStatus(customer.getStatus());
        origin.setBirthday(customer.getBirthday());
        entityManager.persist(origin);
        throw new IllegalArgumentException();
    }

    @Override
    public Customer findById(Long id) {
//        try(EntityManager entityManager = sessionFactory.createEntityManager()) {
            String queryStr = "SELECT c FROM Customer AS c WHERE c.id = :id";
            TypedQuery<Customer> query = entityManager.createQuery(queryStr, Customer.class);
            query.setParameter("id", id);
            return query.getSingleResult();
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
    }
}
