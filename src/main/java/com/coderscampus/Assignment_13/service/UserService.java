package com.coderscampus.Assignment_13.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coderscampus.Assignment_13.domain.Account;
import com.coderscampus.Assignment_13.domain.Address;
import com.coderscampus.Assignment_13.domain.User;
import com.coderscampus.Assignment_13.repository.AccountRepository;
import com.coderscampus.Assignment_13.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepo;
	@Autowired
	private AccountRepository accountRepo;

	public List<User> findByUsername(String username) {

		return userRepo.findByUsername(username);
	}

	public List<User> findByNameAndUsername(String name, String username) {
		return userRepo.findByNameAndUsername(name, username);
	}

	public List<User> findByCreatedDateBetween(LocalDate date1, LocalDate date2) {
		return userRepo.findByCreatedDateBetween(date1, date2);
	}

	public User findExactlyOneUserByUsername(String username) {
		List<User> users = userRepo.findExactlyOneUserByUsername(username);
		if (users.size() > 0)
			return users.get(0);
		else
			return new User();
	}

	public List<User> findAllUsers() {
		return userRepo.findAll();
	}

	public Set<User> findAll() {
		return userRepo.findAllUsersWithAccountsAndAddresses();
	}

	public User findById(Long userId) {
		Optional<User> userOpt = userRepo.findById(userId);
		return userOpt.orElse(new User());
	}

	public User saveUser(User user) {
		if (user.getUserId() == null) {
			System.out.println("This is: " + (user.getUserId() == null));
			Account checking = new Account();
			checking.setAccountName("Checking Account");
			checking.getUsers().add(user);
			Account savings = new Account();
			savings.setAccountName("Savings Account");
			savings.getUsers().add(user);

			user.getAccounts().add(checking);
			user.getAccounts().add(savings);
			accountRepo.save(checking);
			accountRepo.save(savings);
		}
		if (user.getAddress() == null) {
			Address address = new Address();
			address.setAddressLine1("");
			address.setAddressLine2("");
			address.setCity("");
			address.setCountry("");
			address.setRegion("");
			address.setZipCode("");
			address.setUser(user);
			address.setUserId((user.getUserId()));
			System.out.println("user.getUserId is: " + user.getUserId());
			user.setAddress(address);
		} else {
			Address address = user.getAddress();
			address.setUser(user);
			address.setUserId(user.getUserId());
			user.setAddress(user.getAddress());
		}
		return userRepo.save(user);
	}

	public void delete(Long userId) {
		userRepo.deleteById(userId);
	}
//	//add an account to the User's profile
//	public Long addAccount(User user) {
//		Account account = new Account();
//		Integer accountNum = user.getAccounts().size() + 1;
//		account.setAccountName("Account #" + accountNum);
//		account.getUsers().add(user);
//		user.getAccounts().add(account);
//		accountRepo.save(account);
//		System.out.println("AccountId is " + account.getAccountId());
//		return account.getAccountId();
//		
//	}
//	
//	public Stream<Account> findAccountById(Long accountId, User user) {
//		Stream<Account> account = user.getAccounts().stream().filter(x -> x.getAccountId().equals(accountId));
//		return account;
//	}

//	// update the type of account the user has.  
//	public User saveAccount(Account account, User user) {
//		
//		for(Account acc: user.getAccounts()) {
//			if(acc.getAccountId().equals(account.getAccountId())) {
//				acc.setAccountName(account.getAccountName());
//			}
//		}
//		return user;
//	}

}
