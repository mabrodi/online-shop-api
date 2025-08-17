package org.dimchik.web.controller;

import org.dimchik.entity.Product;
import org.dimchik.service.ProductService;
import org.dimchik.web.request.ProductRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductControllerTest {
    private Product product;
    private ProductRequest request;
    private ProductController productController;

    @Mock
    private ProductService productService;

    @BeforeEach
    void setUp() {
        product = new Product();
        product.setId(1L);
        product.setName("product");
        product.setPrice(100.0);
        product.setDescription("description");

        request = new ProductRequest();
        request.setName("Test Product");
        request.setPrice(100.0);
        request.setDescription("Test Description");

        productController = new ProductController(productService);
    }

    @Test
    void findAllShouldReturnListOfProducts() {
        List<Product> expectedProducts = Arrays.asList(product);
        when(productService.findAll()).thenReturn(expectedProducts);

        ResponseEntity<List<Product>> response = productController.findAll();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(expectedProducts);
        verify(productService).findAll();
    }

    @Test
    void findByIdShouldReturnProductWhenExists() {
        when(productService.findById(1L)).thenReturn(product);

        ResponseEntity<Product> response = productController.findById(1L);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(product);
        verify(productService).findById(1L);
    }

    @Test
    void createShouldCreateNewProductAndReturnCreatedStatus() {
        when(productService.create(any(Product.class))).thenReturn(product);

        ResponseEntity<Product> response = productController.create(request);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isEqualTo(product);
        verify(productService).create(any(Product.class));
    }

    @Test
    void updateShouldUpdateExistingProductAndReturnOkStatus() {
        when(productService.update(any(Product.class))).thenReturn(product);

        ResponseEntity<Product> response = productController.update(1L, request);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(product);
        verify(productService).update(any(Product.class));
    }

    @Test
    void deleteShouldDeleteProductAndReturnNoContentStatus() {
        ProductController productController = new ProductController(productService);
        productController.delete(1L);

        verify(productService).delete(1L);
    }

}