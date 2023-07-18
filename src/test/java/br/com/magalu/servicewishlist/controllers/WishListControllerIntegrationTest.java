package br.com.magalu.servicewishlist.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.UUID;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class WishListControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
    }

    @Test
    void testGetWishListOfClient() throws Exception {
        final var clientId = UUID.fromString("fd4751d0-5296-4592-a454-71e35b888da5");

        final var resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/wishlist")
                .param("clientId", clientId.toString())
                .contentType(MediaType.APPLICATION_JSON));

        resultActions.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void testAddProductInWishListOfClient() throws Exception {
        final var clientId = UUID.fromString("fd4751d0-5296-4592-a454-71e35b888da5");
        final var productId = UUID.fromString("a0dd0969-23e7-4b97-b784-53b9ddb2d240");

        final var resultActions = mockMvc.perform(MockMvcRequestBuilders.post("/wishlist/add-product")
                .param("clientId", clientId.toString())
                .param("productId", productId.toString())
                .contentType(MediaType.APPLICATION_JSON));

        resultActions.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void testDeleteProductInWishListOfClient() throws Exception {
        final var clientId = UUID.fromString("fd4751d0-5296-4592-a454-71e35b888da5");
        final var productId = UUID.fromString("a0dd0969-23e7-4b97-b784-53b9ddb2d240");

        final var resultActions = mockMvc.perform(MockMvcRequestBuilders.delete("/wishlist/delete-product")
                .param("clientId", clientId.toString())
                .param("productId", productId.toString())
                .contentType(MediaType.APPLICATION_JSON));

        resultActions.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void testGetProductByClient() throws Exception {
        final var clientId = UUID.fromString("fd4751d0-5296-4592-a454-71e35b888da5");
        final var productName = "{\"productName\":\"Televisão\"}";

        final var resultActions = mockMvc.perform(MockMvcRequestBuilders.post("/wishlist/get-product")
                .param("clientId", clientId.toString())
                .content(productName)
                .contentType(MediaType.APPLICATION_JSON));

        resultActions.andExpect(MockMvcResultMatchers.status().isOk());
    }
}
