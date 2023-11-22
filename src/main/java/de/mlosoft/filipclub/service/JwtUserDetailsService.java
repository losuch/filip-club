package de.mlosoft.filipclub.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import de.mlosoft.filipclub.entity.AccountEntity;
import de.mlosoft.filipclub.persistance.AccountRepository;
import de.mlosoft.filipclub.util.LogUtil;

@Service
public class JwtUserDetailsService implements UserDetailsService {

	private static final Logger LOG = LogUtil.getLogger();

	@Autowired
	private AccountRepository accountRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		LOG.debug("loadUserByUsername for username: {}", username);

		List<AccountEntity> accountEntityList = accountRepository.findAccountByEmail(username);

		if (!accountEntityList.isEmpty()) {
			AccountEntity accountEntity = accountEntityList.get(0);

			List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>() {
				{
					add(new SimpleGrantedAuthority(accountEntity.getRole()));
				}
			};

			// GrantedAuthority authority = new
			// SimpleGrantedAuthority(accountEntity.getRole());
			UserDetails userDetails = new User(accountEntity.getEmail(), accountEntity.getHashedPassword(),
					authorities);
			LOG.debug("JwtUserDetailsServiceu loadUserByUsername: {}", userDetails.toString());
			return userDetails;

		} else {
			LOG.warn("User not found with username: {}", username);
			throw new UsernameNotFoundException("User not found with username: " + username);
		}

	}

}