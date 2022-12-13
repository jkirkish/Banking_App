package com.coderscampus.Assignment_13.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coderscampus.Assignment_13.domain.Account;
import com.coderscampus.Assignment_13.domain.Transaction;
import com.coderscampus.Assignment_13.domain.User;
import com.coderscampus.Assignment_13.repository.TransactionRepository;

@Service
public class TransactionService {
	
	@Autowired
	private TransactionRepository transRepo;
	
	private AccountService accountService;
	
	/*this method insert a new transaction or updates a current transaction in the corresponding 
	 * accountId.
	 * 
	 */
	public Transaction saveTransaction(Long transId,LocalDateTime transDate, Double Amount, String type, Long accountId,User user) {
		Account account = accountService.findById(accountId);
		Optional<Transaction> transaction = transRepo.findById(transId);
		if(transaction.get().getTransactionId() == null) {
			Transaction trans = new Transaction();
			trans.setTransactionDate(transDate);
			trans.setAmount(Amount);
			trans.setType(type);
			account.getTransactions().add(trans);
		}
		int index = account.getTransactions().size()-1;
		Transaction transactionSave = account.getTransactions().get(index);
		return transRepo.save(transactionSave);
		
	}

}
