package repository.interfaces;

import model.Account;
import model.Client;
import model.enums.AccountType;
import java.util.List;
import java.util.Optional;

public interface AccountRepository {
    public boolean addAccount(int idAccount, double balance, AccountType accountType, Client client);
    public boolean removeAccount(int idAccount);
    public boolean updateAccount(int idAccount, double balance, AccountType accountType, Client client);
    public boolean addBalance(int idAccount, double amount);
    public boolean withdrawBalance(int idAccount, double amount);
    public boolean transferBalance(int fromAccountId, int toAccountId, double amount);
    public Optional<Client> getClientByAccountId(int idAccount);
    public AccountType getAccountType(int idAccount);
    public Optional<Account> getAccountById(int idAccount);
    public List<Account> getAllAccounts();
    public double getAccountBalance(int idAccount);

}
