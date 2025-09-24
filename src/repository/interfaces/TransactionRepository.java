package repository.interfaces;

import model.Account;
import model.Transaction;
import model.enums.AccountType;
import model.enums.TransactionType;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface TransactionRepository {

    public boolean createTransaction(int idTransaction, double amount, TransactionType transactionType, Account accountDestination, Account accountOrigin, LocalDateTime date, String motif);
    public boolean removeTransaction(int idTransaction);
    public boolean updateTransaction(int idTransaction, double amount, TransactionType transactionType, Account accountDestination, Account accountOrigin, LocalDateTime date, String motif);
    public Optional<Transaction> getTransactionById(int idTransaction);
    public List<Transaction> getAllTransactions();
    public List<Transaction> getAllTransactionsByAccountId(int idAccount);
    public List<Transaction> getAllTransactionsByClientId(int idClient);
    public List<Transaction> filterTransactionsByType(int idAccount, TransactionType transactionType);
    public List<Transaction> filterTransactionsByAmount(int idAccount, double minAmount, double maxAmount);
    public List<Transaction> filterTransactionsByDate(int idAccount, LocalDateTime from, LocalDateTime to);
    public List<Transaction> getSuspiciousTransactions(int idAccount, double thresholdAmount, int repetitionCount);

}
