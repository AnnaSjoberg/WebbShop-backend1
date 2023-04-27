package com.example.webbshopbackend1.Controllers;

import com.example.webbshopbackend1.Models.Customer;
import com.example.webbshopbackend1.Models.Item;
import com.example.webbshopbackend1.Models.Orders;
import com.example.webbshopbackend1.Repos.CustomerRepo;
import com.example.webbshopbackend1.Repos.ItemRepo;
import com.example.webbshopbackend1.Repos.OrderRepo;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderRepo orderRepo;
    private final CustomerRepo customerRepo;
    private final ItemRepo itemRepo;

    OrderController(OrderRepo orderRepo, CustomerRepo customerRepo, ItemRepo itemRepo) {
        this.orderRepo = orderRepo;
        this.customerRepo = customerRepo;
        this.itemRepo = itemRepo;
    }

    @RequestMapping("/getAll")
    public List<Orders> getAllOrders() {
        return orderRepo.findAll();
    }

    @RequestMapping("/getByCustomerId/{customerId}")
    public List<Orders> getOrdersByCustomerId(@PathVariable Long customerId) {
        List<Orders> orders = orderRepo.findAll();
        List<Orders> customerOrders = new ArrayList<>();
        for (Orders order : orders) {
            if (order.getCustomer().getId() == customerId) {
                customerOrders.add(order);
            }
        }
        return customerOrders;
    }

  /*  @RequestMapping("/buy/{customerId}/{itemId}")
    public String addOrder(@PathVariable Long customerId, @PathVariable Long itemId) {
        Item item = itemRepo.findById(itemId).get();
        Customer customer = customerRepo.findById(customerId).get();
        if (item != null && customer != null) {
            orderRepo.save(new Orders(LocalDate.now(), customer, List.of(item)));
            return "Order added";
        } else {
            return "Order failed";
        }
    }

   */

    //curl -X POST -H "Content-Type: application/json" -d "{\"customerId\":\"1\",\"itemIds\":[\"2\",\"3\"]}" http://localhost:8080/orders/buy/1/2,3
   /* @PostMapping(path = "/buy/{customerId}/{itemIds}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public String addOrder(@PathVariable Long customerId, @PathVariable List<Long> itemIds) {
        List<Item> items = new ArrayList<>();
        for (Long itemId :itemIds) {            //måste gå via en for-loop för att kunna lägga till flera av samma id i samma order
            Item item = itemRepo.findById(itemId).orElse(null);
            if (item != null) {
                items.add(item);
            }
        }
        Customer customer = customerRepo.findById(customerId).orElse(null); //orElse(null) krävs för att inte få 500-fel om obefintligt ID anges
        if (items != null && customer != null) {
            orderRepo.save(new Orders(LocalDate.now(), customer, items));
            return "Order added";
        } else {
            return "Order failed";
        }

    }*/

    @PostMapping(path = "/buy") //curl -X POST -H "Content-Type: application/json" "http://localhost:8080/orders/buy?customerId=1&itemIds=2&itemIds=3"
    public String addOrder(@RequestParam Long customerId, @RequestParam List<Long> itemIds) {
        List<Item> items = new ArrayList<>();
        for (Long itemId :itemIds) {            //måste gå via en for-loop för att kunna lägga till flera av samma id i samma order
            Item item = itemRepo.findById(itemId).orElse(null);
            if (item != null) {
                items.add(item);
            }
            else {      //else sats för att breaka metoden att köra vidare
                return "Order failed";
            }
        }
        Customer customer = customerRepo.findById(customerId).orElse(null); //orElse(null) krävs för att inte få 500-fel om obefintligt ID anges
        if (items != null && customer != null) {
            orderRepo.save(new Orders(LocalDate.now(), customer, items));
            return "Order added";
        } else {
            return "Order failed";
        }

    }

}
