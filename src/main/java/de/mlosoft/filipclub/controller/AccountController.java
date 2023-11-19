package de.mlosoft.filipclub.controller;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import de.mlosoft.filipclub.model.Account;
import de.mlosoft.filipclub.model.AccountList;
import de.mlosoft.filipclub.service.AccountService;
import de.mlosoft.filipclub.util.LogUtil;

@RestController
@RequestMapping("/api")
@CrossOrigin()
public class AccountController {

    private static final Logger LOG = LogUtil.getLogger();

    @Autowired(required = true)
    AccountService accountService;

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
        return accountService.getAccountById(accountId);
    }
}
