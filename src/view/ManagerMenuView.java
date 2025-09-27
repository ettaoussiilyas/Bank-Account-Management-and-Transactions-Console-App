package view;

import model.Client;
import view.interfaces.View;

import java.util.List;
import java.util.Scanner;

public class ManagerMenuView implements View {
    private final Scanner scanner = new Scanner(System.in);

    @Override
    public void display() {
        System.out.println("=== Manager Menu ===");
        System.out.println("1. View All Clients");
        System.out.println("2. View All Accounts");
        System.out.println("3. View All Transactions");
        System.out.println("4. Create Account");
        System.out.println("5. Delete Account");
        System.out.println("6. Update Account");
        System.out.println("7. Logout");
    }

    public void displayClients(List<Client> clients) {
        System.out.println("=== Clients List ===");
        for (Client client : clients) {
            System.out.println("ID: " + client.getIdClient());
            System.out.println("Name: " + client.getFirstName() + " " + client.getLastName());
            System.out.println("Email: " + client.getEmail());
            System.out.println("---------------");
        }
    }

    public int getChoice() {
        return Integer.parseInt(scanner.nextLine());
    }
}