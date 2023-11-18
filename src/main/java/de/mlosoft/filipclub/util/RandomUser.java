package de.mlosoft.filipclub.util;

import com.github.javafaker.Faker;

import de.mlosoft.filipclub.model.Account;

public class RandomUser {
    private Faker faker;

    public RandomUser() {
        super();
        this.faker = new Faker();
    }

    public Account createRandomUser() {
        Account user = new Account();

        user.setEmail(faker.random().toString());
        user.setPassword(faker.random().toString());
        return user;
    }
}
