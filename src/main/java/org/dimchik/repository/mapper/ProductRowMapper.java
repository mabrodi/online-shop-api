package org.dimchik.repository.mapper;

import org.dimchik.entity.Product;
import org.jooq.Record;
import org.jooq.generated.tables.records.ProductsRecord;

public class ProductRowMapper {
    public static Product fromRecord(ProductsRecord productsRecord) {
        Product product = new Product();
        product.setId(productsRecord.getId());
        product.setName(productsRecord.getName());
        product.setPrice(productsRecord.getPrice());
        product.setDescription(productsRecord.getDescription());
        product.setCreatedAt(productsRecord.getCreatedAt());
        product.setUpdatedAt(productsRecord.getUpdatedAt());

        return product;
    }

    public static Product fromProductsRecord(Record record) {
        Product p = new Product();
        p.setId(record.getValue("id", Long.class));
        p.setName(record.getValue("name", String.class));
        p.setPrice(record.getValue("price", Double.class));
        p.setDescription(record.getValue("description", String.class));
        p.setCreatedAt(record.getValue("created_at", java.time.LocalDateTime.class));
        p.setUpdatedAt(record.getValue("updated_at", java.time.LocalDateTime.class));
        return p;
    }
}
