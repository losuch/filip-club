package de.mlosoft.filipclub.persistance;

import java.util.List;

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
    public List<AccountEntity> findAccountById(long accountId) {
        LOG.debug("AccountRepository - findAccountById - accountId: {}", accountId);
        Query query = em.createQuery("SELECT a FROM AccountEntity a WHERE a.accountId=:accountId")
                .setParameter("accountId", accountId);

        @SuppressWarnings("unchecked")
        List<AccountEntity> result = (List<AccountEntity>) query.getResultList();

        return result;
    }

    // private void flushAndClear() {
    // em.flush();
    // em.clear();
    // }
}
