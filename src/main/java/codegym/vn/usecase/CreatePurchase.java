package codegym.vn.usecase;

import codegym.vn.entity.Customer;
import codegym.vn.service.CustomerService;
import org.springframework.transaction.annotation.Transactional;

public class CreatePurchase {
    CustomerService customerService;
//    OrderService orderService;
//    PaymentService paymentService;

    @Transactional
    public void createPurchase() {

    }
}
