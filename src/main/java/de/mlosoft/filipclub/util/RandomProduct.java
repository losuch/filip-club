package de.mlosoft.filipclub.util;

import com.github.javafaker.Faker;

import de.mlosoft.filipclub.model.Product;

public class RandomProduct {
    private Faker faker;

    public RandomProduct() {
        super();
        this.faker = new Faker();
    }

    public Product createRandomProduct() {
        Product product = new Product();

        product.setName(faker.funnyName().name());
        product.setDescription(faker.pokemon().name());
        product.setImagesUrl(faker.file().fileName());
        product.setActiv(0);
        return product;
    }
}
