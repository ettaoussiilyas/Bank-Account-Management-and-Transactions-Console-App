import controller.*;
import model.Client;
import model.Manager;
import model.Person;
import model.Transaction;
import repository.impl.*;
import repository.interfaces.*;
import service.*;
import view.*;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

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
                        Optional<Person> loggedInUser = authController.handleLogin(loginView);
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
                case 1: // View Personal Information
                    view.displayPersonalInfo(client);
                    view.pauseForUser();
                    break;
                case 2: // View Accounts
                    view.displayAccounts(clientController.getClientAccounts());
                    view.pauseForUser();
                    break;
                case 3: // Delete Account
                    handleClientDeleteAccount(view, clientController);
                    break;
                case 4: // View History
                    view.displayTransactions(clientController.getClientTransactions());
                    view.pauseForUser();
                    break;
                case 5: // Filter Transactions
                    handleFilterTransactions(view, clientController);
                    break;
                case 6: // Calculate Totals
                    handleCalculateTotals(view, clientController);
                    break;
                case 7: // Make Transaction
                    handleTransactionMenu(transactionController, transactionView);
                    break;
                case 8: // Logout
                    sessionActive = false;
                    break;
                default:
                    MessageView.displayError("Invalid option!");
                    view.pauseForUser();
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
                        view.pauseForUser();
                        break;
                    case 2: // Create Client
                        handleCreateClient(view, managerController, manager.getIdAdministrator());
                        break;
                    case 3: // Delete Client
                        handleDeleteClient(view, managerController);
                        break;
                    case 4: // View All Accounts
                        handleViewAllAccounts(accountController);
                        view.pauseForUser();
                        break;
                    case 5: // Create Account
                        handleCreateAccount(view, managerController);
                        break;
                    case 6: // Delete Account
                        handleDeleteAccount(view, managerController);
                        break;
                    case 7: // View All Transactions
                        view.displayTransactions(managerController.getAllTransactions());
                        view.pauseForUser();
                        break;
                    case 8: // Logout
                        sessionActive = false;
                        break;
                    default:
                        MessageView.displayError("Invalid option!");
                        view.pauseForUser();
                }
            } catch (Exception e) {
                MessageView.displayError("Operation failed: " + e.getMessage());
                view.pauseForUser();
            }
        }
    }

    private static void handleTransactionMenu(TransactionController controller, TransactionView view) {
        view.display();
        int choice = view.getChoice();

        try {
            switch (choice) {
                case 1: // Deposit
                    boolean depositResult = controller.handleDeposit(view.getAccountId(), view.getAmount(), view.getMotif());
                    if (depositResult) {
                        MessageView.displaySuccess("Deposit completed successfully!");
                    } else {
                        MessageView.displayError("Deposit failed!");
                    }
                    break;
                case 2: // Withdraw
                    boolean withdrawResult = controller.handleWithdrawal(view.getAccountId(), view.getAmount(), view.getMotif());
                    if (withdrawResult) {
                        MessageView.displaySuccess("Withdrawal completed successfully!");
                    } else {
                        MessageView.displayError("Withdrawal failed!");
                    }
                    break;
                case 3: // Transfer
                    handleTransfer(controller, view);
                    break;
                case 4: // Back
                    return;
                default:
                    MessageView.displayError("Invalid option!");
            }
        } catch (Exception e) {
            MessageView.displayError("Transaction error: " + e.getMessage());
        }
        view.pauseForUser();
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

    private static void handleCreateClient(ManagerMenuView view, ManagerController controller, int managerId) {
        try {
            System.out.println("\n=== Create New Client ===");
            String[] clientInfo = view.getClientInfo();
            boolean success = controller.createClient(clientInfo[0], clientInfo[1], clientInfo[2], clientInfo[3], managerId);
            if (success) {
                MessageView.displaySuccess("Client created successfully!");
            } else {
                MessageView.displayError("Failed to create client!");
            }
        } catch (Exception e) {
            MessageView.displayError("Error creating client: " + e.getMessage());
        }
        view.pauseForUser();
    }

    private static void handleDeleteClient(ManagerMenuView view, ManagerController controller) {
        try {
            System.out.println("\n=== Delete Client ===");
            int clientId = view.getClientId();
            boolean success = controller.deleteClient(clientId);
            if (success) {
                MessageView.displaySuccess("Client deleted successfully!");
            } else {
                MessageView.displayError("Failed to delete client!");
            }
        } catch (Exception e) {
            MessageView.displayError("Error deleting client: " + e.getMessage());
        }
        view.pauseForUser();
    }

    private static void handleCreateAccount(ManagerMenuView view, ManagerController controller) {
        try {
            System.out.println("\n=== Create New Account ===");
            int clientId = view.getClientId();
            double initialBalance = view.getInitialBalance();
            
            view.displayAccountTypes();
            int accountTypeChoice = view.getChoice();
            
            model.enums.AccountType accountType;
            switch (accountTypeChoice) {
                case 1:
                    accountType = model.enums.AccountType.COURANT;
                    break;
                case 2:
                    accountType = model.enums.AccountType.EPARGNE;
                    break;
                case 3:
                    accountType = model.enums.AccountType.SALAIRE;
                    break;
                case 4:
                    accountType = model.enums.AccountType.DEPOTATERME;
                    break;
                default:
                    MessageView.displayError("Invalid account type!");
                    return;
            }
            
            boolean success = controller.createAccount(clientId, initialBalance, accountType);
            if (success) {
                MessageView.displaySuccess("Account created successfully!");
            } else {
                MessageView.displayError("Failed to create account!");
            }
        } catch (Exception e) {
            MessageView.displayError("Error creating account: " + e.getMessage());
        }
        view.pauseForUser();
    }

    private static void handleViewAllAccounts(AccountController controller) {
        System.out.println("\n=== All Accounts ===");
        var accounts = controller.getAllAccounts();
        if (accounts.isEmpty()) {
            System.out.println("No accounts found.");
        } else {
            for (var account : accounts) {
                System.out.println("Account ID: " + account.getIdAccount());
                System.out.println("Type: " + account.getAccountType());
                System.out.println("Balance: " + account.getBalance());
                System.out.println("Owner: " + account.getClient().getFirstName() + " " + account.getClient().getLastName());
                System.out.println("---------------");
            }
        }
    }

    private static void handleDeleteAccount(ManagerMenuView view, ManagerController controller) {
        try {
            System.out.println("\n=== Delete Account ===");
            int accountId = view.getAccountId();
            boolean success = controller.deleteAccount(accountId);
            if (success) {
                MessageView.displaySuccess("Account deleted successfully!");
            } else {
                MessageView.displayError("Failed to delete account!");
            }
        } catch (Exception e) {
            MessageView.displayError("Error deleting account: " + e.getMessage());
        }
        view.pauseForUser();
    }

    private static void handleClientDeleteAccount(ClientMenuView view, ClientController controller) {
        try {
            System.out.println("\n=== Delete Account ===");
            System.out.print("Enter Account ID to delete: ");
            Scanner scanner = new Scanner(System.in);
            int accountId = Integer.parseInt(scanner.nextLine());
            
            boolean success = controller.deleteAccount(accountId);
            if (success) {
                MessageView.displaySuccess("Account deleted successfully!");
            } else {
                MessageView.displayError("Failed to delete account or account not found!");
            }
        } catch (Exception e) {
            MessageView.displayError("Error deleting account: " + e.getMessage());
        }
        view.pauseForUser();
    }

    private static void handleCalculateTotals(ClientMenuView view, ClientController controller) {
        try {
            ClientService clientService = new ClientService();
            int clientId = controller.getClientAccounts().get(0).getClient().getIdClient();
            
            double totalBalance = clientService.calculateTotalBalance(clientId);
            double totalDeposits = clientService.calculateTotalDeposits(clientId);
            double totalWithdrawals = clientService.calculateTotalWithdrawals(clientId);
            
            view.displayTotals(totalBalance, totalDeposits, totalWithdrawals);
        } catch (Exception e) {
            MessageView.displayError("Error calculating totals: " + e.getMessage());
        }
        view.pauseForUser();
    }

    private static void handleFilterTransactions(ClientMenuView view, ClientController controller) {
        try {
            int filterChoice = view.getFilterChoice();
            List<Transaction> filteredTransactions = null;

            switch (filterChoice) {
                case 1: // Filter by Type
                    int typeChoice = view.getTransactionTypeChoice();
                    model.enums.TransactionType transactionType;
                    switch (typeChoice) {
                        case 1:
                            transactionType = model.enums.TransactionType.DEPOT;
                            break;
                        case 2:
                            transactionType = model.enums.TransactionType.RETRAIT;
                            break;
                        case 3:
                            transactionType = model.enums.TransactionType.VIREMENT;
                            break;
                        default:
                            MessageView.displayError("Invalid transaction type!");
                            return;
                    }
                    filteredTransactions = controller.filterTransactionsByType(transactionType);
                    break;
                case 2: // Filter by Amount Range
                    double minAmount = view.getMinAmount();
                    double maxAmount = view.getMaxAmount();
                    filteredTransactions = controller.filterTransactionsByAmount(minAmount, maxAmount);
                    break;
                case 3: // Filter by Date Range
                    MessageView.displayInfo("Date filtering not yet implemented");
                    view.pauseForUser();
                    return;
                case 4: // Back
                    return;
                default:
                    MessageView.displayError("Invalid option!");
                    return;
            }

            if (filteredTransactions != null) {
                view.displayTransactions(filteredTransactions);
            }
        } catch (Exception e) {
            MessageView.displayError("Error filtering transactions: " + e.getMessage());
        }
        view.pauseForUser();
    }
}