package controller;

import model.Transaction;
import model.enums.TransactionType;
import service.TransactionService;
import view.MessageView;

import java.time.LocalDateTime;
import java.util.List;

public class TransactionController {
    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    public boolean handleDeposit(int accountId, double amount, String motif) {
        try {
            return transactionService.createDeposit(accountId, amount, motif);
        } catch (Exception e) {
            MessageView.displayError("Error processing deposit: " + e.getMessage());
            return false;
        }
    }

    public boolean handleWithdrawal(int accountId, double amount, String motif) {
        try {
            return transactionService.createWithdrawal(accountId, amount, motif);
        } catch (Exception e) {
            MessageView.displayError("Error processing withdrawal: " + e.getMessage());
            return false;
        }
    }

    public boolean handleTransfer(int sourceAccountId, int destAccountId, double amount, String motif) {
        try {
            return transactionService.createTransfer(sourceAccountId, destAccountId, amount, motif);
        } catch (Exception e) {
            MessageView.displayError("Error processing transfer: " + e.getMessage());
            return false;
        }
    }

    public List<Transaction> getTransactionHistory(int accountId) {
        return transactionService.getTransactionHistory(accountId);
    }

    public List<Transaction> getSuspiciousTransactions(int accountId, double thresholdAmount, int repetitionCount) {
        return transactionService.getSuspiciousTransactions(accountId, thresholdAmount, repetitionCount);
    }

    public List<Transaction> getClientTransactions(int clientId) {
        return transactionService.getClientTransactions(clientId);
    }

}