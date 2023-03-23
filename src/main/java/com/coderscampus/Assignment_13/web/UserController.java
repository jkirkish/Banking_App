package com.coderscampus.Assignment_13.web;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.coderscampus.Assignment_13.domain.Account;
import com.coderscampus.Assignment_13.domain.User;
import com.coderscampus.Assignment_13.service.AccountService;
import com.coderscampus.Assignment_13.service.UserService;

@Controller
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private AccountService accountService;

	@GetMapping("/register")
	public String getCreateUser(ModelMap model) {
		model.put("user", new User());
		return "register";
	}

	@PostMapping("/register")
	public String postCreateUser(User user) {
		System.out.println(user);
		userService.saveUser(user);
		return "redirect:/register";
	}

	@GetMapping("/users")
	public String getAllUsers(ModelMap model) {
		Set<User> users = userService.findAll();
		model.put("users", users);
		if (users.size() == 1) {
			model.put("user", users.iterator().next());
		}
		return "users";
	}

	@PostMapping("/users")
	public String postUpdateUser(User user) {
		List<User> users = userService.findAllUsers();
		if (users.size() == 1) {
			user.setUserId(1L);
		}
		System.out.println("UserId is: " + user.getUserId());
		System.out.println(user.getAddress().toString());
		userService.saveUser(user);
		return "redirect:/users/" + user.getUserId();
	}

	@GetMapping("/users/{userId}")
	public String getOneUser(ModelMap model, @PathVariable Long userId) {
		User user = userService.findById(userId);
		model.put("users", Arrays.asList(user));
		model.put("user", user);
		return "users";
	}

	@GetMapping("/users/{userId}/accounts/{accountId}")
	public String getAccounts(ModelMap model, @PathVariable Long userId, @PathVariable Long accountId) {
		User user = userService.findById(userId);
		Stream<Account> account = accountService.findAccountById(accountId, user);
		Account testAccount = account.iterator().next();
		model.put("user", user);
		model.put("account", testAccount);
		return "accounts";
	}

	@PostMapping("/users/{userId}")
	public String postOneUser(User user) {
		userService.saveUser(user);
		return "redirect:/users/" + user.getUserId();
	}

	@PostMapping("/users/{userId}/delete")
	public String deleteOneUser(@PathVariable Long userId) {
		userService.delete(userId);
		return "redirect:/users";
	}

	@PostMapping("/users/{userId}/accounts")
	public String postAccount(@PathVariable Long userId, User user) {
		Long accountNum = accountService.addAccount(user);
		userService.saveUser(user);
		return "redirect:/users/" + userId + "/accounts/" + accountNum;
	}

	@PostMapping("/users/{userId}/accounts/{accountId}")
	public String updateAccount(@PathVariable Long userId, @PathVariable Long accountId, Account account) {
		account = accountService.saveAccount(account);
		return "redirect:/users/" + userId + "/accounts/" + accountId;
	}

}
