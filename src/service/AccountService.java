package service;

import model.Account;
import model.Client;
import repository.impl.AccountRepositoryImpl;
import repository.interfaces.AccountRepository;
import model.enums.AccountType;

import java.util.List;
import java.util.Optional;

public class AccountService {
    private final AccountRepository accountRepository;

    public AccountService() {
        this.accountRepository = new AccountRepositoryImpl();
    }

    public Optional<Account> getAccountById(int accountId) {

        return accountRepository.getAccountById(accountId);
    }

    public boolean updateAccount(int accountId, double balance, AccountType accountType, Client client) {

        return accountRepository.updateAccount(accountId, balance, accountType, client);
    }

    public double getBalance(int accountId) {
        if (accountId < 0) {
            throw new IllegalArgumentException("Invalid account ID");
        }
        return accountRepository.getAccountBalance(accountId);
    }

    public AccountType getAccountType(int accountId) {

        return accountRepository.getAccountType(accountId);
    }

    public List<Account> getAllAccounts() {
        return accountRepository.getAllAccounts();
    }

    public Optional<Client> getAccountOwner(int accountId) {

        return accountRepository.getClientByAccountId(accountId);
    }
}