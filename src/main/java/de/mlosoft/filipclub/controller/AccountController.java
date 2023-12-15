package de.mlosoft.filipclub.controller;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import de.mlosoft.filipclub.model.Account;
import de.mlosoft.filipclub.model.AccountList;
import de.mlosoft.filipclub.service.AccountService;
import de.mlosoft.filipclub.util.LogUtil;

@Secured("ADMIN")
@RestController
@RequestMapping("/api/admin")
@CrossOrigin()
public class AccountController {

    private static final Logger LOG = LogUtil.getLogger();

    @Autowired(required = true)
    AccountService accountService;

    @Secured("ADMIN")
    @GetMapping("/accounts")
    @JsonSerialize
    @ResponseBody
    public AccountList getAllAccounts() {
        LOG.debug("AccountController - getAllAccounts");
        return new AccountList(accountService.getAllAccounts());
    }

    @GetMapping("/accounts/{accountId}")
    @JsonSerialize
    @ResponseBody
    public Account getAccountById(@PathVariable("accountId") long accountId) {
        LOG.debug("AccountController - getAccountById accluntId: {}", accountId);
        return accountService.getAccountById(accountId);
    }

    @GetMapping(path = "/accounts", params = "email")
    public Account getAccountByEmail(@RequestParam(name = "email") String email) {
        LOG.debug("AccountController - getAccountByEmail email: {}", email);
        return accountService.getAccountByEmail(email);
    }

    @PostMapping("/accounts")
    @JsonSerialize
    @ResponseBody
    public Account createAccount(@RequestBody Account account) {
        LOG.debug("AccountController - createAccount Account: {}", account);
        return accountService.createAccount(account);
    }

    @PutMapping("/accounts/{accountId}")
    @JsonSerialize
    @ResponseBody
    public Account updateAccount(@RequestBody Account account, @PathVariable("accountId") long accountId) {
        LOG.debug("AccountController - updateAccount accountId: {} Account: {}", accountId, account);
        return accountService.updateAccount(account, accountId);
    }

    @DeleteMapping("/accounts/{accountId}")
    @JsonSerialize
    public void deleteAccount(@PathVariable("accountId") long accountId) {
        LOG.debug("AccountController - deleteAccount accountId: {}", accountId);
        accountService.deleteAccount(accountId);
    }

}
