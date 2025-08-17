package org.dimchik.repository;

import org.dimchik.entity.Product;

import java.util.List;

public interface ProductRepository {
    List<Product> findAll();


    Product findById(Long id);
    Product create(Product product);
    Product update(Product product);
    void delete(long id);
}
