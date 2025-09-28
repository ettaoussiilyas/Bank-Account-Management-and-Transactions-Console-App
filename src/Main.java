import controller.*;
import model.Client;
import model.Manager;
import model.Person;
import repository.impl.*;
import repository.interfaces.*;
import service.*;
import view.*;

import java.util.Optional;

public class Main {
    public static void main(String[] args) {
        // Initialize repositories
        AuthRepository authRepository = new AuthRepositoryImpl();
        AccountRepository accountRepository = new AccountRepositoryImpl();
        TransactionRepository transactionRepository = new TransactionRepositoryImpl();
        ClientRepository clientRepository = new ClientRepositoryImpl();
        ManagerRepository managerRepository = new ManagerRepositoryImpl();


        // Initialize services
        AuthService authService = new AuthService(authRepository);
        ClientService clientService = new ClientService();
        ManagerService managerService = new ManagerService();
        TransactionService transactionService = new TransactionService();
        AccountService accountService = new AccountService();

        // Initialize controllers
        AuthController authController = new AuthController(authService);
        AccountController accountController = new AccountController(accountService);
        TransactionController transactionController = new TransactionController(transactionService);
        ManagerController managerController = new ManagerController(managerService);

        // Initialize views
        MainMenuView mainMenuView = new MainMenuView();
        LoginView loginView = new LoginView();
        ClientMenuView clientMenuView = new ClientMenuView();
        ManagerMenuView managerMenuView = new ManagerMenuView();
        TransactionView transactionView = new TransactionView();

        boolean running = true;
        while (running) {
            try {
                int choice = mainMenuView.display();

                switch (choice) {
                    case 1:
                        Optional<Person> loggedInUser = authController.handleLogin();
                        if (loggedInUser.isPresent()) {
                            Person user = loggedInUser.get();
                            if (user instanceof Client) {
                                handleClientSession((Client) user, clientMenuView, accountController, transactionController, transactionView);
                            } else if (user instanceof Manager) {
                                handleManagerSession((Manager) user, managerMenuView, managerController, accountController);
                            }
                        }
                        break;

                    case 2:
                        running = false;
                        MessageView.displayInfo("Thank you for using our banking system!");
                        break;

                    default:
                        MessageView.displayError("Invalid option!");
                }
            } catch (Exception e) {
                MessageView.displayError("System error: " + e.getMessage());
            }
        }
    }

    private static void handleClientSession(Client client, ClientMenuView view,
                                            AccountController accountController,
                                            TransactionController transactionController,
                                            TransactionView transactionView) {
        boolean sessionActive = true;
        ClientController clientController = new ClientController(new ClientService(), client.getIdClient());

        while (sessionActive) {
            view.display();
            int choice = view.getChoice();

            switch (choice) {
                case 1: // View Accounts
                    view.displayAccounts(clientController.getClientAccounts());
                    break;
                case 2: // Create Account
                    MessageView.displayInfo("This feature is not available for clients");
                    break;
                case 3: // Delete Account
                    MessageView.displayInfo("This feature is not available for clients");
                    break;
                case 4: // View History
                    view.displayTransactions(clientController.getClientTransactions());
                    break;
                case 5: // Make Transaction
                    handleTransactionMenu(transactionController, transactionView);
                    break;
                case 6: // Logout
                    sessionActive = false;
                    break;
                default:
                    MessageView.displayError("Invalid option!");
            }
        }
    }

    private static void handleManagerSession(Manager manager, ManagerMenuView view,
                                             ManagerController managerController,
                                             AccountController accountController) {
        boolean sessionActive = true;
        TransactionController transactionController = new TransactionController(new TransactionService());

        while (sessionActive) {
            view.display();
            int choice = view.getChoice();

            try {
                switch (choice) {
                    case 1: // View All Clients
                        view.displayClients(managerController.getManagerClients(manager.getIdAdministrator()));
                        break;
                    case 2: // View All Accounts
                        accountController.getAllAccounts().forEach(account ->
                                MessageView.displayInfo(account.toString()));
                        break;
                    case 3: // View All Transactions
                        // Not fully implemented in view yet
                        MessageView.displayInfo("This feature is not fully implemented");
                        break;
                    case 4: // Create Account
                        MessageView.displayInfo("Enter client ID, initial balance, and account type to create an account");
                        break;
                    case 5: // Delete Account
                        MessageView.displayInfo("This feature is not fully implemented");
                        break;
                    case 6: // Update Account
                        MessageView.displayInfo("This feature is not fully implemented");
                        break;
                    case 7: // Logout
                        sessionActive = false;
                        break;
                    default:
                        MessageView.displayError("Invalid option!");
                }
            } catch (Exception e) {
                MessageView.displayError("Operation failed: " + e.getMessage());
            }
        }
    }

    private static void handleTransactionMenu(TransactionController controller, TransactionView view) {
        view.display();
        int choice = view.getChoice();

        try {
            switch (choice) {
                case 1: // Deposit
                    controller.handleDeposit(view.getAccountId(), view.getAmount(), view.getMotif());
                    break;
                case 2: // Withdraw
                    controller.handleWithdrawal(view.getAccountId(), view.getAmount(), view.getMotif());
                    break;
                case 3: // Transfer
                    handleTransfer(controller, view);
                    break;
            }
        } catch (Exception e) {
            MessageView.displayError("Transaction error: " + e.getMessage());
        }
    }

    private static void handleTransfer(TransactionController controller, TransactionView view) {
        try {
            MessageView.displayInfo("Enter source account details:");
            int sourceAccount = view.getAccountId();
            MessageView.displayInfo("Enter destination account details:");
            int destAccount = view.getAccountId();
            double amount = view.getAmount();
            String motif = view.getMotif();

            boolean result = controller.handleTransfer(sourceAccount, destAccount, amount, motif);
            if (result) {
                MessageView.displaySuccess("Transfer completed successfully");
            } else {
                MessageView.displayError("Transfer failed");
            }
        } catch (Exception e) {
            MessageView.displayError("Error during transfer: " + e.getMessage());
        }
    }
}