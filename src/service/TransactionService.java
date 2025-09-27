package service;

import model.Account;
import model.Transaction;
import model.enums.TransactionType;
import repository.impl.AccountRepositoryImpl;
import repository.impl.TransactionRepositoryImpl;
import repository.interfaces.AccountRepository;
import repository.interfaces.TransactionRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;

    public TransactionService() {
        this.transactionRepository = new TransactionRepositoryImpl();
        this.accountRepository = new AccountRepositoryImpl();
    }

    public boolean createDeposit(int accountId, double amount, String motif) {

        Optional<Account> accountOpt = accountRepository.getAccountById(accountId);
        if (accountOpt.isEmpty()) {
            throw new NoSuchElementException("Account not found");
        }

        Account account = accountOpt.get();
        account.setBalance(account.getBalance() + amount);

        int newTransactionId = (int) (Math.random() * 100000);
        return transactionRepository.createTransaction(
                newTransactionId,
                amount,
                TransactionType.DEPOT,
                account,
                null,
                LocalDateTime.now(),
                motif
        );
    }

    public boolean createWithdrawal(int accountId, double amount, String motif) {

        Optional<Account> accountOpt = accountRepository.getAccountById(accountId);
        if (accountOpt.isEmpty()) {
            throw new NoSuchElementException("Account not found");
        }

        Account account = accountOpt.get();
        if (account.getBalance() < amount) {
            throw new ArithmeticException("Insufficient funds");
        }

        account.setBalance(account.getBalance() - amount);

        int newTransactionId = (int) (Math.random() * 100000);
        return transactionRepository.createTransaction(
                newTransactionId,
                amount,
                TransactionType.RETRAIT,
                null,
                account,
                LocalDateTime.now(),
                motif
        );
    }

    public boolean createTransfer(int sourceAccountId, int destinationAccountId, double amount, String motif) {

        Optional<Account> sourceAccountOpt = accountRepository.getAccountById(sourceAccountId);
        Optional<Account> destAccountOpt = accountRepository.getAccountById(destinationAccountId);

        if (sourceAccountOpt.isEmpty() || destAccountOpt.isEmpty()) {
            throw new NoSuchElementException("One or both accounts not found");
        }

        Account sourceAccount = sourceAccountOpt.get();
        Account destAccount = destAccountOpt.get();

        if (sourceAccount.getBalance() < amount) {
            throw new ArithmeticException("Insufficient funds in source account");
        }

        sourceAccount.setBalance(sourceAccount.getBalance() - amount);
        destAccount.setBalance(destAccount.getBalance() + amount);

        int newTransactionId = (int) (Math.random() * 100000);
        return transactionRepository.createTransaction(
                newTransactionId,
                amount,
                TransactionType.VIREMENT,
                destAccount,
                sourceAccount,
                LocalDateTime.now(),
                motif
        );
    }

    public boolean deleteTransaction(int transactionId) {
        Optional<Transaction> transactionOpt = transactionRepository.getTransactionById(transactionId);
        if (transactionOpt.isEmpty()) {
            throw new NoSuchElementException("Transaction not found");
        }

        Transaction transaction = transactionOpt.get();
        // Reverse the transaction effects
        switch (transaction.getTransactionType()) {
            case DEPOT:
                if (transaction.getAccountDestination() != null) {
                    transaction.getAccountDestination().setBalance(
                            transaction.getAccountDestination().getBalance() - transaction.getAmount()
                    );
                }
                break;
            case RETRAIT:
                if (transaction.getAccount() != null) {
                    transaction.getAccount().setBalance(
                            transaction.getAccount().getBalance() + transaction.getAmount()
                    );
                }
                break;
            case VIREMENT:
                if (transaction.getAccount() != null && transaction.getAccountDestination() != null) {
                    transaction.getAccount().setBalance(
                            transaction.getAccount().getBalance() + transaction.getAmount()
                    );
                    transaction.getAccountDestination().setBalance(
                            transaction.getAccountDestination().getBalance() - transaction.getAmount()
                    );
                }
                break;
        }

        return transactionRepository.removeTransaction(transactionId);
    }

    public List<Transaction> getTransactionHistory(int accountId) {
        return transactionRepository.getAllTransactionsByAccountId(accountId);
    }

    public List<Transaction> getClientTransactions(int clientId) {
        return transactionRepository.getAllTransactionsByClientId(clientId);
    }

    public List<Transaction> filterTransactionsByType(int accountId, TransactionType type) {
        return transactionRepository.filterTransactionsByType(accountId, type);
    }

    public List<Transaction> filterTransactionsByAmount(int accountId, double minAmount, double maxAmount) {

        return transactionRepository.filterTransactionsByAmount(accountId, minAmount, maxAmount);
    }

    public List<Transaction> filterTransactionsByDate(int accountId, LocalDateTime startDate, LocalDateTime endDate) {
        if (startDate == null || endDate == null || startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("Invalid date range");
        }
        return transactionRepository.filterTransactionsByDate(accountId, startDate, endDate);
    }

    public List<Transaction> getSuspiciousTransactions(int accountId, double thresholdAmount, int repetitionCount) {

        return transactionRepository.getSuspiciousTransactions(accountId, thresholdAmount, repetitionCount);
    }

    public double calculateTotalTransactionAmount(int accountId, TransactionType type) {
        List<Transaction> transactions = filterTransactionsByType(accountId, type);
        return transactions.stream()
                .mapToDouble(Transaction::getAmount)
                .sum();
    }
}