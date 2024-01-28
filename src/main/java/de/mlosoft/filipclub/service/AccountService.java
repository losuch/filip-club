package de.mlosoft.filipclub.service;

import java.util.List;

import de.mlosoft.filipclub.model.Account;

public interface AccountService {
    public List<Account> getAllAccounts();

    public Account getAccountById(long accountId);

    public Account getAccountByEmail(String email);

    public Account createAccount(Account account);

    public Account updateAccount(Account account, long accountId);

    public void deleteAccount(long accountId);

    public void updatePassword(String newPassword, String oldPassword, String email) throws Exception;
}
