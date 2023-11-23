package de.mlosoft.filipclub.controller;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import de.mlosoft.filipclub.model.Product;
import de.mlosoft.filipclub.model.ProductList;
import de.mlosoft.filipclub.service.ProductService;
import de.mlosoft.filipclub.util.LogUtil;

@RestController
@RequestMapping("/api/product")
@CrossOrigin()
public class ProductController {

    private static final Logger LOG = LogUtil.getLogger();

    @Autowired(required = true)
    ProductService productService;

    @GetMapping("/products")
    @JsonSerialize
    @ResponseBody
    public ProductList getAllProducts() {
        LOG.debug("ProductController - getAllProducts");
        return new ProductList(productService.getAllProducts());
    }

    @GetMapping("/products/{productId}")
    @JsonSerialize
    @ResponseBody
    public Product getProductById(@PathVariable("productId") long productId) {
        LOG.debug("ProductController - getProductById accluntId: {}", productId);
        return productService.getProductById(productId);
    }

    @PostMapping("/products")
    @JsonSerialize
    @ResponseBody
    public Product createProduct(@RequestBody Product product) {
        LOG.debug("ProductController - createProduct Product: {}", product);
        return productService.createProduct(product);
    }

    @PutMapping("/products/{productId}")
    @JsonSerialize
    @ResponseBody
    public Product updateProduct(@RequestBody Product product, @PathVariable("productId") long productId) {
        LOG.debug("ProductController - updateProduct productId: {} Product: {}", productId, product);
        return productService.updateProduct(product, productId);
    }

    @DeleteMapping("/products/{productId}")
    @JsonSerialize
    public void deleteProduct(@PathVariable("productId") long productId) {
        LOG.debug("ProductController - deleteProduct productId: {}", productId);
        productService.deleteProduct(productId);
    }

}
