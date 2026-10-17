package codegym.vn.controller;

import codegym.vn.entity.Customer;
import codegym.vn.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/customer")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @RequestMapping
    public String customerPage(Model model) {
        return "customer";
    }

    @ResponseBody
    @GetMapping(value = "/get-all",
          produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public List<Customer> getCustomers() {
        return customerService.getAll();
    }

    @GetMapping("/customer-list-fragment")
    public String loadUserList(Model model) {
        model.addAttribute("users", customerService.getAll());

        // Chỉ render fragment, không render cả page
        return "customer-detail-list :: userList";
    }

    @ResponseBody
    @PostMapping(
            value = "/create",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody Customer customer) {
        customerService.create(customer);
    }

    @ResponseBody
    @GetMapping("/find-by-name")
    public List<Customer> findByName(@RequestParam("username") String username) {
        return customerService.findByName(username);
    }
}
