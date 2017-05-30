package com.capgemini.service;

import com.capgemini.beans.Account;
import com.capgemini.exceptions.InsufficientBalanceException;
import com.capgemini.exceptions.InsufficientInitialAmountException;
import com.capgemini.exceptions.InvalidAccountNumberException;
import com.capgemini.repository.AccountRepository;

public class AccountServiceImpl implements AccountService {
	AccountRepository accountRepository;
	
	public AccountServiceImpl(AccountRepository accountRepository) {
		super();
		this.accountRepository = accountRepository;
	}	
	/* (non-Javadoc)
	 * @see com.capgemini.service.AccountService#createAccount(int, int)
	 */
	@Override
	public Account createAccount(int accountNumber,int amount)throws InsufficientInitialAmountException
	{
		if(amount<500)
		{
			throw new InsufficientInitialAmountException();
		}
		
		Account account = new Account();
		account.setAccountNumber(accountNumber);
		account.setAmount(amount);
		
		
		if(accountRepository.save(account))
		{
			return account;
		}
		
		return null;
	}
	@Override
	public int depositAmount(int accountNumber, int amount) throws InvalidAccountNumberException {

		if(accountNumber <= 0){
			throw new InvalidAccountNumberException();
		}
		int balance = 1000;
		balance = balance + amount;
		Account account = new Account();
		account.setAccountNumber(accountNumber);
		account.setAmount(balance);	
		
		if(accountRepository.searchAccount(accountNumber)!=null)
		{
			
			return balance;
		
		}
		return 0;
		
	}
	@Override
	public int withdrawAmout(int accountNumber, int amount) throws InvalidAccountNumberException, InsufficientBalanceException {
		if(accountNumber <= 0){
			throw new InvalidAccountNumberException();
		}
		else if(amount>=1000)
		{
			throw new InsufficientBalanceException();
		}

		int balance = 1000;
		balance = balance - amount;
		Account account = new Account();
		account.setAccountNumber(accountNumber);
		account.setAmount(balance);
		
		
		if(accountRepository.searchAccount(accountNumber)!=null)
		{
			
			return balance;
		
		}
		return 0;
	}

}
