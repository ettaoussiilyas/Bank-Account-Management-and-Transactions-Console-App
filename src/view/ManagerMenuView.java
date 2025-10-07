package view;

import model.Client;
import model.Transaction;
import view.interfaces.View;

import java.util.List;
import java.util.Scanner;

public class ManagerMenuView implements View {
    private final Scanner scanner = new Scanner(System.in);

    @Override
    public void display() {
        System.out.println("\n=== Manager Menu ===");
        System.out.println("1. View All Clients");
        System.out.println("2. Create Client");
        System.out.println("3. Delete Client");
        System.out.println("4. View All Accounts");
        System.out.println("5. Create Account");
        System.out.println("6. Delete Account");
        System.out.println("7. View All Transactions");
        System.out.println("8. Logout");
        System.out.print("Choose an option: ");
    }

    public void displayClients(List<Client> clients) {
        System.out.println("\n=== Clients List ===");
        if (clients.isEmpty()) {
            System.out.println("No clients found.");
        } else {
            for (Client client : clients) {
                System.out.println("ID: " + client.getIdClient());
                System.out.println("Name: " + client.getFirstName() + " " + client.getLastName());
                System.out.println("Email: " + client.getEmail());
                System.out.println("---------------");
            }
        }
    }

    public int getChoice() {
        return Integer.parseInt(scanner.nextLine());
    }

    public String[] getClientInfo() {
        System.out.print("First Name: ");
        String firstName = scanner.nextLine();
        System.out.print("Last Name: ");
        String lastName = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();
        return new String[]{firstName, lastName, email, password};
    }

    public int getClientId() {
        System.out.print("Enter Client ID: ");
        return Integer.parseInt(scanner.nextLine());
    }

    public void displayAccountTypes() {
        System.out.println("\n=== Account Types ===");
        System.out.println("1. COURANT (Current Account)");
        System.out.println("2. EPARGNE (Savings Account)");
        System.out.println("3. SALAIRE (Salary Account)");
        System.out.println("4. DEPOTATERME (Term Deposit)");
        System.out.print("Choose account type: ");
    }

    public double getInitialBalance() {
        System.out.print("Enter initial balance: ");
        return Double.parseDouble(scanner.nextLine());
    }

    public int getAccountId() {
        System.out.print("Enter Account ID: ");
        return Integer.parseInt(scanner.nextLine());
    }

    public void displayTransactions(List<Transaction> transactions) {
        System.out.println("\n=== All Transactions ===");
        if (transactions.isEmpty()) {
            System.out.println("No transactions found.");
        } else {
            for (Transaction t : transactions) {
                System.out.println("ID: " + t.getIdTransaction());
                System.out.println("Type: " + t.getTransactionType());
                System.out.println("Amount: " + t.getAmount());
                System.out.println("Date: " + t.getDate());
                System.out.println("Motif: " + t.getMotif());
                if (t.getAccount() != null) {
                    System.out.println("Source Account: " + t.getAccount().getIdAccount());
                }
                if (t.getAccountDestination() != null) {
                    System.out.println("Destination Account: " + t.getAccountDestination().getIdAccount());
                }
                System.out.println("---------------");
            }
        }
    }

    public void pauseForUser() {
        System.out.print("\nPress Enter to continue...");
        scanner.nextLine();
    }
}