package de.mlosoft.filipclub.controller;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import de.mlosoft.filipclub.config.JwtTokenUtil;
import de.mlosoft.filipclub.model.Account;
import de.mlosoft.filipclub.model.JwtRequest;
import de.mlosoft.filipclub.model.JwtResponse;
import de.mlosoft.filipclub.service.AccountService;

/***
 * Controller class responsible for sign-in and sign-up processes
 * 
 * @author lukasz osuch
 *
 */
@RestController
@RequestMapping("/api")
@CrossOrigin
public class AuthenticationController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private UserDetailsService jwtInMemoryUserDetailsService;

	@Autowired
	private AccountService accountService;

	@Autowired
	PasswordEncoder encoder;

	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public ResponseEntity<?> registerUser(@RequestBody Account signupRequest) throws Exception {

		return ResponseEntity.ok(accountService.createAccount(signupRequest));
	}

	/**
	 * Sign-in REST controller
	 * 
	 * @param authenticationRequest authentication request object
	 * @return authentication response
	 * @throws Exception
	 */
	@RequestMapping(value = "/signin", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {

		authenticate(authenticationRequest.getEmail(), authenticationRequest.getPassword());

		final UserDetails userDetails = jwtInMemoryUserDetailsService
				.loadUserByUsername(authenticationRequest.getEmail());

		final String token = jwtTokenUtil.generateToken(userDetails);

		return ResponseEntity.ok(new JwtResponse(token, userDetails.getUsername()));
	}

	private void authenticate(String username, String password) throws Exception {
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
