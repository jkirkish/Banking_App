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
import com.coderscampus.Assignment_13.service.UserService;


//@Controller: The @Controller is a class-level annotation. It is a specialization of @Component. 
//It marks a class as a web request handler. It is often used to serve web pages. By default, it 
//returns a string that indicates which route to redirect.
//https://www.w3schools.in/mvc-architecture
//https://www.thymeleaf.org/doc/articles/springmvcaccessdata.html

@Controller
public class UserController {
	
	//It is used to autowire spring bean on setter methods, instance variable, and constructor.
	//When we use @Autowired annotation, the spring container auto-wires the bean by matching data-type.
	@Autowired
	private UserService userService;
	
	/*@GetMapping annotated methods in the @Controller annotated classes handle the HTTP GET requests 
	 * matched with given URI expression
	 */
	@GetMapping("/register")
	public String getCreateUser (ModelMap model) {
		model.put("user", new User());
		return "register";
	}
	/*POST is used to send data to a server to create/update a resource. The data sent to the server with
	 *POST is stored in the request body of the HTTP request: POST /test/demo_form.php HTTP/1.1
	 * 
	 */
	@PostMapping("/register")
	public String postCreateUser (User user) {
		System.out.println(user);
		userService.saveUser(user);
		return "redirect:/register";
	}
	
	@GetMapping("/users")
	public String getAllUsers (ModelMap model) {
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
	   if(users.size()== 1) {
		   user.setUserId(1L);
	   }
	   System.out.println("UserId is: " + user.getUserId());
		System.out.println(user.getAddress().toString());
		userService.saveUser(user);
		return "redirect:/users/"+user.getUserId();
	}
	
	@GetMapping("/users/{userId}")
	public String getOneUser (ModelMap model, @PathVariable Long userId) {
		User user = userService.findById(userId);
		model.put("users", Arrays.asList(user));
		model.put("user", user);
		return "users";
	}
	
	/*
	 * 
	 */
	@GetMapping("/users/{userId}/accounts/{accountId}")
	public String getAccounts(ModelMap model, @PathVariable Long userId, @PathVariable Long accountId) {
		User user = userService.findById(userId);
		Stream<Account> account = userService.findAccountById(accountId, user);
		Account testAccount = account.iterator().next();
		model.put("user", user);
		model.put("account", testAccount);
		return "accounts";
	}
	
	@PostMapping("/users/{userId}")
	public String postOneUser (User user) {
		userService.saveUser(user);
		return "redirect:/users/"+user.getUserId();
	}
	
	@PostMapping("/users/{userId}/delete")
	public String deleteOneUser (@PathVariable Long userId) {
		userService.delete(userId);
		return "redirect:/users";
	}
	@PostMapping("/users/{userId}/accounts")
	public String postAccount(@PathVariable Long userId, User user) {
		Long accountNum = userService.addAccount(user);
		userService.saveUser(user);
		return "redirect:/users/" + userId + "/accounts/" + accountNum;
	}
	
	//this method shows all of the accounts associated with the user.  You can update the account
	@PostMapping("/users/{userId}/accounts/{accountId}")
	public String updateAccount(@PathVariable Long userId, @PathVariable Long accountId, Account account, User user) {
		User userUpdate = userService.saveAccount(account, user);
		userService.saveUser(userUpdate);
		return "redirect:/users/" + userId + "/accounts/" + accountId;
	}
	
}
