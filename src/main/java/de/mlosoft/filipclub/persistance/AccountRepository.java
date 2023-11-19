package de.mlosoft.filipclub.persistance;

import java.util.List;

import de.mlosoft.filipclub.entity.AccountEntity;

public interface AccountRepository {

    public List<AccountEntity> getAllAccounts();

    public List<AccountEntity> findAccountById(long accountId);

}
