package repository.interfaces;

import model.Account;
import model.Client;
import model.Manager;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public interface ClientRepository {

    public boolean addClient(int idClient, String firstName, String lastName, String email, String password, Manager manager);
    public boolean removeClient(int idClient);
    public boolean updateClient(int idClient, String firstName, String lastName, String email, String password);
    public Optional<Client> getClientById(int idClient);
    public Optional<Client> getClientByEmail(String email);
    public List<Client> getAllClients();
    public List<Account> getAccountsByClientId(int idClient);
    public boolean addAccountToClient(int idClient, Account account);
    public boolean removeAccountFromClient(int idClient, int accountNumber);
}
