package de.mlosoft.filipclub.util;

import com.github.javafaker.Faker;

import de.mlosoft.filipclub.model.Account;

public class RandomAccount {
    private Faker faker;

    public RandomAccount() {
        super();
        this.faker = new Faker();
    }

    public Account createRandomAccount() {
        Account account = new Account();

        account.setEmail(faker.name().firstName() + "." + faker.name().lastName() + "@mail.com");
        account.setPassword(faker.starTrek().location());
        account.setRole(faker.starTrek().specie());
        return account;
    }
}
