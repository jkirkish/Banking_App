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
	
	/* This method adds an account to the user and saves to the account table
	 * 
	 */
	public Long addAccount(User user) {
		Account account = new Account();
		Integer accountNum = user.getAccounts().size() + 1;
		account.setAccountName("Account #" + accountNum);
		account.getUsers().add(user);
		user.getAccounts().add(account);
		accountRepo.save(account);
		return account.getAccountId();
	}

	
   /*
    * This method streams the accounts filtering the account ID primary key associated with a user
    */
	public Stream<Account> findAccountById(Long accountId, User user) {
		Stream<Account> account = user.getAccounts().stream().filter(x -> x.getAccountId().equals(accountId));
		return account;
	}
   
    /*This method finds an account by Id and and uses an Optional to avoid a null pointer exception.
     * 
     */
	public Account findById(Long accountId) {
		Optional<Account> accountOpt = accountRepo.findById(accountId);
		return accountOpt.orElse(new Account());
	}
	

}