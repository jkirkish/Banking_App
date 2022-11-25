package com.coderscampus.Assignment_13.service;

import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coderscampus.Assignment_13.domain.Account;
import com.coderscampus.Assignment_13.domain.User;
import com.coderscampus.Assignment_13.repository.AccountRepository;
@Service
public class AccountService {
	
	@Autowired
	private AccountRepository accountRepo;
	
	public Long addAccount(User user) {
		Account account = new Account();
		Integer accountNum = user.getAccounts().size() + 1;
		account.setAccountName("Account #" + accountNum);
		account.getUsers().add(user);
		user.getAccounts().add(account);
		accountRepo.save(account);
		return account.getAccountId();
	}

	

	public Stream<Account> findAccountById(Long accountId, User user) {
		Stream<Account> account = user.getAccounts().stream().filter(x -> x.getAccountId().equals(accountId));
		return account;
	}

	public User saveAccount(Account account, User user) {
		for(Account acc: user.getAccounts()) {
			if(acc.getAccountId().equals(account.getAccountId())) {
				acc.setAccountName(account.getAccountName());
			}
		}
		return user;
	}



	public Account findById(Long accountId) {
		Optional<Account> accountOpt = accountRepo.findById(accountId);
		return accountOpt.orElse(new Account());
	}
	

}