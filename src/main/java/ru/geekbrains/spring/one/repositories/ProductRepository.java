package ru.geekbrains.spring.one.repositories;

import org.springframework.data.domain.Page;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;
import ru.geekbrains.spring.one.model.Product;


@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Page<Product> findAllByPriceLessThanEqualAndPriceGreaterThanEqual(int min, int max, Pageable pagenation);

    Product findDistinctFirstByOrderByPriceDesc();

}
