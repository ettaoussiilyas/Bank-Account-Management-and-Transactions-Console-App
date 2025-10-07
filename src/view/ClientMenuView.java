package view;

import model.Account;
import model.Client;
import model.Transaction;
import view.interfaces.View;

import java.util.List;
import java.util.Scanner;

public class ClientMenuView implements View {
    private final Scanner scanner = new Scanner(System.in);

    @Override
    public void display() {
        System.out.println("\n=== Client Menu ===");
        System.out.println("1. View Personal Information");
        System.out.println("2. View Accounts");
        System.out.println("3. Delete Account");
        System.out.println("4. Transactions History");
        System.out.println("5. Filter Transactions");
        System.out.println("6. Calculate Totals");
        System.out.println("7. Make Transaction");
        System.out.println("8. Logout");
        System.out.print("Choose an option: ");
    }

    public void displayAccounts(List<Account> accounts) {
        System.out.println("\n=== Your Accounts ===");
        if (accounts.isEmpty()) {
            System.out.println("No accounts found.");
        } else {
            for (Account account : accounts) {
                System.out.println("Account ID: " + account.getIdAccount());
                System.out.println("Type: " + account.getAccountType());
                System.out.println("Balance: " + account.getBalance());
                System.out.println("---------------");
            }
        }
    }

    public void displayTransactions(List<Transaction> transactions) {
        System.out.println("\n=== Transactions History ===");
        if (transactions.isEmpty()) {
            System.out.println("No transactions found.");
        } else {
            for (Transaction t : transactions) {
                System.out.println("ID: " + t.getIdTransaction());
                System.out.println("Type: " + t.getTransactionType());
                System.out.println("Amount: " + t.getAmount());
                System.out.println("Date: " + t.getDate());
                System.out.println("Motif: " + t.getMotif());
                System.out.println("---------------");
            }
        }
    }

    public int getChoice() {
        return Integer.parseInt(scanner.nextLine());
    }

    public void displayPersonalInfo(Client client) {
        System.out.println("\n=== Personal Information ===");
        System.out.println("Client ID: " + client.getIdClient());
        System.out.println("Name: " + client.getFirstName() + " " + client.getLastName());
        System.out.println("Email: " + client.getEmail());
        System.out.println("Number of Accounts: " + client.getAccounts().size());
    }

    public void displayTotals(double totalBalance, double totalDeposits, double totalWithdrawals) {
        System.out.println("\n=== Account Totals ===");
        System.out.println("Total Balance: " + totalBalance);
        System.out.println("Total Deposits: " + totalDeposits);
        System.out.println("Total Withdrawals: " + totalWithdrawals);
    }

    public void pauseForUser() {
        System.out.print("\nPress Enter to continue...");
        scanner.nextLine();
    }
}