package view;

import model.Account;
import model.Transaction;
import view.interfaces.View;

import java.util.List;
import java.util.Scanner;

public class ClientMenuView implements View {
    private final Scanner scanner = new Scanner(System.in);

    @Override
    public void display() {
        System.out.println("=== Client Menu ===");
        System.out.println("1. View Accounts");
        System.out.println("2. Create Account");
        System.out.println("3. Delete Account");
        System.out.println("4. Transactions History");
        System.out.println("5. Make Transaction");
        System.out.println("6. Logout");
    }

    public void displayAccounts(List<Account> accounts) {
        System.out.println("=== Your Accounts ===");
        for (Account account : accounts) {
            System.out.println("Account ID: " + account.getIdAccount());
            System.out.println("Type: " + account.getAccountType());
            System.out.println("Balance: " + account.getBalance());
            System.out.println("---------------");
        }
    }

    public void displayTransactions(List<Transaction> transactions) {
        System.out.println("=== Transactions History ===");
        for (Transaction t : transactions) {
            System.out.println("ID: " + t.getIdTransaction());
            System.out.println("Type: " + t.getTransactionType());
            System.out.println("Amount: " + t.getAmount());
            System.out.println("Date: " + t.getDate());
            System.out.println("Motif: " + t.getMotif());
            System.out.println("---------------");
        }
    }

    public int getChoice() {
        return Integer.parseInt(scanner.nextLine());
    }
}