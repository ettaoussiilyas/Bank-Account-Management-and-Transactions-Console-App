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
        if (accountId < 0) {
            throw new IllegalArgumentException("Invalid account ID");
        }
        return accountRepository.getAccountById(accountId);
    }

    public boolean updateAccount(int accountId, double balance, AccountType accountType, Client client) {
        if (accountId < 0 || balance < 0 || accountType == null || client == null) {
            throw new IllegalArgumentException("Invalid parameters");
        }
        return accountRepository.updateAccount(accountId, balance, accountType, client);
    }

    public double getBalance(int accountId) {
        if (accountId < 0) {
            throw new IllegalArgumentException("Invalid account ID");
        }
        return accountRepository.getAccountBalance(accountId);
    }

    public AccountType getAccountType(int accountId) {
        if (accountId < 0) {
            throw new IllegalArgumentException("Invalid account ID");
        }
        return accountRepository.getAccountType(accountId);
    }

    public List<Account> getAllAccounts() {
        return accountRepository.getAllAccounts();
    }

    public Optional<Client> getAccountOwner(int accountId) {
        if (accountId < 0) {
            throw new IllegalArgumentException("Invalid account ID");
        }
        return accountRepository.getClientByAccountId(accountId);
    }
}