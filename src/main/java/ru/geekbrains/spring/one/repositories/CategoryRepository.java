package ru.geekbrains.spring.one.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.geekbrains.spring.one.model.Category;
import ru.geekbrains.spring.one.model.Product;

import java.util.Optional;


@Repository
public interface CategoryRepository extends JpaRepository<Product, Long> {

    Optional<Category> findOneByTitle(String title);

}
