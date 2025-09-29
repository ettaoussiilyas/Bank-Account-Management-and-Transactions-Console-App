package view;


import view.interfaces.View;

import java.util.Scanner;

public class LoginView implements View {
    private final Scanner scanner = new Scanner(System.in);

    @Override
    public void display() {
        System.out.println("\n=== Login System ===");
        System.out.println("1. Login");
        System.out.println("2. Back to Main Menu");
        System.out.print("Choose an option: ");
    }

    public String[] getCredentials() {
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();
        return new String[]{email, password};
    }

    public int getChoice() {
        return Integer.parseInt(scanner.nextLine());
    }


}