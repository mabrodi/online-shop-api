package org.dimchik.service.base;

import org.dimchik.entity.Product;
import org.dimchik.repository.ProductRepository;
import org.dimchik.service.ProductService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ProductServiceBase implements ProductService {
    private final ProductRepository productRepository;

    public ProductServiceBase(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public Product findById(Long id) {
        return productRepository.findById(id);
    }

    @Override
    public Product create(Product product) {
        return productRepository.create(product);
    }

    @Override
    public Product update(Product product) {
        return productRepository.update(product);
    }

    @Override
    public void delete(long id) {
        productRepository.delete(id);
    }
}
