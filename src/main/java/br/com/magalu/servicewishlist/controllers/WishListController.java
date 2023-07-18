package br.com.magalu.servicewishlist.controllers;

import br.com.magalu.servicewishlist.servicies.WishListService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/wishlist")
public class WishListController {

    private final WishListService service;
    private final WishListMapper mapper;

    @GetMapping
    public ResponseEntity<List<WishListResponse>> getWishListOfClient(@RequestParam("clientId") UUID clientId) {
        final var response = service.findAllByClientId(clientId)
                .stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/add-product")
    public ResponseEntity<WishListResponse> addProductInWishListOfCLient(
            @RequestParam("clientId") UUID clientId,
            @RequestParam("productId") UUID productId) {
        final var getProduct = service.addProductInWishListOfClient(clientId, productId);
        final var response = mapper.toResponse(getProduct);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete-product")
    public ResponseEntity<String> deleteProductInWishListOfClient(
            @RequestParam("clientId") UUID clientId,
            @RequestParam("productId") UUID productId) {
        service.deletProductByClientInWishList(clientId, productId);
        return ResponseEntity.ok("Produto removido da wishlist com sucesso");
    }

    @PostMapping("/get-product")
    public ResponseEntity<WishListResponse> getProductByClient(
            @RequestParam("clientId") UUID clientId,
            @RequestBody String productName) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String product = objectMapper.readTree(productName).get("productName").asText();
        final var products = service.searchProductByClientInWishList(clientId, product);
        final var response = mapper.toResponse(products);
        return ResponseEntity.ok(response);
    }
}
