package model;

import model.enums.AccountType;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;
import model.Client;
import model.Transaction;

public class Account {

    private int idAccount;
    private double balance;
    private AccountType accountType;
    private Client client;
    private List<Transaction> transactions;
    private LocalDateTime dateCreated;

    public Account(int idAccount, double balance, AccountType accountType, Client client){
        if (balance < 0 ) throw new IllegalArgumentException("Balance cannot be negative");
        this.idAccount = idAccount;
        this.balance = balance;
        this.accountType = accountType;
        this.client = client;
        this.transactions = new ArrayList<Transaction>();
        this.dateCreated = LocalDateTime.now();
    }

    public int getIdAccount(){
        return this.idAccount;
    }

    public void setIdAccount(int idAccount){
        this.idAccount = idAccount;
    }

    public double getBalance(){
        return this.balance;
    }

    public void setBalance(double balance) {
        if(balance < 0){
            throw new IllegalArgumentException("Balance cannot be negative");
        }
        this.balance = balance;
    }

    public AccountType getAccountType(){
        return this.accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public Client getClient(){
        return this.client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public List<Transaction> getTransactions(){
        return this.transactions;
    }

    public void addTransaction(Transaction transaction) {
        this.transactions.add(transaction);
    }

    public LocalDateTime getDateCreated(){
        return this.dateCreated;
    }

    public void setDateCreated(LocalDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }





}
