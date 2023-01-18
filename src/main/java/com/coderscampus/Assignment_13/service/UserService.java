package com.coderscampus.Assignment_13.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

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
	private UserRepository userRepo;//instance variable for of type userRepository
	@Autowired
	private AccountRepository accountRepo;//instance variable of type AccountRepository
	
	//provides a list of users by their usernames in the database
	public List<User> findByUsername(String username) {
	
		return userRepo.findByUsername(username);
	}
	//provides a list of Users that match a name and username.
	public List<User> findByNameAndUsername(String name, String username) {
		return userRepo.findByNameAndUsername(name, username);
	}
	//provides a list of users that are between two dates given in localDate
	public List<User> findByCreatedDateBetween(LocalDate date1, LocalDate date2) {
		return userRepo.findByCreatedDateBetween(date1, date2);
	}
	//Provides a list of Users that have an exact match of one username in the database.
	//the zero index in the list is what is returned. Otherwise a new user is given.  
	public User findExactlyOneUserByUsername(String username) {
		List<User> users = userRepo.findExactlyOneUserByUsername(username);
		if (users.size() > 0)
			return users.get(0);
		else
			return new User();
	}
	//returns a set of users that have accounts and Addresses
	public Set<User> findAll () {
		return userRepo.findAllUsersWithAccountsAndAddresses();
	}
	//returns a User that matches the specified userId.  Or an optional new User to avoid null exceptions
	public User findById(Long userId) {
		Optional<User> userOpt = userRepo.findById(userId);
		return userOpt.orElse(new User());
	}
    /*This method saves and return a User with a checking and Savings Account and address information to go 
     * along with their user Id.  if The user Id has to be null and address have to be null, default values are added
     * Also a checking and savings account comes with each new user.  Otherwise the current addressed values are attached 
     * to the user.  
     * 
     */
	public User saveUser(User user) {
		if (user.getUserId() == null) {
			System.out.println("This is: " + (user.getUserId() == null));
			Account checking = new Account();
			checking.setAccountName("Checking Account");
			checking.getUsers().add(user);
			Account savings = new Account();
			savings.setAccountName("Savings Account");
			savings.getUsers().add(user);
			/*the user object has getAccount() method add the checking and the savings account to the 
			 * List of Accounts in the user object. Then the accountRepo does crud operation to save 
			 * the account to the user object.  Users and accounts has a many to many relationship defined 
			 * in the user object.  
			 * 
			 */
			user.getAccounts().add(checking);
			user.getAccounts().add(savings);
			accountRepo.save(checking);
			accountRepo.save(savings);
		}
		if(user.getAddress()== null) {
		/*if the address assigned to a user is null. Then default values are given which can 
		 * later be updated in their respective column names. The address table has a one to one
		 * relationship defined in the user object. 
		 * 	
		 */
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
    /*
     * delete a single user identified by the primary key the user id
     */
	public void delete(Long userId) {
		userRepo.deleteById(userId);
	}
	//add an account to the User's profile
	public Long addAccount(User user) {
		Account account = new Account();
		Integer accountNum = user.getAccounts().size() + 1;
		account.setAccountName("Account #" + accountNum);
		account.getUsers().add(user);
		user.getAccounts().add(account);
		accountRepo.save(account);
		System.out.println("AccountId is " + account.getAccountId());
		return account.getAccountId();
		
	}
	
	public Stream<Account> findAccountById(Long accountId, User user) {
		Stream<Account> account = user.getAccounts().stream().filter(x -> x.getAccountId().equals(accountId));
		return account;
	}
	
	
	// update the type of account the user has.  
	public User saveAccount(Account account, User user) {
		
		for(Account acc: user.getAccounts()) {
			if(acc.getAccountId().equals(account.getAccountId())) {
				acc.setAccountName(account.getAccountName());
			}
		}
		return user;
	}

	
}
