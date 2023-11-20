package de.mlosoft.filipclub.persistance;

import java.util.List;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import de.mlosoft.filipclub.entity.AccountEntity;
import de.mlosoft.filipclub.error.ErrorCode;
import de.mlosoft.filipclub.error.ErrorInfo;
import de.mlosoft.filipclub.error.FilipClubException;
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
        try {
            @SuppressWarnings("unchecked")
            List<AccountEntity> result = (List<AccountEntity>) query.getResultList();
            return result;
        } catch (Exception e) {
            LOG.error("AccountRepository getAllAccounts: {}", e.getMessage());
            ErrorInfo info = new ErrorInfo(ErrorCode.DB_ERROR.name());
            info.setAdditionalInfo(ErrorCode.DB_ERROR.name(), e.getMessage());
            throw new FilipClubException(info, e);
        }
    }

    @Override
    @Transactional
    public List<AccountEntity> findAccountById(long accountId) {
        LOG.debug("AccountRepository - findAccountById - accountId: {}", accountId);
        Query query = em.createQuery("SELECT a FROM AccountEntity a WHERE a.accountId=:accountId")
                .setParameter("accountId", accountId);

        try {
            @SuppressWarnings("unchecked")
            List<AccountEntity> result = (List<AccountEntity>) query.getResultList();
            if (result.isEmpty()) {
                // no user found
                ErrorInfo info = new ErrorInfo(ErrorCode.USER_NOT_FOUND.name());
                info.setAdditionalInfo("no user fount for accountId:", String.valueOf(accountId));
                throw new FilipClubException(info);
            }
            return result;
        } catch (FilipClubException f) {
            LOG.warn("AccountRepository findAccountById {}", f.getMessage());
            throw f;
        } catch (Exception e) {
            LOG.error("AccountRepository findAccountById: {}", e.getMessage());
            ErrorInfo info = new ErrorInfo(ErrorCode.DB_ERROR.name());
            info.setAdditionalInfo(ErrorCode.DB_ERROR.name(), e.getMessage());
            throw new FilipClubException(info, e);
        }
    }

    @Override
    @Transactional
    public List<AccountEntity> findAccountByEmail(String email) {
        LOG.debug("AccountRepository - findAccountByEmail - email: {}", email);
        Query query = em.createQuery("SELECT a FROM AccountEntity a WHERE a.email=:email")
                .setParameter("email", email);

        try {
            @SuppressWarnings("unchecked")
            List<AccountEntity> result = (List<AccountEntity>) query.getResultList();
            if (result.isEmpty()) {
                // no user found
                ErrorInfo info = new ErrorInfo(ErrorCode.USER_NOT_FOUND.name());
                info.setAdditionalInfo("user not for email:", email);
                throw new FilipClubException(info);
            }
            return result;
        } catch (FilipClubException f) {
            LOG.warn("AccountRepository findAccountByEmail {}", f.getMessage());
            throw f;
        } catch (Exception e) {
            LOG.error("AccountRepository findAccountByEmail: {}", e.getMessage());
            ErrorInfo info = new ErrorInfo(ErrorCode.DB_ERROR.name());
            info.setAdditionalInfo(ErrorCode.DB_ERROR.name(), e.getMessage());
            throw new FilipClubException(info, e);
        }
    }

    @Override
    @Transactional
    public AccountEntity createAccount(AccountEntity account) {

        LOG.debug("AccountRepository - createAccount: {}", account.toString());
        try {
            AccountEntity accountEntity = em.merge(account);
            flushAndClear();
            return accountEntity;
        } catch (Exception e) {
            LOG.error("AccountRepository createAccount: {}", e.getMessage());
            ErrorInfo info = new ErrorInfo(ErrorCode.DB_ERROR.name());
            info.setAdditionalInfo(ErrorCode.DB_ERROR.name(), e.getMessage());
            throw new FilipClubException(info, e);
        }
    }

    @Override
    @Transactional
    public AccountEntity updateAccount(AccountEntity account, long accountId) {

        AccountEntity accountEntity;
        try {
            @SuppressWarnings("unchecked")
            List<AccountEntity> result = (List<AccountEntity>) em.createQuery(
                    "SELECT a FROM AccountEntity a WHERE a.accountId = :accountId")
                    .setParameter("accountId", accountId).getResultList();
            if (result.size() == 1) {

                // update role
                accountEntity = result.get(0);
                accountEntity.setRole(account.getRole());

            } else {
                // no user found
                ErrorInfo info = new ErrorInfo(ErrorCode.USER_NOT_FOUND.name());
                info.setAdditionalInfo("no user fount for accountId:", String.valueOf(accountId));
                throw new FilipClubException(info);
            }
            flushAndClear();
            LOG.debug("AccountRepositoryImpl updateAccount return: {}", accountEntity.toString());
            return accountEntity;
        } catch (FilipClubException f) {
            LOG.warn("AccountRepository updateAccou: {}", f.getMessage());
            throw f;
        } catch (Exception e) {
            LOG.error("AccountRepository updateAccount: {}", e.getMessage());
            ErrorInfo info = new ErrorInfo(ErrorCode.DB_ERROR.name());
            info.setAdditionalInfo(ErrorCode.DB_ERROR.name(), e.getMessage());
            throw new FilipClubException(info, e);
        }
    }

    @Override
    @Transactional
    public void deleteAccount(long accountId) {
        Query query = em.createQuery("SELECT a FROM AccountEntity a WHERE a.accountId = :accountId");
        query.setParameter("accountId", accountId);

        try {
            @SuppressWarnings("unchecked")
            List<AccountEntity> result = (List<AccountEntity>) query.getResultList();
            if (result.isEmpty()) {
                // no user found
                ErrorInfo info = new ErrorInfo(ErrorCode.USER_NOT_FOUND.name());
                info.setAdditionalInfo("no user fount for accountId:", String.valueOf(accountId));
                throw new FilipClubException(info);
            }
            AccountEntity userEntity = result.get(0);

            em.remove(userEntity);
            flushAndClear();
        } catch (FilipClubException f) {
            LOG.warn("AccountRepository deleteAccount: {}", f.getMessage());
            throw f;
        } catch (Exception e) {
            LOG.error("AccountRepository deleteAccount: {}", e.getMessage());
            ErrorInfo info = new ErrorInfo(ErrorCode.DB_ERROR.name());
            info.setAdditionalInfo(ErrorCode.DB_ERROR.name(), e.getMessage());
            throw new FilipClubException(info, e);
        }
    }

    private void flushAndClear() {
        em.flush();
        em.clear();
    }
}
