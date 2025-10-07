package controller;

import model.Account;
import model.Transaction;
import service.ClientService;
import view.ClientMenuView;
import view.MessageView;
import view.TransactionView;

import java.util.List;

public class ClientController {
    private final ClientService clientService;
    private final ClientMenuView clientMenuView;
    private final TransactionView transactionView;
    private final int clientId;

    public ClientController(ClientService clientService, int clientId) {
        this.clientService = clientService;
        this.clientMenuView = new ClientMenuView();
        this.transactionView = new TransactionView();
        this.clientId = clientId;
    }

    public void handleClientMenu() {
        boolean running = true;
        while (running) {
            clientMenuView.display();
            int choice = clientMenuView.getChoice();

            switch (choice) {
                case 1:
                    clientMenuView.displayAccounts(clientService.getClientAccounts(clientId));
                    break;
                case 2:
                    // Handle create account

                    break;
                case 3:
                    // Handle delete account
                    break;
                case 4:
                    clientMenuView.displayTransactions(clientService.filterTransactionsByDate(clientId, null, null));
                    break;
                case 5:
                    handleTransaction();
                    break;
                case 6:
                    running = false;
                    break;
                default:
                    MessageView.displayError("Invalid option!");
            }
        }
    }

    private void handleTransaction() {
        transactionView.display();
        int choice = transactionView.getChoice();

        switch (choice) {
            case 1:
                handleDeposit();
                break;
            case 2:
                handleWithdrawal();
                break;
            case 3:
                handleTransfer();
                break;
        }
    }

    private void handleDeposit() {
        int accountId = transactionView.getAccountId();
        double amount = transactionView.getAmount();
        if (clientService.makeDeposit(accountId, amount)) {
            MessageView.displaySuccess("Deposit successful!");
        } else {
            MessageView.displayError("Deposit failed!");
        }
    }

    private void handleWithdrawal() {
        int accountId = transactionView.getAccountId();
        double amount = transactionView.getAmount();
        if (clientService.makeWithdrawal(accountId, amount)) {
            MessageView.displaySuccess("Withdrawal successful!");
        } else {
            MessageView.displayError("Withdrawal failed!");
        }
    }

    private void handleTransfer() {
        MessageView.displayInfo("Enter source account details:");
        int sourceAccountId = transactionView.getAccountId();
        MessageView.displayInfo("Enter destination account details:");
        int destAccountId = transactionView.getAccountId();
        double amount = transactionView.getAmount();

        if (clientService.makeTransfer(sourceAccountId, destAccountId, amount)) {
            MessageView.displaySuccess("Transfer successful!");
        } else {
            MessageView.displayError("Transfer failed!");
        }
    }

    public List<Transaction> getClientTransactions() {
        try {
            return clientService.filterTransactionsByDate(clientId, null, null);
        } catch (Exception e) {
            MessageView.displayError("Error getting transactions: " + e.getMessage());
            return List.of();
        }
    }

    public boolean performDeposit(int accountId, double amount) {
        try {
            return clientService.makeDeposit(accountId, amount);
        } catch (Exception e) {
            MessageView.displayError("Error making deposit: " + e.getMessage());
            return false;
        }
    }

    public boolean performWithdrawal(int accountId, double amount) {
        return clientService.makeWithdrawal(accountId, amount);
    }

    public boolean performTransfer(int sourceAccountId, int destAccountId, double amount) {
        return clientService.makeTransfer(sourceAccountId, destAccountId, amount);
    }

    public List<Account> getClientAccounts() {
        return clientService.getClientAccounts(clientId);
    }

    public boolean deleteAccount(int accountId) {
        try {
            return clientService.deleteAccount(accountId, clientId);
        } catch (Exception e) {
            MessageView.displayError("Error deleting account: " + e.getMessage());
            return false;
        }
    }

    public List<Transaction> filterTransactionsByType(model.enums.TransactionType type) {
        return clientService.filterTransactionsByType(clientId, type);
    }

    public List<Transaction> filterTransactionsByAmount(double minAmount, double maxAmount) {
        return clientService.filterTransactionsByAmount(clientId, minAmount, maxAmount);
    }


}