package ru.geekbrains.spring.one.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.geekbrains.spring.one.model.Filter;
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

    public Page<Product> findAll(int page, int size, Filter userFilter) {
        if (userFilter.getMaxPrice() == 0) {
            userFilter.setMaxPrice(productRepository.findDistinctFirstByOrderByPriceDesc().getPrice());
        } else if (userFilter.getMaxPrice() < 0) userFilter.setMaxPrice(0);
        Pageable pagenation = PageRequest.of(page, size);
        return productRepository.findAllByPriceLessThanEqualAndPriceGreaterThanEqualAndTitleContains(userFilter.getMaxPrice(), userFilter.getMinPrice(), userFilter.getFilter(), pagenation);
    }

    Filter oldFilter = new Filter();

    public Filter userFilter(int minPrice, int maxPrice, String filterSubstr) {
        Filter userFilter = new Filter(minPrice, maxPrice, filterSubstr);
        if (minPrice != 0 && oldFilter.getMinPrice() != minPrice) {
            userFilter.setMinPrice(minPrice);
        }
        if (maxPrice != 0 && oldFilter.getMaxPrice() != maxPrice) {
            userFilter.setMaxPrice(maxPrice);
        }
        if (filterSubstr != null && !oldFilter.getFilter().equals(filterSubstr)) {
            userFilter.setFilter(filterSubstr);
        } else {
            userFilter.setFilter(" ");
        }
        oldFilter = userFilter;
        return userFilter;
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
