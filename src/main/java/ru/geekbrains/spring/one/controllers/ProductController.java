package ru.geekbrains.spring.one.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.spring.one.model.Product;
import ru.geekbrains.spring.one.services.ProductService;

import java.util.List;
import java.util.Optional;

@Controller
public class ProductController {
    private ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/")
    public String showFilteredProductPage(@RequestParam(required = false) String category, Model model) {
        if (category==null) {
            List<Product> products = productService.findAll();
            model.addAttribute("products", products);
            return "index";
        } else {
            List<Product> products = productService.findFilteredProducts(category);
            model.addAttribute("products", products);
            return "index";
        }
    }

    @GetMapping("/products/{id}")
    public String showProductInfo(@PathVariable(name = "id") Long id, Model model) {
        productService.findOneById(id).ifPresent(p -> model.addAttribute("product", p));
        return "product_info";
    }

    @GetMapping("/products/add")
    public String showCreateProductForm() {
        return "create_product_form";
    }

    @PostMapping("/products/add")
    public String createNewProduct(@ModelAttribute Product product) {
        productService.save(product);
        return "redirect:/";
    }

    @GetMapping("/products/delete/{id}")
    public String deleteProductById(@PathVariable Long id) {
        productService.deleteById(id);
        return "redirect:/";
    }
}
