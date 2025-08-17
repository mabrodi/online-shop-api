package org.dimchik.repository.mapper;

import org.dimchik.entity.Product;
import org.jooq.generated.tables.records.ProductsRecord;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductRowMapperTest {
    @Mock
    ProductsRecord productsRecord;

    @Test
    void mapRowShouldReturnProduct() {
        when(productsRecord.getId()).thenReturn(1L);
        when(productsRecord.getName()).thenReturn("name");
        when(productsRecord.getPrice()).thenReturn(10.0);
        when(productsRecord.getDescription()).thenReturn("description");
        when(productsRecord.getCreatedAt()).thenReturn(LocalDateTime.of(2025, 7, 10, 12, 0));
        when(productsRecord.getUpdatedAt()).thenReturn(LocalDateTime.of(2025, 7, 10, 12, 0));

        Product product = ProductRowMapper.mapRow(productsRecord);
        assertEquals(1L, product.getId());
        assertEquals("name", product.getName());
        assertEquals(10.0, product.getPrice());
        assertEquals("description", product.getDescription());
        assertEquals(LocalDateTime.of(2025, 7, 10, 12, 0), product.getCreatedAt());
        assertEquals(LocalDateTime.of(2025, 7, 10, 12, 0), product.getUpdatedAt());
    }
}