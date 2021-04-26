package ru.geekbrains.spring.one.repositories;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.geekbrains.spring.one.model.Product;
import ru.geekbrains.spring.one.utils.HibernateUtils;

import java.util.List;
import java.util.Optional;

@Component
public class ProductRepository {
    private HibernateUtils hibernateUtils;

    @Autowired
    public ProductRepository(HibernateUtils hibernateUtils) {
        this.hibernateUtils = hibernateUtils;
    }

    public List<Product> findAll() {
        try (Session session = hibernateUtils.getCurrentSession()) {
            session.beginTransaction();
            List<Product> products = session.createQuery("from Product").getResultList();
            session.getTransaction().commit();
            return products;
        }
    }

    public void save(Product product) {
        try (Session session = hibernateUtils.getCurrentSession()) {
            session.beginTransaction();
            session.saveOrUpdate(product);
            session.getTransaction().commit();
        }
    }

    public Optional<Product> findOneById(Long id) {
        try (Session session = hibernateUtils.getCurrentSession()) {
            session.beginTransaction();
            Optional<Product> product = Optional.ofNullable(session.get(Product.class, id));
            session.getTransaction().commit();
            return product;
        }
    }


    public void deleteById(Long id) {
        try (Session session = hibernateUtils.getCurrentSession()) {
            session.beginTransaction();
            session.createQuery("delete from Product p where p.id = " + id).executeUpdate();
            session.getTransaction().commit();
        }
    }

    public List<Product> findFilteredProducts(String category) {
        try (Session session = hibernateUtils.getCurrentSession()) {
            String filter=category;
            session.beginTransaction();
            List<Product> products = session.createQuery("select p from Product p where p.category.title ='" + filter+"'").getResultList();
            session.getTransaction().commit();
            return products;
        }
    }
}
