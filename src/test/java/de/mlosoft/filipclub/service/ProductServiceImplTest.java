package de.mlosoft.filipclub.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import de.mlosoft.filipclub.entity.ProductEntity;
import de.mlosoft.filipclub.model.Product;
import de.mlosoft.filipclub.persistance.ProductRepository;

public class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private PasswordEncoder encoder;

    @InjectMocks
    private ProductServiceImpl productService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCreateProduct() {
        Product product = new Product();
        product.setName("Test Product");
        product.setActive(1);
        ProductEntity productEntity = new ProductEntity();
        productEntity.setName("Test Product");
        productEntity.setActive(1);

        when(productRepository.createProduct(any(ProductEntity.class))).thenReturn(productEntity);

        Product createdProduct = productService.createProduct(product);

        assertEquals(product.getName(), createdProduct.getName());
        assertEquals(product.getActive(), createdProduct.getActive());
        verify(productRepository, times(1)).createProduct(any(ProductEntity.class));
    }

    @Test
    public void testGetProductById() {
        ProductEntity productEntity = new ProductEntity();
        productEntity.setProductId(1L);
        productEntity.setName("Test Product");

        when(productRepository.findProductById(anyLong())).thenReturn(Collections.singletonList(productEntity));

        Product product = productService.getProductById(1L);

        assertEquals(productEntity.getName(), product.getName());
        verify(productRepository, times(1)).findProductById(anyLong());
    }
}