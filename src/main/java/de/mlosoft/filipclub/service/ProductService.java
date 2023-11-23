package de.mlosoft.filipclub.service;

import java.util.List;

import de.mlosoft.filipclub.model.Product;

public interface ProductService {
    public List<Product> getAllProducts();

    public Product getProductById(long productId);

    public Product createProduct(Product product);

    public Product updateProduct(Product product, long productId);

    public void deleteProduct(long productId);
}
