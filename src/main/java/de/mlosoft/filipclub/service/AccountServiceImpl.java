package de.mlosoft.filipclub.service;

import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;

import de.mlosoft.filipclub.entity.AccountEntity;
import de.mlosoft.filipclub.error.ErrorCode;
import de.mlosoft.filipclub.error.ErrorInfo;
import de.mlosoft.filipclub.error.FilipClubException;
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

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    PasswordEncoder encoder;

    @Override
    public List<Account> getAllAccounts() {
        List<AccountEntity> response = accountRepository.getAllAccounts();

        if (response.isEmpty()) {
            // user not found
            ErrorInfo info = new ErrorInfo(ErrorCode.USER_NOT_FOUND.name());
            throw new FilipClubException(info);
        }

        List<Account> accounts = DozerHelper.map(mapper, response, Account.class);
        LOG.debug("AccountService getAllAccounts: {}", accounts.toString());
        return accounts;
    }

    @Override
    public Account getAccountById(long accountId) {
        List<AccountEntity> result = accountRepository.findAccountById(accountId);

        if (result.size() == 1) {
            return mapper.map(result.get(0), Account.class);
        }
        ErrorInfo info = new ErrorInfo(ErrorCode.UNKNOWN_ERROR.name());
        info.setAdditionalInfo("found more then one user for accountId:", String.valueOf(accountId));
        throw new FilipClubException(info);
    }

    public Account getAccountByEmail(String email) {
        List<AccountEntity> response = accountRepository.findAccountByEmail(email);

        if (response.size() == 1) {
            return mapper.map(response.get(0), Account.class);
        }
        ErrorInfo info = new ErrorInfo(ErrorCode.UNKNOWN_ERROR.name());
        info.setAdditionalInfo("found more then one user for email:", email);
        throw new FilipClubException(info);
    }

    @Override
    public Account createAccount(Account account) {

        // check if user already exists
        try {
            accountRepository.findAccountByEmail(account.getEmail());
        } catch (FilipClubException f) {
            if (f.getErrorInfo().getErrorCode().equals(ErrorCode.USER_NOT_FOUND.name())) {
                // user not exists we can proceed
                AccountEntity entityRequest = mapper.map(account, AccountEntity.class);
                entityRequest.setHashedPassword(encoder.encode(account.getPassword()));
                AccountEntity entityResponse = accountRepository.createAccount(entityRequest);
                return mapper.map(entityResponse, Account.class);
            }
        }
        // if user found throw exeption - user already exists
        LOG.warn("AccountRepository createAccount user already exists for email: {}", account.getEmail());
        ErrorInfo info = new ErrorInfo(ErrorCode.USER_ALREADY_EXISTS.name());
        info.setAdditionalInfo(ErrorCode.USER_ALREADY_EXISTS.name(), account.getEmail());
        throw new FilipClubException(info);
    }

    @Override
    public Account updateAccount(Account account, long accountId) {
        AccountEntity accountEntity = mapper.map(account, AccountEntity.class);

        // if password provided update hashed password
        if (!"".equals(account.getPassword()) && account.getPassword() != null) {
            LOG.info("update password for accountId: {}", accountId);
            accountEntity.setHashedPassword(encoder.encode(account.getPassword()));
        }

        AccountEntity accountEntityResponse = accountRepository.updateAccount(accountEntity, accountId);
        return mapper.map(accountEntityResponse, Account.class);
    }

    @Override
    public void deleteAccount(long accountId) {
        accountRepository.deleteAccount(accountId);
    }

    @Override
    public void updatePassword(String newPassword, String oldPassword, String email) throws Exception {

        // verify old password
        this.verifyPassword(email, oldPassword);

        // find account and update password
        List<AccountEntity> response = accountRepository.findAccountByEmail(email);

        if (response.size() != 1) {
            ErrorInfo info = new ErrorInfo(ErrorCode.UNKNOWN_ERROR.name());
            info.setAdditionalInfo("found more then one user for email:", email);
            throw new FilipClubException(info);
        }
        AccountEntity accountEntity = response.get(0);
        accountEntity.setHashedPassword(encoder.encode(newPassword));
        accountRepository.updateAccount(response.get(0), accountEntity.getAccountId());
    }

    private void verifyPassword(String username, String password) throws Exception {
        Objects.requireNonNull(username);
        Objects.requireNonNull(password);

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }

}
