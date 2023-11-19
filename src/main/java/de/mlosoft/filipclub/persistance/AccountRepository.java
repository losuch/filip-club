package de.mlosoft.filipclub.persistance;

import java.util.List;

import de.mlosoft.filipclub.entity.AccountEntity;

public interface AccountRepository {

    public List<AccountEntity> getAllAccounts();

    public List<AccountEntity> findAccountById(long accountId);

    public List<AccountEntity> findAccountByEmail(String email);

    public AccountEntity createAccount(AccountEntity account);

    public AccountEntity updateAccount(AccountEntity account, long accountId);

    public void deleteAccount(long accountId);

}
