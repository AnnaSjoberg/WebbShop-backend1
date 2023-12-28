package com.example.webbshopbackend1.Controllers;

import com.example.webbshopbackend1.Models.Customer;
import com.example.webbshopbackend1.Models.Orders;
import com.example.webbshopbackend1.Repos.CustomerRepo;
import com.example.webbshopbackend1.Repos.OrderRepo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/customerHTML")
public class CustomerHTMLController {
    private final CustomerRepo customerRepo;
    private final OrderRepo orderRepo;

    public CustomerHTMLController(CustomerRepo customerRepo, OrderRepo orderRepo) {
        this.customerRepo = customerRepo;
        this.orderRepo = orderRepo;
    }

    @RequestMapping("/getAll")
    public String getAllCustomers(Model model) {
        List<Customer> customers = customerRepo.findAll();
        model.addAttribute("allCustomers", customers);
        model.addAttribute("nameTitle", "Full name");
        model.addAttribute("ssnTitle", "Social security number");
        model.addAttribute("headline", "All customers");
        return "all-customers.html";
    }

    @RequestMapping("/getById/{id}")
    public String getById(@PathVariable Long id, Model model) {

        Customer customer = customerRepo.findById(id).orElse(null);
        int orders = orderRepo.findByCustomerId(id).size();

        if (customer.getName()==null){
            model.addAttribute("id", customer.getId());
            model.addAttribute("idTitle", "Former Customer ID");
            model.addAttribute("orderTitle", "Number of orders");
            model.addAttribute("orders", orders);
            model.addAttribute("deleted", true);
        }else {
            model.addAttribute("fullname", customer.getName());
            model.addAttribute("ssn", customer.getSocialSecurityNumber());
            model.addAttribute("id", customer.getId());
            model.addAttribute("orders", orders);
            model.addAttribute("nameTitle", "Full name");
            model.addAttribute("ssnTitle", "Social security number");
            model.addAttribute("idTitle", "Customer ID");
            model.addAttribute("orderTitle", "Number of orders");
            model.addAttribute("deleted", false);
        }
        return "one-customer.html";
    }

    @RequestMapping("/deleteById/{id}")
    public String delete(@PathVariable Long id, Model model) {
        int noOfOrders = orderRepo.findByCustomerId(id).size(); //skapa en int som motsvarar antalet ordrar kunden gjort
        if (noOfOrders <1) {
            customerRepo.deleteById(id);
        }else {
            Customer customer = customerRepo.findById(id).orElse(null);
            customer.setName(null);
            customer.setSocialSecurityNumber(null);
            customerRepo.save(customer);
            model.addAttribute("id", customer.getId());
            model.addAttribute("idTitle", "Former Customer ID");
            model.addAttribute("orderTitle", "Number of orders");
            model.addAttribute("orders", noOfOrders);
        }
            model.addAttribute("message", "CUSTOMER DELETED!");
            model.addAttribute("deleted", true);
            return "one-customer.html";
    }

    @PostMapping("/save")
    public String save(@RequestParam String fullname,
                       @RequestParam String ssn, Model model) {
        if (fullname.length() <1 || ssn.length() <1){
            model.addAttribute("errorMessage", "Customer must have a name and social security number");
        }else {
            customerRepo.save(new Customer(fullname, ssn));
        }
        return getAllCustomers(model);
    }

}
