package de.mlosoft.filipclub.persistance;

import java.util.List;

import org.antlr.v4.runtime.atn.ErrorInfo;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import de.mlosoft.filipclub.entity.AccountEntity;
import de.mlosoft.filipclub.util.LogUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

@Repository
@Qualifier("accountRepository")
public class AccountRepositoryImpl implements AccountRepository {

    private static final Logger LOG = LogUtil.getLogger();

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public List<AccountEntity> getAllAccounts() {
        LOG.debug("AccountRepository - getAllAccounts");
        Query query = em.createQuery("SELECT a FROM AccountEntity a");

        @SuppressWarnings("unchecked")
        List<AccountEntity> result = (List<AccountEntity>) query.getResultList();

        return result;
    }

    @Override
    @Transactional
    public List<AccountEntity> findAccountById(long accountId) {
        LOG.debug("AccountRepository - findAccountById - accountId: {}", accountId);
        Query query = em.createQuery("SELECT a FROM AccountEntity a WHERE a.accountId=:accountId")
                .setParameter("accountId", accountId);

        @SuppressWarnings("unchecked")
        List<AccountEntity> result = (List<AccountEntity>) query.getResultList();

        return result;
    }

    @Override
    @Transactional
    public List<AccountEntity> findAccountByEmail(String email) {
        LOG.debug("AccountRepository - findAccountByEmail - email: {}", email);
        Query query = em.createQuery("SELECT a FROM AccountEntity a WHERE a.email=:email")
                .setParameter("email", email);

        @SuppressWarnings("unchecked")
        List<AccountEntity> result = (List<AccountEntity>) query.getResultList();

        return result;
    }

    @Override
    @Transactional
    public AccountEntity createAccount(AccountEntity account) {
        LOG.debug("AccountRepository - createAccount A: {}", account.toString());
        AccountEntity accountEntity = em.merge(account);
        flushAndClear();
        return accountEntity;
    }

    @Override
    @Transactional
    public AccountEntity updateAccount(AccountEntity account, long accountId) {

        AccountEntity accountEntity;

        @SuppressWarnings("unchecked")
        List<AccountEntity> result = (List<AccountEntity>) em.createQuery(
                "SELECT a FROM AccountEntity a WHERE a.accountId = :accountId")
                .setParameter("accountId", accountId).getResultList();
        if (result.size() == 1) {

            // update
            accountEntity = result.get(0);
            accountEntity.setRole(account.getRole());

        } else {

            throw new UnsupportedOperationException("User not found");
        }
        flushAndClear();
        LOG.debug("AccountRepositoryImpl updateAccount return: {}", accountEntity.toString());
        return accountEntity;
    }

    @Override
    @Transactional
    public void deleteAccount(long accountId) {
        Query query = em.createQuery("SELECT a FROM AccountEntity a WHERE a.accountId = :accountId");
        query.setParameter("accountId", accountId);

        @SuppressWarnings("unchecked")
        List<AccountEntity> result = (List<AccountEntity>) query.getResultList();
        if (result.isEmpty()) {
            LOG.warn("User not found for accountId: {}", accountId);
            throw new UnsupportedOperationException("User not found");
        }

        AccountEntity userEntity = result.get(0);

        em.remove(userEntity);
        flushAndClear();
    }

    private void flushAndClear() {
        em.flush();
        em.clear();
    }
}
