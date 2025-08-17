package org.dimchik.repository.jooq;

import org.dimchik.entity.Product;
import org.dimchik.repository.ProductRepository;
import org.dimchik.repository.mapper.ProductRowMapper;
import org.jooq.DSLContext;
import org.jooq.generated.tables.records.ProductsRecord;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.jooq.generated.tables.Products.PRODUCTS;
import static org.dimchik.repository.mapper.ProductRowMapper.fromProductsRecord;

@Repository
public class ProductRepositoryJooq implements ProductRepository {
    private final DSLContext dsl;

    public ProductRepositoryJooq(DSLContext dsl) {
        this.dsl = dsl;
    }

    @Override
    public List<Product> findAll() {
        return dsl.selectFrom(PRODUCTS).fetch(ProductRowMapper::fromRecord);
    }

    @Override
    public Optional<Product> findById(Long id) {
        return dsl.selectFrom(PRODUCTS)
                .where(PRODUCTS.ID.eq(id)).fetchOptional(ProductRowMapper::fromRecord);
    }

    @Override
    public Product create(Product product) {
        ProductsRecord record = dsl.insertInto(PRODUCTS)
                .set(PRODUCTS.NAME, product.getName())
                .set(PRODUCTS.PRICE, product.getPrice())
                .set(PRODUCTS.DESCRIPTION, product.getDescription())
                .set(PRODUCTS.CREATED_AT, LocalDateTime.now())
                .set(PRODUCTS.UPDATED_AT, LocalDateTime.now())
                .returning()
                .fetchOne();

        if (record == null) {
            throw new IllegalStateException("Insert failed");
        }

        return fromProductsRecord(record);
    }

    @Override
    public Product update(Product product) {
        ProductsRecord productsRecord = dsl.update(PRODUCTS)
                .set(PRODUCTS.NAME, product.getName())
                .set(PRODUCTS.PRICE, product.getPrice())
                .set(PRODUCTS.DESCRIPTION, product.getDescription())
                .set(PRODUCTS.UPDATED_AT, LocalDateTime.now())
                .where(PRODUCTS.ID.eq(product.getId()))
                .returning(
                        PRODUCTS.ID, PRODUCTS.NAME, PRODUCTS.PRICE,
                        PRODUCTS.DESCRIPTION, PRODUCTS.CREATED_AT,
                        PRODUCTS.UPDATED_AT
                )
                .fetchOne();

        if (productsRecord == null) {
            throw new IllegalStateException("Updating failed");
        }

        return fromProductsRecord(productsRecord);
    }

    @Override
    public void delete(long id) {
        int row = dsl.delete(PRODUCTS).where(PRODUCTS.ID.eq(id)).execute();

        if (row == 0) {
            throw new IllegalArgumentException("Product with id " + id + " not found");
        }
    }
}
