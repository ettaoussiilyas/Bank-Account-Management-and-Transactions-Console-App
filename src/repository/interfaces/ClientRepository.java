package repository.interfaces;

import model.Account;
import model.Client;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public interface ClientRepository {

    public boolean addClient(String idClient, String firstName, String lastName, String email, String password);
    public boolean removeClient(String idClient);
    public boolean updateClient(String idClient, String firstName, String lastName, String email, String password);
    public Optional<Client> getClientById(String idClient);
    public Optional<Client> getClientByEmail(String email);
    public List<Client> getAllClients();
    public List<Account> getAccountsByClientId(String idClient);
    public boolean addAccountToClient(String idClient, Account account);
    public boolean removeAccountFromClient(String idClient, String accountNumber);
}
