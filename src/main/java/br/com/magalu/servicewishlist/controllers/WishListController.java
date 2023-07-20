package br.com.magalu.servicewishlist.controllers;

import br.com.magalu.servicewishlist.servicies.WishListService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/wishlist")
@Api(tags = "Wishlist", description = "Endpoints para gerenciamento da wishlist do cliente.")
public class WishListController {

    private final WishListService service;
    private final WishListMapper mapper;

    @GetMapping
    @ApiOperation(value = "Obter a wishlist do cliente", notes = "Retorna a lista de itens na Wishlist do cliente.")
    public ResponseEntity<List<WishListResponse>> getWishListOfClient(
            @RequestHeader(value="clientId") UUID clientId) {
        log.info("Buscando wishlist do cliente.");
        final var response = service.findAllByClientId(clientId)
                .stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
        log.info("Wishlist do cliente " + response.get(0).getClient().getName() + " listada com sucesso!");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/add-product")
    @ApiOperation(value = "Adicionar produto a wishlist do cliente", notes = "Adiciona novos produtos a Wishlist do cliente.")
    public ResponseEntity<WishListResponse> addProductInWishListOfCLient(
            @RequestHeader(value="clientId") UUID clientId,
            @RequestBody String productId) throws JsonProcessingException {
        log.info("Adicionando produto a wishlist do cliente.");
        final var objectMapper = new ObjectMapper();
        final var product = objectMapper.readTree(productId).get("productId").asText();
        final var getProduct = service.addProductInWishListOfClient(clientId, UUID.fromString(product));
        final var response = mapper.toResponse(getProduct);
        log.info("Produto "
                + response.getProduct().getName()
                + " adicionado a wishlist do cliente "
                + response.getClient().getName());
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete-product")
    @ApiOperation(value = "Deletar produto na wishlist do cliente", notes = "Deleta produto na Wishlist do cliente.")
    public ResponseEntity<String> deleteProductInWishListOfClient(
            @RequestHeader(value="clientId") UUID clientId,
            @RequestBody String productId) throws JsonProcessingException {
        log.info("Removendo produto da wishlist do cliente.");
        final var objectMapper = new ObjectMapper();
        final var product = objectMapper.readTree(productId).get("productId").asText();
        service.deletProductByClientInWishList(clientId, UUID.fromString(product));
        log.info("Produto removido da wishlist do cliente.");
        return ResponseEntity.ok("Produto removido da wishlist com sucesso");
    }

    @PostMapping("/get-product")
    @ApiOperation(value = "Buscar por produto na wishlist do cliente", notes = "Retorna produto especifico na wishlist do cliente.")
    public ResponseEntity<WishListResponse> getProductByClient(
            @RequestHeader(value="clientId") UUID clientId,
            @RequestBody String productName) throws JsonProcessingException {
        log.info("Buscando produto na wishlist do cliente.");
        final var objectMapper = new ObjectMapper();
        final var product = objectMapper.readTree(productName).get("productName").asText();
        final var products = service.searchProductByClientInWishList(clientId, product);
        final var response = mapper.toResponse(products);
        log.info("Produto encontrado na wishlist do cliente");
        return ResponseEntity.ok(response);
    }
}
