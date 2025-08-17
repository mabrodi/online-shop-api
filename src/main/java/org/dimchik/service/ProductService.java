package org.dimchik.service;

import org.dimchik.entity.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    List<Product> findAll();

    public Product findById(Long id);

    public Product create(Product product);

    public Product update(Product product);

    public void delete(long id);
}
