package com.example.webbshopbackend1.Controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.nio.charset.StandardCharsets;
import java.util.List;

import com.example.webbshopbackend1.Models.Item;
import com.example.webbshopbackend1.Repos.ItemRepo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultHandler;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.Optional;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.when;
import static org.springframework.mock.http.server.reactive.MockServerHttpRequest.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.web.servlet.function.RequestPredicates.contentType;

@SpringBootTest
@AutoConfigureMockMvc
public class ItemControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private ItemRepo itemRepo;

    @BeforeEach
    void beforeTest() {
        Item item1 = new Item(1L, "Joggers", 599, 10);
        Item item2 = new Item(2L, "Black sneakers", 859, 10);
        Item item3 = new Item(3L, "Spongebob T-shirt", 150, 10);

        when(itemRepo.findById(1L)).thenReturn(Optional.of(item1));
        when(itemRepo.findById(2L)).thenReturn(Optional.of(item2));
        when(itemRepo.findById(3L)).thenReturn(Optional.of(item3));
        when(itemRepo.findAll()).thenReturn(Arrays.asList(item1, item2, item3));
    }

    @Test
    void getAllTest() throws Exception {
        this.mvc.perform(get("/items/getAll"))
                .andExpect(status().isOk())
                .andExpect(content().json("[{\"id\":1,\"name\":\"Joggers\",\"price\":599,\"stock\":10}," +
                        "{\"id\":2,\"name\":\"Black sneakers\",\"price\":859,\"stock\":10}," +
                        "{\"id\":3,\"name\":\"Spongebob T-shirt\",\"price\":150,\"stock\":10}]"));
    }

    @Test
    void getItemByIdTest() throws Exception {
        this.mvc.perform(get("/items/getById/1")).andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo("Joggers")))
                .andExpect(jsonPath("$.id", equalTo(1)))
                .andExpect(jsonPath("$.price", equalTo(599)))
                .andExpect(jsonPath("$.stock", equalTo(10)));

        this.mvc.perform(get("/items/getById/2")).andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo("Black sneakers")))
                .andExpect(jsonPath("$.id", equalTo(2)))
                .andExpect(jsonPath("$.price", equalTo(859)))
                .andExpect(jsonPath("$.stock", equalTo(10)));

        this.mvc.perform(get("/items/getById/3")).andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo("Spongebob T-shirt")))
                .andExpect(jsonPath("$.id", equalTo(3)))
                .andExpect(jsonPath("$.price", equalTo(150)))
                .andExpect(jsonPath("$.stock", equalTo(10)));
    }

    @Test
    void addTest() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                        .post("/items/add")
                        .content("{\"id\":1, \"name\":\"NicePants\", \"price\":10000,\"stock\":10}")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("\t\tSaved NicePants"));

    }
}
