package model;

import model.Person;
import model.Account;
import java.util.ArrayList;


public class Client extends Person {

    private int idClient;
    private ArrayList<Account> accounts;

    public Client(int idClient, String firstName, String lastName, String email, String password){
        super(firstName, lastName, email, password);
        this.idClient = idClient;
        this.accounts = new ArrayList<Account>();
    }

    public void setIdClient(int idClient){
        this.idClient = idClient;
    }

    public int getIdClient(){
        return this.idClient;
    }

    public ArrayList<Account> getAccounts(){
        return this.accounts;
    }

    public void setAccounts(ArrayList<Account> accounts){
        this.accounts = accounts;
    }

    public void setAccount(Account account){
        this.accounts.add(account);
    }



}
