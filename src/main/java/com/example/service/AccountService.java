package com.example.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.repository.AccountRepository;

@Service
public class AccountService {

    
    private AccountRepository accountRepository;

    /**
     * Constructor with a AccountRepository param
     * @param accountRepository
     */
    public AccountService(AccountRepository accountRepository){
        this.accountRepository = accountRepository;
    }

    /**
     * Service method that calls the derived save method from the AccountRespitory interface
     * Returns the saved/created Account
     * @param account
     * @return
     */
    public Account createAccount(Account account){
        return this.accountRepository.save(account);
    }

    /**
     * Service method that returns an Account entry if an entry with the passed username exists
     * Returns null if no entry exists
     * @param username
     * @return
     */
    public Account getAccountByUsername(String username){
        Optional<Account> optAccount = this.accountRepository.findByUsername(username);

        if(optAccount.isPresent()) return optAccount.get();

        else return null;
    }

    /**
     * Service method that returns an Account entry if an entry with the passed account_id exists
     * Returns null if no entry exists
     * @param username
     * @return
     */
    public Account getAccountById(int id){
        Optional<Account> optAccount = this.accountRepository.findById(id);

        if(optAccount.isPresent()) return optAccount.get();

        else return null;
    }

    /**
     * Service method that returns the result of the derived findAll method from the accountRepository interface
     * Returns a list of all Accounts, if no accounts list will be empty
     * @return
     */
    public List<Account> getAllAccounts(){
        return this.accountRepository.findAll();
    }

    /**
     * Service method that returns the result of the method findById in the accountRepository interface
     * @param id
     * @return
     */
    public boolean checkAccountIdExists(int id){
        return this.accountRepository.existsById(id);
    }
    
    /**
     * Service method that checks if an account exists by checking if there is an existing record that has a username that matches the parameter
     * @param username
     * @return
     */
    public boolean checkAccountUsernameExists(String username){
        if(this.getAccountByUsername(username) != null) return true;
        return false;
    }

}

