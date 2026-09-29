package codegym.vn.repository;

import codegym.vn.entity.Customer;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

@Repository
@RequiredArgsConstructor
public class CustomerRepositoryImpl implements CustomerRepository{
    private final SessionFactory sessionFactory;

    @Override
    public List<Customer> findAll() {
        try(EntityManager entityManager = sessionFactory.createEntityManager()){
            String queryStr = "SELECT c FROM Customer AS c";
            TypedQuery<Customer> query = entityManager.createQuery(queryStr, Customer.class);
            return query.getResultList();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void save(Customer customer) {
        Transaction transaction = null;
        Customer origin;
        if (Objects.nonNull(customer.getId())) {
            origin = findById(customer.getId());
        } else {
            origin = new Customer();
        }
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            origin.setUsername(customer.getUsername());
            origin.setEmail(customer.getEmail());
            origin.setStatus(customer.getStatus());
            origin.setBirthday(customer.getBirthday());
            session.saveOrUpdate(origin);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public Customer findById(Long id) {
        try(EntityManager entityManager = sessionFactory.createEntityManager()) {
            String queryStr = "SELECT c FROM Customer AS c WHERE c.id = :id";
            TypedQuery<Customer> query = entityManager.createQuery(queryStr, Customer.class);
            query.setParameter("id", id);
            return query.getSingleResult();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
