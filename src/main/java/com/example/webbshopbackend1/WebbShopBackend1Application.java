package com.example.webbshopbackend1;

import com.example.webbshopbackend1.Models.Customer;
import com.example.webbshopbackend1.Models.Item;
import com.example.webbshopbackend1.Models.Orders;
import com.example.webbshopbackend1.Repos.CustomerRepo;
import com.example.webbshopbackend1.Repos.ItemRepo;
import com.example.webbshopbackend1.Repos.OrderRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class WebbShopBackend1Application {

    public static void main(String[] args) {
        SpringApplication.run(WebbShopBackend1Application.class, args);
    }

 /*   @Bean
    public CommandLineRunner bootstrap(ItemRepo itemRepo, CustomerRepo customerRepo, OrderRepo orderRepo) {
        return (args) -> {
            Item item1 = new Item("Sweatshirt", 499, 10);
            Item item2 = new Item("Tophat", 299, 20);
            Item item3 = new Item("Cool jeans", 799, 30);
            Item item4 = new Item("Pearl bracelet", 1299, 1);
            Item item5 = new Item("White t-shirt", 99, 15);
            Item item6 = new Item("Black t-shirt", 99, 16);
            Item item7 = new Item("Yellow t-shirt", 99, 17);

            itemRepo.save(item1);
            itemRepo.save(item2);
            itemRepo.save(item3);
            itemRepo.save(item4);
            itemRepo.save(item5);
            itemRepo.save(item6);
            itemRepo.save(item7);

            Customer customer1 = new Customer("Anna", "871234-5678");
            Customer customer2 = new Customer("Ling", "963852-0147");
            Customer customer3 = new Customer("Maria", "987654-3210");

            customerRepo.save(customer1);
            customerRepo.save(customer2);
            customerRepo.save(customer3);

            Orders order1 = new Orders(LocalDate.of(2023, 4, 20),
                    customer1, List.of(item1, item2));
            Orders order2 = new Orders(LocalDate.of(2023, 4, 21),
                    customer2, List.of(item3));
            Orders order3 = new Orders(LocalDate.of(2023, 4, 21),
                    customer3, List.of(item4, item5));

            orderRepo.save(order1);
            orderRepo.save(order2);
            orderRepo.save(order3);
        };
    }

  */
}
