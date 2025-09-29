package repository.impl;

import model.Account;
import model.Client;
import model.Manager;
import repository.interfaces.ClientRepository;
import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

public class ClientRepositoryImpl implements ClientRepository {

    private static final List<Client> clients = new ArrayList<>();

    public ClientRepositoryImpl() {
    }

    @Override
    public boolean addClient(int idClient, String firstName, String lastName, String email, String password, Manager manager) {
        Client client = new Client(idClient, firstName, lastName, email, password);
        return clients.add(client);
    }


    @Override
    public boolean removeClient(int idClient){
        boolean removed = clients.removeIf(client -> client.getIdClient() == idClient);
        return removed;
    }

    @Override
    public boolean updateClient(int idClient, String firstName, String lastName, String email, String password){
        for(Client client : clients){
            if(client.getIdClient() == idClient){
                client.setFirstName(firstName);
                client.setLastName(lastName);
                client.setEmail(email);
                client.setPassword(password);
            }
        }
        return true;
    }

    @Override
    public Optional<Client> getClientById(int idClient){
        for(Client client : clients){
            if(client.getIdClient() == idClient){
                return Optional.of(client);
            }
        }
        return Optional.empty();
    }

    @Override
    public Optional<Client> getClientByEmail(String email){
        for(Client client : clients){
            if(client.getEmail().equals(email)){
                return Optional.of(client);
            }
        }
        return Optional.empty();
    }

    @Override
    public List<Client> getAllClients(){
        return clients;
    }

    @Override
    public List<Account> getAccountsByClientId(int idClient){
        for(Client client : clients){
            if(client.getIdClient() == idClient){
                return client.getAccounts();
            }
        }
        return null;
    }

    @Override
    public boolean addAccountToClient(int idClient, Account account){
        for(Client client : clients){
            if(client.getIdClient() == idClient){
                client.setAccount(account);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean removeAccountFromClient(int idClient, int accountNumber){
        for(Client client : clients){
            if(client.getIdClient() == idClient){
                List <Account> accounts = client.getAccounts();
                boolean removed = accounts.removeIf(account -> account.getIdAccount() == accountNumber);
                client.setAccounts((ArrayList<Account>) accounts);
                return removed;
            }
        }
        return false;
    }















}
