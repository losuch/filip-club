package de.mlosoft.filipclub.service;

import java.util.List;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;

import de.mlosoft.filipclub.entity.AccountEntity;
import de.mlosoft.filipclub.model.Account;
import de.mlosoft.filipclub.persistance.AccountRepository;
import de.mlosoft.filipclub.util.DozerHelper;
import de.mlosoft.filipclub.util.LogUtil;

@Service
@Qualifier("accountService")
public class AccountServiceImpl implements AccountService {

    private static final Logger LOG = LogUtil.getLogger();

    private Mapper mapper = DozerBeanMapperBuilder.buildDefault();

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public List<Account> getAllAccounts() {
        List<AccountEntity> response = accountRepository.getAllAccounts();

        if (response.isEmpty()) {

        }

        List<Account> accounts = DozerHelper.map(mapper, response, Account.class);
        return accounts;
    }

    @Override
    public Account getAccountById(long accountId) {
        List<AccountEntity> result = accountRepository.findAccountById(accountId);

        if (result.size() == 1) {
            return mapper.map(result.get(0), Account.class);
        }
        throw new UnsupportedOperationException("Unimplemented method 'getAccountById'");
    }

}
