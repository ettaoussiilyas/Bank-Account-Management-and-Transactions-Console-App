package service;

import model.Client;
import model.Manager;
import model.Account;
import model.Transaction;
import model.enums.AccountType;
import repository.impl.ManagerRepositoryImpl;
import repository.impl.ClientRepositoryImpl;
import repository.impl.AccountRepositoryImpl;
import repository.impl.TransactionRepositoryImpl;
import repository.interfaces.ManagerRepository;
import repository.interfaces.ClientRepository;
import repository.interfaces.AccountRepository;
import repository.interfaces.TransactionRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

public class ManagerService {
    private final ManagerRepository managerRepository;
    private final ClientRepository clientRepository;
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    public ManagerService() {
        this.managerRepository = new ManagerRepositoryImpl();
        this.clientRepository = new ClientRepositoryImpl();
        this.accountRepository = new AccountRepositoryImpl();
        this.transactionRepository = new TransactionRepositoryImpl();
    }

    public boolean addManager(int idManager, String firstName, String lastName, String email, String password, String department) {
        if (firstName == null || lastName == null || email == null || password == null || department == null) {
            throw new IllegalArgumentException("All fields are required");
        }
        return managerRepository.addManager(idManager, firstName, lastName, email, password, department);
    }

    public Optional<Manager> getManagerById(String managerId) {
        if (managerId == null) {
            throw new IllegalArgumentException("Manager ID cannot be null");
        }
        return managerRepository.getManagerById(managerId);
    }

    public boolean createClient(String firstName, String lastName, String email, String password, int managerId) {
        if (firstName == null || lastName == null || email == null || password == null) {
            throw new IllegalArgumentException("All client fields are required");
        }

        Optional<Manager> managerOpt = managerRepository.getManagerById(String.valueOf(managerId));
        if (managerOpt.isEmpty()) {
            throw new NoSuchElementException("Manager not found");
        }

        int newClientId = (int) (Math.random() * 100000);
        boolean clientCreated = clientRepository.addClient(newClientId, firstName, lastName, email, password, managerOpt.get());
        
        if (clientCreated) {
            return managerRepository.addClientToManager(managerId, newClientId);
        }
        return false;
    }

    public boolean updateClient(int clientId, String firstName, String lastName, String email, String password) {
        if (firstName == null || lastName == null || email == null || password == null) {
            throw new IllegalArgumentException("All fields are required");
        }

        Optional<Client> client = clientRepository.getClientById(clientId);
        if (client.isEmpty()) {
            throw new NoSuchElementException("Client not found");
        }

        return clientRepository.updateClient(clientId, firstName, lastName, email, password);
    }

    public boolean createAccount(int clientId, double initialBalance, AccountType accountType) {
        if (initialBalance < 0 || accountType == null) {
            throw new IllegalArgumentException("Invalid account parameters");
        }

        Optional<Client> clientOpt = clientRepository.getClientById(clientId);
        if (clientOpt.isEmpty()) {
            throw new NoSuchElementException("Client not found");
        }

        int newAccountId = (int) (Math.random() * 100000);
        Account newAccount = new Account(newAccountId, initialBalance, accountType, clientOpt.get());
        
        boolean accountCreated = accountRepository.addAccount(newAccountId, initialBalance, accountType, clientOpt.get());
        if (accountCreated) {
            return clientRepository.addAccountToClient(clientId, newAccount);
        }
        return false;
    }

    public List<Client> getManagerClients(int managerId) {
        return managerRepository.getClientsByManagerId(managerId);
    }

    public List<Manager> getAllManagers() {
        return managerRepository.getAllManagers();
    }

}