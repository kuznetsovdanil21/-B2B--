package ru.course.b2b.service;

import javafx.collections.ObservableList;
import ru.course.b2b.data.ProductRepository;
import ru.course.b2b.model.Product;

public class ProductService {

    public ObservableList<Product> getAllProducts() {
        return ProductRepository.getProducts();
    }

    public void addProduct(Product product) {
        ProductRepository.addProduct(product);
    }

    public void removeProduct(Product product) {
        ProductRepository.removeProduct(product);
    }
    public void updateProduct(Product product) {
        ProductRepository.updateProduct(product);
    }

    public Product findByName(String name) {

        for (Product product : ProductRepository.getProducts()) {

            if (product.getName().equalsIgnoreCase(name)) {
                return product;
            }
        }

        return null;
    }
}