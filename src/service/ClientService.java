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

        return clientRepository.getClientById(clientId);
    }

    public List<Account> getClientAccounts(int clientId) {

        Optional<Client> client = clientRepository.getClientById(clientId);
        if (client.isEmpty()) {
            return List.of();
        }
        return clientRepository.getAccountsByClientId(clientId);
    }

    public double calculateTotalBalance(int clientId){

        List<Account> accounts = clientRepository.getAccountsByClientId(clientId);
        double total = 0;
        for (Account account : accounts){
            total += account.getBalance();
        }
        return total;
    }

    public List<Transaction> filterTransactionsByType(int clientId, TransactionType type) {

        List<Transaction> transactions = transactionRepository.filterTransactionsByType(clientId, type);
        return transactions;
    }

    public List<Transaction> filterTransactionsByAmount(int clientId, double minAmount, double maxAmount){

        List<Transaction> transactions = transactionRepository.filterTransactionsByAmount(clientId, minAmount, maxAmount);
        return transactions;
    }

    public List<Transaction> filterTransactionsByDate(int clientId, LocalDateTime startDate, LocalDateTime endDate){
        try {
            if (startDate == null && endDate == null) {
                return transactionRepository.getAllTransactionsByClientId(clientId);
            }
            List<Transaction> transactions = transactionRepository.filterTransactionsByDate(clientId, startDate, endDate);
            return transactions;
        } catch (Exception e) {
            System.err.println("Error filtering transactions: " + e.getMessage());
            return List.of();
        }
    }

    public double calculateTotalDeposits(int clientId){

        List<Transaction> transactions = transactionRepository.filterTransactionsByType(clientId, TransactionType.DEPOT);
        double total = 0;
        for (Transaction transaction : transactions){
            total += transaction.getAmount();
        }
        return total;
    }

    public double calculateTotalWithdrawals(int clientId){

        List<Transaction> transactions = transactionRepository.filterTransactionsByType(clientId, TransactionType.RETRAIT);
        double total = 0;
        for (Transaction transaction : transactions){
            total += transaction.getAmount();
        }
        return total;
    }

    public boolean makeDeposit(int accountId, double amount){

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

    public boolean deleteAccount(int accountId, int clientId) {
        Optional<Account> accountOpt = accountRepository.getAccountById(accountId);
        if (accountOpt.isEmpty()) {
            return false;
        }
        
        Account account = accountOpt.get();
        // Check if account belongs to this client
        if (account.getClient().getIdClient() != clientId) {
            return false;
        }
        
        // Remove account from client's account list
        Optional<Client> clientOpt = clientRepository.getClientById(clientId);
        if (clientOpt.isPresent()) {
            clientOpt.get().getAccounts().removeIf(acc -> acc.getIdAccount() == accountId);
        }
        
        // Remove account from repository
        return accountRepository.removeAccount(accountId);
    }

}