/*
package com.example.webbshopbackend1.Controllers;

import com.example.webbshopbackend1.Models.Customer;
import com.example.webbshopbackend1.Models.Item;
import com.example.webbshopbackend1.Models.Orders;
import com.example.webbshopbackend1.Repos.CustomerRepo;
import com.example.webbshopbackend1.Repos.ItemRepo;
import com.example.webbshopbackend1.Repos.OrderRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
public class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderRepo mockOrderRepo;
    @MockBean
    private CustomerRepo mockCusRepo;
    @MockBean
    private ItemRepo mockItemRepo;

    @BeforeEach
    public void init(){
        Customer customer1 = new Customer(1L,"Amy","546789");
        Customer customer2 = new Customer(2L,"Joe","789654");
        Customer customer3 = new Customer(3L,"Sara","523698");
        Item item1 = new Item(1L, "White T-shirt", 399,10);
        Item item2 = new Item(2L, "Red T-shirt", 399,10);
        Item item3 = new Item(3L, "Yellow T-shirt", 299,10);
        Item item4 = new Item(4L, "Green T-shirt", 299,10);
        Item item5 = new Item(5L, "Blue T-shirt", 299,0);
        Orders order1 = new Orders(1L, LocalDate.of(2023,04,26),customer1, List.of(item1,item2));
        Orders order2 = new Orders(2L, LocalDate.of(2023,04,26),customer2, List.of(item3,item4));
        Orders order3 = new Orders(3L, LocalDate.of(2023,04,26),customer3, List.of(item1));

        when(mockOrderRepo.findById(1L)).thenReturn(Optional.of(order1));
        when(mockOrderRepo.findById(2L)).thenReturn(Optional.of(order2));
        when(mockOrderRepo.findById(3L)).thenReturn(Optional.of(order3));
        when(mockOrderRepo.findAll()).thenReturn(Arrays.asList(order1,order2,order3));

        when(mockCusRepo.findById(1L)).thenReturn(Optional.of(customer1));
        when(mockCusRepo.findById(2L)).thenReturn(Optional.of(customer2));
        when(mockCusRepo.findById(3L)).thenReturn(Optional.of(customer3));
        when(mockCusRepo.findAll()).thenReturn(Arrays.asList(customer1,customer2,customer3));

        when(mockItemRepo.findById(1L)).thenReturn(Optional.of(item1));
        when(mockItemRepo.findById(2L)).thenReturn(Optional.of(item2));
        when(mockItemRepo.findById(3L)).thenReturn(Optional.of(item3));
        when(mockItemRepo.findById(4L)).thenReturn(Optional.of(item4));
        when(mockItemRepo.findAll()).thenReturn(Arrays.asList(item1,item2,item3,item4));

    }

    @Test
    void getAllOrders() throws Exception {

        this.mockMvc.perform(get("/orders/getAll"))
                .andExpect(status().isOk())
                .andExpect(content().json("[{\"id\":1,\"date\":\"2023-04-26\"," +
                        "\"customer\":{\"id\":1,\"name\":\"Amy\",\"socialSecurityNumber\":\"546789\"}," +
                        "\"items\":[{\"id\":1,\"name\":\"White T-shirt\",\"price\":399,\"stock\":10}," +
                        "{\"id\":2,\"name\":\"Red T-shirt\",\"price\":399,\"stock\":10}]}," +
                        "{\"id\":2,\"date\":\"2023-04-26\"," +
                        "\"customer\":{\"id\":2,\"name\":\"Joe\",\"socialSecurityNumber\":\"789654\"}," +
                        "\"items\":[{\"id\":3,\"name\":\"Yellow T-shirt\",\"price\":299,\"stock\":10}," +
                        "{\"id\":4,\"name\":\"Green T-shirt\",\"price\":299,\"stock\":10}]}," +
                        "{\"id\":3,\"date\":\"2023-04-26\"," +
                        "\"customer\":{\"id\":3,\"name\":\"Sara\",\"socialSecurityNumber\":\"523698\"}," +
                        "\"items\":[{\"id\":1,\"name\":\"White T-shirt\",\"price\":399,\"stock\":10}]}]"
                ));

        this.mockMvc.perform(get("/orders/getAll")).andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[1].customer.name", equalTo("Joe")))
                .andExpect(jsonPath("$[2].items", hasSize(1)));

    }

    @Test
    void getOrdersByCustomerId() throws Exception {

        this.mockMvc.perform(get("/orders/getByCustomerId/1"))
                .andExpect(status().isOk())
                .andExpect(content().json("[{\"id\":1,\"date\":\"2023-04-26\"," +
                        "\"customer\":{\"id\":1,\"name\":\"Amy\",\"socialSecurityNumber\":\"546789\"}," +
                        "\"items\":[{\"id\":1,\"name\":\"White T-shirt\",\"price\":399,\"stock\":10}," +
                        "{\"id\":2,\"name\":\"Red T-shirt\",\"price\":399,\"stock\":10}]}]"
                ));

    }

    @Test
    void addOrder() throws Exception {

        this.mockMvc.perform(post("/orders/buy?customerId=2&itemIds=3&itemIds=2"))
                .andExpect(status().isOk())
                .andExpect(content().string("\t\tOrder added"));

        this.mockMvc.perform(post("/orders/buy?customerId=4&itemIds=3&itemIds=2"))
                .andExpect(status().isOk())
                .andExpect(content().string("\t\tOrder failed, customer id not found"));

        this.mockMvc.perform(post("/orders/buy?customerId=1&itemIds=5"))
                .andExpect(status().isOk())
                .andExpect(content().string("\t\tOrder failed, item id /ids not found"));

        this.mockMvc.perform(post("/orders/buy?customerId=3&itemIds=3&itemIds=6"))
                .andExpect(status().isOk())
                .andExpect(content().string("\t\tOrder failed, item id /ids not found"));

    }


}
*/
