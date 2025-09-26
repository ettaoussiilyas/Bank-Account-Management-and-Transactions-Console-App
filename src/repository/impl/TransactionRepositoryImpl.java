package repository.impl;

import model.Transaction;
import model.Account;
import model.enums.TransactionType;
import repository.interfaces.TransactionRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Collectors;

public class TransactionRepositoryImpl implements TransactionRepository {
    List<Transaction> transactions = new ArrayList<>();

    @Override
    public boolean createTransaction(int idTransaction, double amount, TransactionType transactionType, Account accountDestination, Account accountOrigin, LocalDateTime date, String motif) {
        Transaction transaction = new Transaction(idTransaction, amount, transactionType, accountDestination, accountOrigin, date, motif);
        return transactions.add(transaction);
    }

    @Override
    public boolean removeTransaction(int idTransaction) {
        return transactions.removeIf(transaction -> transaction.getIdTransaction() == idTransaction);
    }

    @Override
    public boolean updateTransaction(int idTransaction, double amount, TransactionType transactionType, Account accountDestination, Account accountOrigin, LocalDateTime date, String motif) {
        for (Transaction transaction : transactions) {
            if (transaction.getIdTransaction() == idTransaction) {
                transaction.setAmount(amount);
                transaction.setTransactionType(transactionType);
                transaction.setAccountDestination(accountDestination);
                transaction.setAccount(accountOrigin);
                transaction.setDate(date);
                transaction.setMotif(motif);
                return true;
            }
        }
        return false;
    }

    @Override
    public Optional<Transaction> getTransactionById(int idTransaction) {
        for (Transaction transaction : transactions) {
            if (transaction.getIdTransaction() == idTransaction) {
                return Optional.of(transaction);
            }
        }
        return Optional.empty();
    }

    @Override
    public List<Transaction> getAllTransactions() {
        return transactions;
    }

    @Override
    public List<Transaction> getAllTransactionsByAccountId(int idAccount) {
        return transactions.stream()
                .filter(t -> t.getAccountDestination() != null && t.getAccountDestination().getIdAccount() == idAccount
                        || t.getAccount() != null && t.getAccount().getIdAccount() == idAccount)
                .collect(Collectors.toList());
    }

    @Override
    public List<Transaction> getAllTransactionsByClientId(int idClient) {
        return transactions.stream()
                .filter(t -> (t.getAccountDestination() != null && t.getAccountDestination().getClient().getIdClient() == idClient)
                        || (t.getAccount() != null && t.getAccount().getClient().getIdClient() == idClient))
                .collect(Collectors.toList());
    }

    @Override
    public List<Transaction> filterTransactionsByType(int idAccount, TransactionType transactionType) {
        return transactions.stream()
                .filter(t -> (t.getAccountDestination() != null && t.getAccountDestination().getIdAccount() == idAccount
                        || t.getAccount() != null && t.getAccount().getIdAccount() == idAccount)
                        && t.getTransactionType() == transactionType)
                .collect(Collectors.toList());
    }

    @Override
    public List<Transaction> filterTransactionsByAmount(int idAccount, double minAmount, double maxAmount) {
        return transactions.stream()
                .filter(t -> (t.getAccountDestination() != null && t.getAccountDestination().getIdAccount() == idAccount
                        || t.getAccount() != null && t.getAccount().getIdAccount() == idAccount)
                        && t.getAmount() >= minAmount && t.getAmount() <= maxAmount)
                .collect(Collectors.toList());
    }

    @Override
    public List<Transaction> filterTransactionsByDate(int idAccount, LocalDateTime from, LocalDateTime to) {
        return transactions.stream()
                .filter(t -> (t.getAccountDestination() != null && t.getAccountDestination().getIdAccount() == idAccount
                        || t.getAccount() != null && t.getAccount().getIdAccount() == idAccount)
                        && !t.getDate().isBefore(from) && !t.getDate().isAfter(to))
                .collect(Collectors.toList());
    }

    @Override
    public List<Transaction> getSuspiciousTransactions(int idAccount, double thresholdAmount, int repetitionCount) {
        List<Transaction> filtered = filterTransactionsByAmount(idAccount, thresholdAmount, Double.MAX_VALUE);
        if (filtered.size() >= repetitionCount) {
            return filtered;
        }
        return new ArrayList<>();
    }
}
