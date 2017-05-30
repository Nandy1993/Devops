package com.capgemini.test;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.capgemini.beans.Account;
import com.capgemini.exceptions.InsufficientBalanceException;
import com.capgemini.exceptions.InsufficientInitialAmountException;
import com.capgemini.exceptions.InvalidAccountNumberException;
import com.capgemini.repository.AccountRepository;
import com.capgemini.service.AccountService;
import com.capgemini.service.AccountServiceImpl;
public class AccountServiceImplTest {

	@Mock
	AccountRepository accountRepository;
	AccountService accountService;
	

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		accountService = new AccountServiceImpl(accountRepository);
	}


	/*
	 * test cases for create account
	 * 1. when the amount is less than 500 system should generate exception
	 * 2. when the valid(101,5000) info is passed account should be created successfully
	 */
	@Test(expected=com.capgemini.exceptions.InsufficientInitialAmountException.class)
	public void whenTheAmountIsLessThanFiveHundredSystemShouldThrowException() throws InsufficientInitialAmountException
	{
		accountService.createAccount(101,400);
	}
	
	public void whenTheAmountIsLessThanFiveHundredSystemShouldThrowException
	@Test
	public void whenTheValidInfoIsPassedAccountShouldBeCreatedSuccessfully() throws InsufficientInitialAmountException
	{    
		
		Account account = new Account();
		
		account.setAccountNumber(101);
		account.setAmount(5000);
		when(accountRepository.save(account)).thenReturn(true);
		assertEquals(account, accountService.createAccount(101, 5000));
	}
	/*
	 * test cases for withdraw amount
	 * 1. when the account is invalid then system should generate exception
	 * 2. when the amount is less than 500 system should generate exception
	 * 3. when the valid(101,5000) info is passed amount should be withdrawn succesfully successfully
	 */
	
	@Test(expected = com.capgemini.exceptions.InvalidAccountNumberException.class)
	public void whenTheAccountIsInvalidSystemShouldThrowException() throws InvalidAccountNumberException, InsufficientBalanceException{
		accountService.withdrawAmout(-0, 500);
	}
	
	@Test(expected = com.capgemini.exceptions.InsufficientBalanceException.class)
	public void whenTheAmounyIsInsufficientSystemShouldThrowException() throws InvalidAccountNumberException, InsufficientBalanceException{
		accountService.withdrawAmout(101,10011);
	}
	
	@Test
	public void whenTheValidInfoIsPassedAmountShouldBeWithdrawnSuccessfully() throws InvalidAccountNumberException,InsufficientBalanceException{

		Account account = new Account();
		account.setAccountNumber(101);
		account.setAmount(500);
		when(accountRepository.searchAccount(101)).thenReturn(account);
		assertEquals(500, accountService.withdrawAmout(101, 500));
	}
	
	/*
	 * test cases for deposit amount
	 * 1. when the account is invalid then system should generate exception
	 * 2. when the valid(101,5000) info is passed account should be deposit succesfully successfully
	 */
	
	@Test(expected = com.capgemini.exceptions.InvalidAccountNumberException.class)
	public void whenTheAccountIsInvalidSystemShouldThrowExceptionForDeposit() throws InvalidAccountNumberException{
		accountService.depositAmount(0, 500);
	}
	
	
	@Test
	public void whenTheValidInfoIsPassedAmountShouldBeWithdrawnSuccessfullyForDeposit() throws InvalidAccountNumberException{
		Account account = new Account();
		account.setAccountNumber(101);
		account.setAmount(500);
		when(accountRepository.searchAccount(101)).thenReturn(account);
		assertEquals(1500, accountService.depositAmount(101, 500));
	}
	
}
