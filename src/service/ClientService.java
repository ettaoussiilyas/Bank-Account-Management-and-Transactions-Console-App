package service;

import model.Account;
import model.Client;
import model.Transaction;
import model.enums.TransactionType;
import repository.impl.AccountRepositoryImpl;
import repository.impl.ClientRepositoryImpl;
import repository.impl.TransactionRepositoryImpl;
import repository.interfaces.AccountRepository;
import repository.interfaces.ClientRepository;
import repository.interfaces.TransactionRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class ClientService {

    private final ClientRepository clientRepository;
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    public ClientService() {
        this.clientRepository = new ClientRepositoryImpl();
        this.accountRepository = new AccountRepositoryImpl();
        this.transactionRepository = new TransactionRepositoryImpl();
    }

    public Optional<Client> getClientInformation(int clientId) {
        if (clientId < 0) {
            throw new IllegalArgumentException("Invalid client ID");
        }
        return clientRepository.getClientById(clientId);
    }

    public List<Account> getClientAccounts(int clientId) {
        if (clientId < 0) {
            throw new IllegalArgumentException("Invalid client ID");
        }
        Optional<Client> client = clientRepository.getClientById(clientId);
        if (client.isEmpty()) {
            return List.of();
        }
        return clientRepository.getAccountsByClientId(clientId);
    }

    public double calculateTotalBalance(int clientId){
        if( clientId < 0){
            throw new IllegalArgumentException("Invalid client id");
        }
        List<Account> accounts = clientRepository.getAccountsByClientId(clientId);
        double total = 0;
        for (Account account : accounts){
            total += account.getBalance();
        }
        return total;
    }

    public List<Transaction> filterTransactionsByType(int clientId, TransactionType type) {
        // check on type
        if (type == null || type != TransactionType.DEPOT && type != TransactionType.RETRAIT && type != TransactionType.VIREMENT) {
            throw new IllegalArgumentException("Invalid transaction type");
        }
        if (clientId < 0) {
            throw new IllegalArgumentException("Invalid client id");
        }
        List<Transaction> transactions = transactionRepository.filterTransactionsByType(clientId, type);
        return transactions;
    }

    public List<Transaction> filterTransactionsByAmount(int clientId, double minAmount, double maxAmount){
        if( clientId < 0 || minAmount < 0 || maxAmount < 0 || minAmount > maxAmount){
            throw new IllegalArgumentException("Invalid arguments");
        }
        List<Transaction> transactions = transactionRepository.filterTransactionsByAmount(clientId, minAmount, maxAmount);
        return transactions;
    }

    public List<Transaction> filterTransactionsByDate(int clientId, LocalDateTime startDate, LocalDateTime endDate){
        if( clientId < 0 || startDate == null || endDate == null || startDate.isAfter(endDate)){
            throw new IllegalArgumentException("Invalid arguments");
        }
        List<Transaction> transactions = transactionRepository.filterTransactionsByDate(clientId, startDate, endDate);
        return transactions;
    }

    public double calculateTotalDeposits(int clientId){
        if( clientId < 0){
            throw new IllegalArgumentException("Invalid client id");
        }
        List<Transaction> transactions = transactionRepository.filterTransactionsByType(clientId, TransactionType.DEPOT);
        double total = 0;
        for (Transaction transaction : transactions){
            total += transaction.getAmount();
        }
        return total;
    }

    public double calculateTotalWithdrawals(int clientId){
        if( clientId < 0){
            throw new IllegalArgumentException("Invalid client id");
        }
        List<Transaction> transactions = transactionRepository.filterTransactionsByType(clientId, TransactionType.RETRAIT);
        double total = 0;
        for (Transaction transaction : transactions){
            total += transaction.getAmount();
        }
        return total;
    }

    public boolean makeDeposit(int accountId, double amount){
        if( accountId < 0 || amount <= 0){
            throw new IllegalArgumentException("Invalid arguments");
        }
        Optional<Account> accountOpt = accountRepository.getAccountById(accountId);
        if (accountOpt.isEmpty()){
            return false;
        }
        Account account = accountOpt.get();
        account.setBalance(account.getBalance() + amount);
        int newTransactionId = (int) (Math.random() * 100000);
        Transaction transaction = new Transaction(newTransactionId, amount, TransactionType.DEPOT, account, null, LocalDateTime.now(), "Deposit");
        transactionRepository.createTransaction(newTransactionId, amount, TransactionType.DEPOT, account, null, LocalDateTime.now(), "Deposit");
        return true;
    }

    public boolean makeWithdrawal(int accountId, double amount){
        if( accountId <0 || amount <= 0){
            throw new IllegalArgumentException("Invalid arguments");
        }
        Optional<Account> account = accountRepository.getAccountById(accountId);

        if( account.isEmpty()){
            return false;
        }
        if( account.get().getBalance() < amount){
            return false;
        }
        account.get().setBalance(account.get().getBalance() - amount);
        int newTransactionId = (int) (Math.random() * 100000);
        transactionRepository.createTransaction(newTransactionId, amount, TransactionType.RETRAIT, null, account.get(), LocalDateTime.now(), "Withdrawal");
        return true;

    }

    public boolean makeTransfer(int sourceAccountId, int destinationAccountId, double amount){
        if (sourceAccountId < 0 || destinationAccountId < 0 || amount <= 0) {
            throw  new IllegalArgumentException("Invalid arguments");
        }
        Optional<Account> sourceAccountOpt = accountRepository.getAccountById(sourceAccountId);
        Optional<Account> destinationAccountIdOpt = accountRepository.getAccountById(destinationAccountId);
        if( sourceAccountOpt.isEmpty() || destinationAccountIdOpt.isEmpty()){
            return false;
        }
        if(sourceAccountOpt.get().getBalance() < amount){
            return false;
        }
        sourceAccountOpt.get().setBalance(sourceAccountOpt.get().getBalance() - amount);
        destinationAccountIdOpt.get().setBalance(destinationAccountIdOpt.get().getBalance() + amount);
        int newTransactionId = (int) (Math.random() * 100000);
        transactionRepository.createTransaction(newTransactionId, amount, TransactionType.VIREMENT, sourceAccountOpt.get(), destinationAccountIdOpt.get(), LocalDateTime.now(), "Transfer");
        return true;
    }

}