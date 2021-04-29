package ru.geekbrains.spring.one.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.spring.one.model.Product;
import ru.geekbrains.spring.one.repositories.CategoryRepository;
import ru.geekbrains.spring.one.repositories.ProductRepository;
import ru.geekbrains.spring.one.services.CategoryService;
import ru.geekbrains.spring.one.services.ProductService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final CategoryService categoryService;


//    @GetMapping("/test")
//    @ResponseBody
//    public Object getTestData() {
//        //return productRepository.findOneByTitle("Milk");
////        return productRepository.findAllByPriceGreaterThan(100);
//        //return productRepository.findAllByIdLessThanAndPriceGreaterThan(3L, 28);
//        return productRepository.hqlFindById(1L);
//    }

    @GetMapping("/")
    public String showAllProductsPage(
            Model model,
            @RequestParam(name = "p", defaultValue = "1") int pageIndex,
            @RequestParam(name = "min",  defaultValue = "0") int minPrice,
            @RequestParam(name = "max", defaultValue = "0") int maxPrice,
            @RequestParam(name = "filter", defaultValue = "") String filter
    ) {
        if (pageIndex < 1) {
            pageIndex = 1;
        }

        Page<Product> page = productService.findAll(pageIndex - 1, 4, minPrice, maxPrice, filter);
        model.addAttribute("page", page);

        return "index";
//        } else {
//            List<Product> products = productService.findFilteredProducts(category);
//            model.addAttribute("products", products);
//            return "index";
//        }
    }


    @GetMapping("/products/{id}/price/inc")
    public String incrementProductPrice(@PathVariable Long id) {
        productService.incrementPriceById(id, 10);
        return "redirect:/";
    }

//    @GetMapping("/")
//    public String showFilteredProductPage(@RequestParam(required = false) String category, Model model) {
//        if (category == null) {
//            Page<Product> page =productService.findAll(0, 4 );
//            model.addAttribute("page", page);
//            return "index";
//        }
//        return "index";
////        } else {
////            List<Product> products = productService.findFilteredProducts(category);
////            model.addAttribute("products", products);
////            return "index";
////        }
//    }

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
