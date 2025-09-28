package controller;

import model.Client;
import model.Manager;
import model.enums.AccountType;
import service.ManagerService;
import view.MessageView;

import java.util.List;
import java.util.Optional;

public class ManagerController {
    private final ManagerService managerService;

    public ManagerController(ManagerService managerService) {
        this.managerService = managerService;
    }

    public boolean createClient(String firstName, String lastName, String email, String password, int managerId) {
        try {
            return managerService.createClient(firstName, lastName, email, password, managerId);
        } catch (Exception e) {
            MessageView.displayError("Error creating client: " + e.getMessage());
            return false;
        }
    }

    public boolean createAccount(int clientId, double initialBalance, AccountType accountType) {
        try {
            return managerService.createAccount(clientId, initialBalance, accountType);
        } catch (Exception e) {
            MessageView.displayError("Error creating account: " + e.getMessage());
            return false;
        }
    }

    public List<Client> getManagerClients(int managerId) {
        return managerService.getManagerClients(managerId);
    }

    public List<Manager> getAllManagers() {
        return managerService.getAllManagers();
    }

    public boolean updateClient(int clientId, String firstName, String lastName, String email, String password) {
        try {
            return managerService.updateClient(clientId, firstName, lastName, email, password);
        } catch (Exception e) {
            MessageView.displayError("Error updating client: " + e.getMessage());
            return false;
        }
    }
}