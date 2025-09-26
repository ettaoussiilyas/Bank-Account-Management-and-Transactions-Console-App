package repository.impl;

import model.Manager;
import model.Client;
import repository.interfaces.ManagerRepository;
import repository.interfaces.ClientRepository;

import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

public class ManagerRepositoryImpl implements ManagerRepository {
    private final List<Manager> managers = new ArrayList<>();
    private final ClientRepository clientRepository;

    public ManagerRepositoryImpl() {
        this.clientRepository = new ClientRepositoryImpl();
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

        List<Client> clients = new ArrayList<>();
        for (Client client : managerOpt.get().getClients()) {
            clientRepository.getClientById(client.getIdClient())
                    .ifPresent(clients::add);
        }
        return clients;
    }

    @Override
    public boolean addClientToManager(int idManager, int idClient) {
        Optional<Manager> managerOpt = managers.stream()
                .filter(manager -> manager.getIdAdministrator() == idManager)
                .findFirst();

        if (managerOpt.isEmpty()) {
            return false;
        }

        Optional<Client> clientOpt = clientRepository.getClientById(idClient);
        if (clientOpt.isEmpty()) {
            return false;
        }

        Manager manager = managerOpt.get();
        if (manager.getClients().stream()
                .anyMatch(client -> client.getIdClient() == idClient)) {
            return false;
        }

        return manager.getClients().add(clientOpt.get());
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