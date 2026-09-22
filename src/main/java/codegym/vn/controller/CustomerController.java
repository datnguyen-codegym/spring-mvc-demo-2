package codegym.vn.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
@RequestMapping("/customer")
public class CustomerController {
    @GetMapping
    public String getCustomers(Model model, HttpSession session) {
        model.addAttribute("userName", "12345");
        model.addAttribute("today", "22 tháng 9 năm 2026");
        session.setAttribute("user", Map.of( "name", "K", "age", 11));
        return "customer";
    }
}
