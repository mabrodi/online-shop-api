package org.dimchik.repository.jooq;

import org.dimchik.entity.Product;
import org.jooq.DSLContext;
import org.jooq.Result;
import org.jooq.SQLDialect;
import org.jooq.generated.tables.records.ProductsRecord;
import org.jooq.impl.DSL;
import org.jooq.tools.jdbc.MockConnection;
import org.jooq.tools.jdbc.MockDataProvider;
import org.jooq.tools.jdbc.MockResult;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.jooq.generated.tables.Products.PRODUCTS;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ProductRepositoryJooqTest {
    private static final DSLContext DSL_CONTEXT = DSL.using(SQLDialect.POSTGRES);

    private DSLContext dsl(MockDataProvider provider) {
        return DSL.using(new MockConnection(provider), SQLDialect.POSTGRES);
    }

    @Test
    void findAllShouldReturnListProducts() {
        MockDataProvider provider = ctx -> {
            ProductsRecord productsRecordFirst = DSL_CONTEXT.newRecord(PRODUCTS);
            productsRecordFirst.set(PRODUCTS.ID, 1L);
            productsRecordFirst.set(PRODUCTS.NAME, "name1");
            productsRecordFirst.set(PRODUCTS.PRICE, 10.0);
            productsRecordFirst.set(PRODUCTS.DESCRIPTION, "description1");
            productsRecordFirst.set(PRODUCTS.CREATED_AT, LocalDateTime.now());
            productsRecordFirst.set(PRODUCTS.UPDATED_AT, LocalDateTime.now());

            ProductsRecord productsRecordSecond = DSL_CONTEXT.newRecord(PRODUCTS);
            productsRecordSecond.set(PRODUCTS.ID, 2L);
            productsRecordSecond.set(PRODUCTS.NAME, "name2");
            productsRecordSecond.set(PRODUCTS.PRICE, 20.0);
            productsRecordSecond.set(PRODUCTS.DESCRIPTION, "description2");
            productsRecordSecond.set(PRODUCTS.CREATED_AT, LocalDateTime.now());
            productsRecordSecond.set(PRODUCTS.UPDATED_AT, LocalDateTime.now());

            Result<ProductsRecord> result = DSL_CONTEXT.newResult(PRODUCTS);
            result.add(productsRecordFirst);
            result.add(productsRecordSecond);

            return new MockResult[]{new MockResult(result.size(), result)};
        };

        ProductRepositoryJooq repository = new ProductRepositoryJooq(dsl(provider));

        List<Product> list = repository.findAll();
        assertEquals(2, list.size());
        assertEquals("name1", list.get(0).getName());
        assertEquals(20.0, list.get(1).getPrice());
    }

    @Test
    void findByIdShouldReturnProduct() {
        MockDataProvider provider = ctx -> {
            Long id = (Long) ctx.bindings()[0];
            ProductsRecord productsRecord = DSL_CONTEXT.newRecord(PRODUCTS);
            productsRecord.set(PRODUCTS.ID, id);
            productsRecord.set(PRODUCTS.NAME, "name");
            productsRecord.set(PRODUCTS.PRICE, 10.0);
            productsRecord.set(PRODUCTS.DESCRIPTION, "description");
            productsRecord.set(PRODUCTS.CREATED_AT, LocalDateTime.now());
            productsRecord.set(PRODUCTS.UPDATED_AT, LocalDateTime.now());

            Result<ProductsRecord> result = DSL_CONTEXT.newResult(PRODUCTS);
            result.add(productsRecord);

            return new MockResult[]{new MockResult(1, result)};
        };

        ProductRepositoryJooq repository = new ProductRepositoryJooq(dsl(provider));
        Product product = repository.findById(7L);

        assertEquals(7L, product.getId());
        assertEquals("name", product.getName());
        assertEquals(10.0, product.getPrice());
        assertEquals("description", product.getDescription());
    }

    @Test
    void findByIdShouldThrowWhenNotFound() {
        MockDataProvider provider = ctx -> {
            Result<?> empty = DSL_CONTEXT.newResult(PRODUCTS);
            return new MockResult[]{new MockResult(0, empty)};
        };

        ProductRepositoryJooq repository = new ProductRepositoryJooq(dsl(provider));

        assertThrows(IllegalArgumentException.class, () -> repository.findById(404L));
    }

    @Test
    void createShouldInsertAndReturn() {
        MockDataProvider provider = ctx -> {
            String name = (String) ctx.bindings()[0];
            Double price = (Double) ctx.bindings()[1];
            String description = (String) ctx.bindings()[2];

            ProductsRecord productsRecord = DSL_CONTEXT.newRecord(PRODUCTS);
            productsRecord.set(PRODUCTS.ID, 7L);
            productsRecord.set(PRODUCTS.NAME, name);
            productsRecord.set(PRODUCTS.PRICE, price);
            productsRecord.set(PRODUCTS.DESCRIPTION, description);
            productsRecord.set(PRODUCTS.CREATED_AT, LocalDateTime.now());
            productsRecord.set(PRODUCTS.UPDATED_AT, LocalDateTime.now());

            Result<ProductsRecord> result = DSL_CONTEXT.newResult(PRODUCTS);
            result.add(productsRecord);

            return new MockResult[]{new MockResult(1, result)};
        };

        ProductRepositoryJooq repository = new ProductRepositoryJooq(dsl(provider));

        Product product = new Product();
        product.setName("New");
        product.setPrice(11.5);
        product.setDescription("desc");

        Product saved = repository.create(product);

        assertEquals(7L, saved.getId());
        assertEquals("New", saved.getName());
        assertEquals(11.5, saved.getPrice());
    }

    @Test
    void updateShouldUpdateAndReturn() {
        MockDataProvider provider = ctx -> {
            String name = (String) ctx.bindings()[0];
            Double price = (Double) ctx.bindings()[1];
            String description = (String) ctx.bindings()[2];
            Long id = (Long) ctx.bindings()[4];

            ProductsRecord productsRecord = DSL_CONTEXT.newRecord(PRODUCTS);
            productsRecord.set(PRODUCTS.ID, id);
            productsRecord.set(PRODUCTS.NAME, name);
            productsRecord.set(PRODUCTS.PRICE, price);
            productsRecord.set(PRODUCTS.DESCRIPTION, description);
            productsRecord.set(PRODUCTS.CREATED_AT, LocalDateTime.now().minusDays(1));
            productsRecord.set(PRODUCTS.UPDATED_AT, LocalDateTime.now());

            Result<ProductsRecord> result = DSL_CONTEXT.newResult(PRODUCTS);
            result.add(productsRecord);

            return new MockResult[]{new MockResult(1, result)};
        };

        ProductRepositoryJooq repository = new ProductRepositoryJooq(dsl(provider));

        Product product = new Product();
        product.setId(7L);
        product.setName("New");
        product.setPrice(11.5);
        product.setDescription("desc");

        Product saved = repository.update(product);

        assertEquals(7L, saved.getId());
        assertEquals("New", saved.getName());
        assertEquals(11.5, saved.getPrice());
        assertEquals("desc", saved.getDescription());
    }

    @Test
    void deleteShouldDelete() {
        MockDataProvider provider = ctx -> new MockResult[]{new MockResult(1)};
        ProductRepositoryJooq repository = new ProductRepositoryJooq(dsl(provider));
        assertDoesNotThrow(() -> repository.delete(10L));
    }

    @Test
    void deleteShouldThrowWhenNothingDeleted() {
        MockDataProvider provider = ctx -> new MockResult[]{new MockResult(0)};
        ProductRepositoryJooq repository = new ProductRepositoryJooq(dsl(provider));
        assertThrows(IllegalArgumentException.class, () -> repository.delete(999L));
    }
}