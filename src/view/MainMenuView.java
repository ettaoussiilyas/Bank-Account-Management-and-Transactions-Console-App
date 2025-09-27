package view;

import java.util.Scanner;

public class MainMenuView {

    Scanner scanner = new Scanner(System.in);

    private final ClientMenuView clientMenuView;
    private final ManagerMenuView managerMenuView;


    public MainMenuView() {
        this.clientMenuView = new ClientMenuView();
        this.managerMenuView = new ManagerMenuView();
    }



    public int display() {
        System.out.println("=== Main Menu ===");
        System.out.println("1. Login");
        System.out.println("2. Exit");
        System.out.print("Select an option: ");
        return scanner.nextInt();
    }

}
