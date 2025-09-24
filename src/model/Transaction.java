package model;

import java.time.LocalDateTime;
import model.enums.TransactionType;
import model.Account;

public class Transaction {

    private int idTransaction;
    private double amount;
    private TransactionType transactionType;
    private Account accountDestination;
    private Account account;
    private LocalDateTime date;
    private String motif;

    public Transaction(int idTransaction, double amount, TransactionType transactionType, Account account, LocalDateTime date, String motif, Account accountDestination){
        this.idTransaction = idTransaction;
        this.amount = amount;
        this.transactionType = transactionType;
        this.account = account;
        this.date = date;
        this.motif = motif;
        this.accountDestination = accountDestination;
    }


    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public void setAmount(double amount) {
        if(amount < 0){
            throw new IllegalArgumentException("Amount cannot be negative");
        }
        this.amount = amount;
    }

    public int getIdTransaction() {
        return idTransaction;
    }

    public double getAmount() {
        return amount;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public Account getAccountDestination() {
        return accountDestination;
    }

    public Account getAccount() {
        return account;
    }

    public String getMotif() {
        return motif;
    }

    public void setIdTransaction(int idTransaction) {
        this.idTransaction = idTransaction;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public void setAccountDestination(Account accountDestination) {
        this.accountDestination = accountDestination;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public void setMotif(String motif) {
        this.motif = motif;
    }
}
