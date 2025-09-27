package view;

import view.interfaces.View;

import java.util.Scanner;

public class TransactionView implements View {
    private final Scanner scanner = new Scanner(System.in);

    @Override
    public void display() {
        System.out.println("=== Transaction Menu ===");
        System.out.println("1. Deposit");
        System.out.println("2. Withdraw");
        System.out.println("3. Transfer");
        System.out.println("4. Back");
    }

    public double getAmount() {
        System.out.print("Enter amount: ");
        return Double.parseDouble(scanner.nextLine());
    }

    public String getMotif() {
        System.out.print("Enter description: ");
        return scanner.nextLine();
    }

    public int getAccountId() {
        System.out.print("Enter Account ID: ");
        return Integer.parseInt(scanner.nextLine());
    }

    public int getChoice() {
        return Integer.parseInt(scanner.nextLine());
    }
}