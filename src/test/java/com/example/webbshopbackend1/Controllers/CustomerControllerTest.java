/* 
package com.example.webbshopbackend1.Controllers;

import com.example.webbshopbackend1.Models.Customer;
import com.example.webbshopbackend1.Repos.CustomerRepo;
import com.example.webbshopbackend1.Repos.OrderRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Optional;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.equalTo;

@SpringBootTest
@AutoConfigureMockMvc
class CustomerControllerTest {


    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private CustomerRepo mockRepo;

    @BeforeEach
    public void init() {
        Customer c1 = new Customer(1L, "Athos", "123");
        Customer c2 = new Customer(2L, "Porthos", "456");
        Customer c3 = new Customer(3L, "Aramis", "789");



        when(mockRepo.findById(1L)).thenReturn(Optional.of(c1));
        when(mockRepo.findById(2L)).thenReturn(Optional.of(c2));
        when(mockRepo.findById(3L)).thenReturn(Optional.of(c3));
        when(mockRepo.findAll()).thenReturn(Arrays.asList(c1, c2, c3));
    }


    @Test
    void getAllCustomers() throws Exception {
        this.mockMvc.perform(get("/customers/getAll")).andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[2].socialSecurityNumber", equalTo("789")))
                .andExpect(jsonPath("$[2].name", not(equalTo("Albert"))));
    }

    @Test
    void getCustomerById() throws Exception {
        this.mockMvc.perform(get("/customers/getById/1")).andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo("Athos")))
                .andExpect(jsonPath("$.id", equalTo(1)));

        this.mockMvc.perform(get("/customers/getById/1")).andExpect(status().isOk())
                .andExpect(jsonPath("$.name", not(equalTo("Albert"))))
                .andExpect(jsonPath("$.id", not(equalTo(5))));
    }

    @Test
    void addCustomer() throws Exception {
        this.mockMvc.perform(post("/customers/add").contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":5, \"name\":\"Albert\", \"socialSecurityNumber\":\"777\"}"))
                .andExpect(status().isOk()).andExpect(content().string(equalTo("Customer Albert added to database")));
    }
}
*/
