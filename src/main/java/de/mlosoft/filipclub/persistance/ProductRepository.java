package de.mlosoft.filipclub.persistance;

import java.util.List;

import de.mlosoft.filipclub.entity.ProductEntity;
import de.mlosoft.filipclub.entity.ProductTypeEntity;

public interface ProductRepository {

    public List<ProductEntity> getAllProducts();

    public List<ProductEntity> findProductById(long productId);

    public ProductEntity createProduct(ProductEntity product);

    public ProductEntity updateProduct(ProductEntity product, long productId);

    public ProductTypeEntity getProductTypeById(long typeId);

    public void deleteProduct(long productId);

}
