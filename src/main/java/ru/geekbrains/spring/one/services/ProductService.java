package ru.geekbrains.spring.one.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.geekbrains.spring.one.model.Product;
import ru.geekbrains.spring.one.repositories.ProductRepository;
import java.util.Optional;

@Service
public class ProductService {
    private ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Page<Product> findAll(int page, int size, int minPrice, int maxPrice, String filter) {
        if (maxPrice == 0) {
            maxPrice = productRepository.findDistinctFirstByOrderByPriceDesc().getPrice();
        } else if (maxPrice < 0) maxPrice = 0;
        Pageable pagenation = PageRequest.of(page, size);
        return productRepository.findAllByPriceLessThanEqualAndPriceGreaterThanEqual(maxPrice, minPrice, pagenation);
    }

    public Optional<Product> findOneById(Long id) {
        return productRepository.findById(id);
    }

    public void save(Product product) {
        productRepository.save(product);
    }

    @Transactional
    public void incrementPriceById(Long id, int amount) {
        Product p = productRepository.findById(id).get();
        p.incrementPrice(amount);
    }


    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }

}
