package de.mlosoft.filipclub.service;

import java.util.List;

import de.mlosoft.filipclub.model.Account;

public interface AccountService {
    public List<Account> getAllAccounts();

    public Account getAccountById(long accountId);
}
