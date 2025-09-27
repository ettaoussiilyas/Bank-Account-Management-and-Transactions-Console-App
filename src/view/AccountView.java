package view;

import java.util.Scanner;

public class AccountView {
    Scanner scanner = new Scanner(System.in);
    //display account menu
    public int displayAccountMenu(){
        System.out.println("1. Create Account");
        System.out.println("2. View Accounts");
        System.out.println("3. Delete Account");
        System.out.println("4. Back to Main Menu");
        System.out.print("Choose an option: ");
        int option = scanner.nextInt();
        return option;
    }
}
