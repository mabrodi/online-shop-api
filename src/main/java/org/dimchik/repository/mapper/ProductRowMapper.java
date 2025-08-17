package org.dimchik.repository.mapper;

import org.dimchik.entity.Product;
import org.jooq.generated.tables.records.ProductsRecord;

public class ProductRowMapper {
    public static Product mapRow(ProductsRecord productsRecord) {
        Product product = new Product();
        product.setId(productsRecord.getId());
        product.setName(productsRecord.getName());
        product.setPrice(productsRecord.getPrice());
        product.setDescription(productsRecord.getDescription());
        product.setCreatedAt(productsRecord.getCreatedAt());
        product.setUpdatedAt(productsRecord.getUpdatedAt());

        return product;
    }
}
