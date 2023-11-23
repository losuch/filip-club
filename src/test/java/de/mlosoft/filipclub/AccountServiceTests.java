package de.mlosoft.filipclub;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import de.mlosoft.filipclub.error.FilipClubException;
import de.mlosoft.filipclub.model.Account;
import de.mlosoft.filipclub.service.AccountService;
import de.mlosoft.filipclub.util.RandomAccount;

@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
class AccountServiceTests {

	@Autowired(required = true)
	private AccountService accountService;

	private static Account accountTest;

	@Test
	@Order(1)
	void contextLoads() {
	}

	@Test
	@Order(2)
	public void testCreateAccount() {
		RandomAccount randomAccount = new RandomAccount();

		Account account = randomAccount.createRandomAccount();
		Account account2 = accountService.createAccount(account);

		assertEquals(account.getEmail(), account2.getEmail());
		accountTest = new Account(account2.getAccountId(), account2.getEmail(), account2.getRole(),
				null);
	}

	@Test
	@Order(3)
	public void testGetAccountById() {
		Account account = accountService.getAccountById(accountTest.getAccountId());
		assertEquals(account.getEmail(), accountTest.getEmail());
	}

	@Test
	@Order(4)
	public void testGetAccountByEmail() {
		Account account = accountService.getAccountByEmail(accountTest.getEmail());
		assertEquals(account.getAccountId(), accountTest.getAccountId());
	}

	@Test
	@Order(5)
	public void testListAllAccount() {
		List<Account> accounts = accountService.getAllAccounts();
		assertNotEquals(0, accounts.size());
		for (Account account : accounts) {
			if (account.getAccountId() == accountTest.getAccountId()) {
				assertEquals(account.getEmail(), accountTest.getEmail());
			}
		}
	}

	@Test
	@Order(6)
	public void testUpdateAccount() {
		String newEmail = "test@mail.com";
		accountTest.setEmail(newEmail);
		Account account = accountService.updateAccount(accountTest, accountTest.getAccountId());
		Account account2 = accountService.getAccountById(accountTest.getAccountId());
		assertEquals(account.getRole(), account2.getRole());
	}

	@Test
	@Order(7)
	public void testdeleteAccount() {
		List<Account> accounts = accountService.getAllAccounts();
		assertNotEquals(0, accounts.size());

		for (Account account : accounts) {
			accountService.deleteAccount(account.getAccountId());
		}

		assertThrows(FilipClubException.class, () -> accountService.getAllAccounts());

	}
}
