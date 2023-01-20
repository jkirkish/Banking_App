package com.coderscampus.Assignment_13;
/*
 * @Author Joseph Kirkish
 * 
 */
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Assignment13Application {

	public static void main(String[] args) {
		SpringApplication.run(Assignment13Application.class, args);
		/*2 things left, when there is only one user, and i try to update the user, 
		 * it deletes all the user information. and when i try to create an account, 
		 * it redirects me to http://localhost:8080/users//accounts which gives me a 404 
		 * not found error. again, when there are multiple users all is well
		 * 
		 * 
		 */
	}

}

