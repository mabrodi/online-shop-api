package org.dimchik.service.base;

import org.dimchik.entity.Product;
import org.dimchik.repository.ProductRepository;
import org.dimchik.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServiceBaseTest {
    private ProductService service;

    @Mock
    ProductRepository repository;

    @BeforeEach
    void setUp() {
        service = new ProductServiceBase(repository);
    }

    @Test
    void getFindAllShouldReturnProducts() {
        List<Product> products = List.of(new Product());
        when(repository.findAll()).thenReturn(products);

        List<Product> result = service.findAll();

        assertEquals(products, result);
        verify(repository).findAll();
    }

    @Test
    void addProductShouldSave() {
        Product product = new Product();
        product.setId(1L);
        product.setName("name");
        product.setPrice(10.0);
        product.setDescription("description");

        service.create(product);

        ArgumentCaptor<Product> captor = ArgumentCaptor.forClass(Product.class);
        verify(repository).create(captor.capture());

        Product saved = captor.getValue();
        assertEquals(1L, saved.getId());
        assertEquals("name", saved.getName());
        assertEquals(10.0, saved.getPrice());
        assertEquals("description", saved.getDescription());
    }

    @Test
    void getFindByIdShouldReturnProduct() {
        Product product = new Product();
        when(repository.findById(1L)).thenReturn(product);

        Product result = service.findById(1L);

        assertEquals(product, result);
        verify(repository).findById(1L);
    }


    @Test
    void updateProductShouldValidateAndUpdate() {
        Product product = new Product();
        product.setId(1L);
        product.setName("name");
        product.setPrice(10.0);
        product.setDescription("description");

        service.update(product);

        ArgumentCaptor<Product> captor = ArgumentCaptor.forClass(Product.class);
        verify(repository).update(captor.capture());

        Product updated = captor.getValue();
        assertEquals(1L, updated.getId());
        assertEquals("name", updated.getName());
        assertEquals(10.0, updated.getPrice());
        assertEquals("description", updated.getDescription());
    }


    @Test
    void deleteProductShouldValidateAndDelete() {
        service.delete(1L);
        verify(repository).delete(1L);
    }

}