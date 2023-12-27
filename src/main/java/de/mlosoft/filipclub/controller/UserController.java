package de.mlosoft.filipclub.controller;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import de.mlosoft.filipclub.model.UpdatePasswordRequest;
import de.mlosoft.filipclub.service.AccountService;
import de.mlosoft.filipclub.util.LogUtil;

@RestController
@RequestMapping("/api")
@CrossOrigin()
public class UserController {

    private static final Logger LOG = LogUtil.getLogger();

    @Autowired(required = true)
    private AccountService accountService;

    @PostMapping("/user/password")
    @JsonSerialize
    @ResponseBody
    public void updatePassword(@RequestBody UpdatePasswordRequest updatePasswordRequest,
            @AuthenticationPrincipal UserDetails userDetails) throws Exception {
        LOG.debug("updatePassword: {}, userEmail: {}", updatePasswordRequest, userDetails.getUsername());
        accountService.updatePassword(updatePasswordRequest.getNewPassword(),
                updatePasswordRequest.getOldPassword(),
                userDetails.getUsername());
    }

}
