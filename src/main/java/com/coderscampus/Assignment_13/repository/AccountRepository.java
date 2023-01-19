package com.coderscampus.Assignment_13.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.coderscampus.Assignment_13.domain.Account;
import com.coderscampus.Assignment_13.domain.User;

@Repository
public interface AccountRepository extends JpaRepository<Account,Long>{

	@Query("select u from User u"
			+ " left join fetch u.accounts"
	        + " left join fetch u.address"
			+ " where u.userId = :userId")
		User findByIdWithAccounts(Long userId);
}
