package repository.impl;

import model.Person;
import model.Client;
import model.Manager;
import repository.interfaces.AuthRepository;
import repository.interfaces.ClientRepository;
import repository.interfaces.ManagerRepository;
import java.util.Optional;
import java.util.List;

public class AuthRepositoryImpl implements AuthRepository {
    private final ClientRepository clientRepository;
    private final ManagerRepository managerRepository;

    public AuthRepositoryImpl(ClientRepository clientRepository, ManagerRepository managerRepository) {
        this.clientRepository = clientRepository;
        this.managerRepository = managerRepository;
    }

    @Override
    public Optional<Person> login(String email, String password) {
        // Check among all clients
        List<Client> clients = clientRepository.getAllClients();
        for (Client client : clients) {
            if (client.getEmail().equals(email) && client.getPassword().equals(password)) {
                return Optional.of(client);
            }
        }

        List<Manager> managers = managerRepository.getAllManagers();
        for (Manager manager : managers) {
            if (manager.getEmail().equals(email) && manager.getPassword().equals(password)) {
                return Optional.of(manager);
            }
        }

        return Optional.empty();
    }

    @Override
    public boolean isEmailExists(String email) {
        List<Client> clients = clientRepository.getAllClients();
        for (Client client : clients) {
            if (client.getEmail().equals(email)) {
                return true;
            }
        }

        List<Manager> managers = managerRepository.getAllManagers();
        for (Manager manager : managers) {
            if (manager.getEmail().equals(email)) {
                return true;
            }
        }

        return false;
    }
}