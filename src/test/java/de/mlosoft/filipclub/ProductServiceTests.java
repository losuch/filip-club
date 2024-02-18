package de.mlosoft.filipclub;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import de.mlosoft.filipclub.error.FilipClubException;
import de.mlosoft.filipclub.model.Product;
import de.mlosoft.filipclub.service.ProductService;
import de.mlosoft.filipclub.util.RandomProduct;

@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
public class ProductServiceTests {

    @Autowired(required = true)
    private ProductService productService;

    private static Product productTest;

    @Test
    @Order(1)
    void contextLoads() {
    }

    @Test
    @Order(2)
    public void testCreateProduct() {
        RandomProduct randomProduct = new RandomProduct();

        Product product = randomProduct.createRandomProduct();
        Product product2 = productService.createProduct(product);

        assertEquals(product.getName(), product2.getName());
        productTest = new Product(product2.getProductId(), product2.getName(), product2.getDescription(),
                product2.getImagesUrl(), product2.getPrice(), product2.getActive(), product2.getCreatedAt());
    }

    @Test
    @Order(3)
    public void testGetProductById() {
        Product product = productService.getProductById(productTest.getProductId());
        assertEquals(product.getName(), productTest.getName());
    }

    @Test
    @Order(4)
    public void testListAllProduct() {
        List<Product> products = productService.getAllProducts();
        assertNotEquals(0, products.size());
        for (Product product : products) {
            if (product.getProductId() == productTest.getProductId()) {
                assertEquals(product.getName(), productTest.getName());
            }
        }
    }

    @Test
    @Order(5)
    public void testUpdateProduct() {
        String newName = "TestName";
        productTest.setName(newName);
        Product product = productService.updateProduct(productTest, productTest.getProductId());
        Product product2 = productService.getProductById(productTest.getProductId());
        assertEquals(product.getDescription(), product2.getDescription());
    }

    @Test
    @Order(6)
    public void testdeleteProduct() {
        List<Product> products = productService.getAllProducts();
        assertNotEquals(0, products.size());

        for (Product product : products) {
            productService.deleteProduct(product.getProductId());
        }

        assertThrows(FilipClubException.class, () -> productService.getAllProducts());

    }

}
