package com.coderscampus.Assignment_13.service;

import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coderscampus.Assignment_13.domain.Account;
import com.coderscampus.Assignment_13.domain.User;
import com.coderscampus.Assignment_13.repository.AccountRepository;

@Service
public class AccountService {

	@Autowired
	AccountRepository accountRepo;

	public Stream<Account> findAccountById(Long accountId, User user) {
		Stream<Account> account = user.getAccounts().stream().filter(x -> x.getAccountId().equals(accountId));
		return account;
	}

	public Long addAccount(User user) {
		Account account = new Account();
		Integer accountNum = user.getAccounts().size() + 1;
		account.setAccountName("Account " + accountNum);
		account.getUsers().add(user);
		user.getAccounts().add(account);
		accountRepo.save(account);
		System.out.println("AccountId is " + account.getAccountId());
		return account.getAccountId();
	}

	public Account saveAccount(Account account) {
		return accountRepo.save(account);
	}

}
