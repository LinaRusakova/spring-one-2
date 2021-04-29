package ru.geekbrains.spring.one.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.geekbrains.spring.one.model.Category;
import ru.geekbrains.spring.one.model.Product;
import ru.geekbrains.spring.one.repositories.CategoryRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    private CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public void deleteById(Long id) {
        categoryRepository.deleteById(id);
    }

//    @SneakyThrows
//    public List<Product> findFilteredProducts(String category) {
//        return null;// productRepository.findFilteredProducts(category);
//    }
}
