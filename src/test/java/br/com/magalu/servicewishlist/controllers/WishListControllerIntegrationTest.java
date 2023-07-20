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
                .header("clientId", clientId.toString())
                .contentType(MediaType.APPLICATION_JSON));

        resultActions.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void testAddProductInWishListOfClient() throws Exception {
        final var clientId = UUID.fromString("fd4751d0-5296-4592-a454-71e35b888da5");
        final var productId = UUID.fromString("a0dd0969-23e7-4b97-b784-53b9ddb2d240");
        final var requestBody = "{\"productId\": \"" + productId + "\"}";

        mockMvc.perform(MockMvcRequestBuilders.post("/wishlist/add-product")
                        .header("clientId", clientId.toString())
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.client.id").value(clientId.toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.product.id").value(productId.toString()))
                .andReturn();
    }

    @Test
    void testDeleteProductInWishListOfClient() throws Exception {
        final var clientId = UUID.fromString("fd4751d0-5296-4592-a454-71e35b888da5");
        final var productId = UUID.fromString("a0dd0969-23e7-4b97-b784-53b9ddb2d240");
        String requestBody = "{\"productId\": \"" + productId + "\"}";

        mockMvc.perform(MockMvcRequestBuilders.delete("/wishlist/delete-product")
                        .header("clientId", clientId.toString())
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Produto removido da wishlist com sucesso"))
                .andReturn();
    }

    @Test
    void testGetProductByClient() throws Exception {
        final var clientId = UUID.fromString("fd4751d0-5296-4592-a454-71e35b888da5");
        final var productName = "{\"productName\":\"Televisão\"}";

        final var resultActions = mockMvc.perform(MockMvcRequestBuilders.post("/wishlist/get-product")
                .header("clientId", clientId.toString())
                .content(productName)
                .contentType(MediaType.APPLICATION_JSON));

        resultActions.andExpect(MockMvcResultMatchers.status().isOk());
    }
}
