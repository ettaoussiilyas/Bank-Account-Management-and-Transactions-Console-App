package controller;

import model.Account;
import model.Client;
import model.enums.AccountType;
import service.AccountService;
import view.MessageView;

import java.util.List;
import java.util.Optional;

public class AccountController {
    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    public List<Account> getAllAccounts() {
        return accountService.getAllAccounts();
    }

    public Optional<Account> getAccount(int accountId) {
        return accountService.getAccountById(accountId);
    }

    public boolean updateAccount(int accountId, double balance, AccountType accountType, Client client) {
        try {
            return accountService.updateAccount(accountId, balance, accountType, client);
        } catch (Exception e) {
            MessageView.displayError("Error updating account: " + e.getMessage());
            return false;
        }
    }

    public double getBalance(int accountId) {
        try {
            return accountService.getBalance(accountId);
        } catch (IllegalArgumentException e) {
            MessageView.displayError("Invalid account ID");
            return -1;
        }
    }

    public Optional<Client> getAccountOwner(int accountId) {
        return accountService.getAccountOwner(accountId);
    }
}