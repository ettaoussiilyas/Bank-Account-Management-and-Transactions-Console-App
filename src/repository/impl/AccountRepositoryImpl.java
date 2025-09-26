package repository.impl;

import model.Account;
import model.Client;
import model.enums.AccountType;
import repository.interfaces.AccountRepository;

import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

public class AccountRepositoryImpl implements AccountRepository {
    List<Account> accounts = new ArrayList<>();

    @Override
    public boolean addAccount(int idAccount, double balance, AccountType accountType, Client client){
        Account account = new Account(idAccount, balance, accountType, client);
        accounts.add(account);
        return true;
    }

    @Override
    public boolean removeAccount(int idAccount){
        return accounts.removeIf(account -> account.getIdAccount() == idAccount);
    }

    @Override
    public boolean updateAccount(int idAccount, double balance, AccountType accountType, Client client){
        for(Account account : accounts){
            if(account.getIdAccount() == idAccount){
                account.setBalance(balance);
                account.setAccountType(accountType);
                account.setClient(client);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean addBalance(int idAccount, double amount){
        for(Account account : accounts){
            if(account.getIdAccount() == idAccount){
                account.setBalance(amount + account.getBalance());
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean withdrawBalance(int idAccount, double amount){
        for(Account account : accounts){
            if(account.getIdAccount() == idAccount){
                if(account.getBalance() >= amount){
                    account.setBalance(account.getBalance() - amount);
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public Optional<Client> getClientByAccountId(int idAccount){
        for(Account account : accounts){
            if(account.getIdAccount() == idAccount){
                return Optional.of(account.getClient());
            }
        }
        return Optional.empty();
    }

    @Override
    public AccountType getAccountType(int idAccount){
        for(Account account : accounts){
            if(account.getIdAccount() == idAccount){
                return account.getAccountType();
            }
        }
        return null;
    }

    @Override
    public Optional<Account> getAccountById(int idAccount){
        for(Account account : accounts){
            if(account.getIdAccount() == idAccount){
                return Optional.of(account);
            }
        }
        return Optional.empty();
    }

    @Override
    public List<Account> getAllAccounts(){
        return accounts;
    }

    @Override
    public double getAccountBalance(int idAccount) {
        for (Account account : accounts) {
            if (account.getIdAccount() == idAccount) {
                return account.getBalance();
            }
        }
        return 0;
    }


}
