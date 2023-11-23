package de.mlosoft.filipclub.persistance;

import java.util.List;

import de.mlosoft.filipclub.entity.ProductEntity;

public interface ProductRepository {

    public List<ProductEntity> getAllProducts();

    public List<ProductEntity> findProductById(long productId);

    public ProductEntity createProduct(ProductEntity product);

    public ProductEntity updateProduct(ProductEntity product, long productId);

    public void deleteProduct(long productId);

}
