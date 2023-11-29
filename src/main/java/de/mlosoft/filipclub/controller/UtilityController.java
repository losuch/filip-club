package de.mlosoft.filipclub.controller;

import java.security.Principal;
import java.util.Date;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import de.mlosoft.filipclub.error.ErrorCode;
import de.mlosoft.filipclub.model.HealthResponse;
import de.mlosoft.filipclub.util.LogUtil;

@RestController
@RequestMapping("/api")
@CrossOrigin()
public class UtilityController {

    private static final Logger LOG = LogUtil.getLogger();

    @Value("${filipclub.appname}")
    private String appName;

    @Value("${filipclub.apiversion}")
    private String apiVersion;

    @GetMapping("/health")
    @JsonSerialize
    @ResponseBody
    public HealthResponse healthCheck() {
        HealthResponse healthResponse = new HealthResponse(apiVersion, appName, "SUCCESS",
                new Date());

        LOG.info("UtilityController - healthCheck: {}", healthResponse.toString());
        return healthResponse;
    }

    @GetMapping("/authifo")
    @JsonSerialize
    @ResponseBody
    public String helloAuth(Authentication authentication, Principal principal) {
        LOG.info("authentication {}", authentication.toString());
        LOG.info("principal {}", principal.toString());
        return "OK";
    }
}
