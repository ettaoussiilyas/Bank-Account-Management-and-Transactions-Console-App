package repository.impl;

import model.Manager;
import model.Client;
import repository.interfaces.ManagerRepository;
import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

public class ManagerRepositoryImpl implements ManagerRepository {
    private static final List<Manager> managers = new ArrayList<>();

    static {
        Manager defaultManager = new Manager(1, "Manager", "Manager", "manager@bank.com", "manager123", "IT");
        managers.add(defaultManager);
    }

    public ManagerRepositoryImpl() {
    }

    @Override
    public boolean addManager(int idManager, String firstName, String lastName, String email, String password, String department) {
        if (getManagerByEmail(email).isPresent()) {
            return false;
        }
        Manager manager = new Manager(idManager, firstName, lastName, email, password, department);
        return managers.add(manager);
    }

    @Override
    public boolean removeManager(String idManager) {
        return managers.removeIf(manager -> String.valueOf(manager.getIdAdministrator()).equals(idManager));
    }

    @Override
    public boolean updateManager(String idManager, String firstName, String lastName, String email, String password) {
        Optional<Manager> managerOpt = getManagerById(idManager);
        if (managerOpt.isEmpty()) {
            return false;
        }
        Manager manager = managerOpt.get();
        manager.setFirstName(firstName);
        manager.setLastName(lastName);
        manager.setEmail(email);
        manager.setPassword(password);
        return true;
    }

    @Override
    public Optional<Manager> getManagerById(String idManager) {
        return managers.stream()
                .filter(manager -> String.valueOf(manager.getIdAdministrator()).equals(idManager))
                .findFirst();
    }

    @Override
    public Optional<Manager> getManagerByEmail(String email) {
        return managers.stream()
                .filter(manager -> manager.getEmail().equals(email))
                .findFirst();
    }

    @Override
    public List<Manager> getAllManagers() {
        return new ArrayList<>(managers);
    }

    @Override
    public List<Client> getClientsByManagerId(int idManager) {
        Optional<Manager> managerOpt = managers.stream()
                .filter(manager -> manager.getIdAdministrator() == idManager)
                .findFirst();
        
        if (managerOpt.isEmpty()) {
            return new ArrayList<>();
        }

        return managerOpt.get().getClients();
    }

    @Override
    public boolean addClientToManager(int idManager, int idClient) {
        // This method will be handled by the service layer
        return true;
    }

    @Override
    public boolean removeClientFromManager(int idManager, int idClient) {
        Optional<Manager> managerOpt = managers.stream()
                .filter(manager -> manager.getIdAdministrator() == idManager)
                .findFirst();

        if (managerOpt.isEmpty()) {
            return false;
        }

        return managerOpt.get().getClients()
                .removeIf(client -> client.getIdClient() == idClient);
    }
}