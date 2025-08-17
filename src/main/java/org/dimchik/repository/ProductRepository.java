package org.dimchik.repository;

import org.dimchik.entity.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {
    List<Product> findAll();


    Optional<Product> findById(Long id);
    Product create(Product product);
    Product update(Product product);
    void delete(long id);
}
