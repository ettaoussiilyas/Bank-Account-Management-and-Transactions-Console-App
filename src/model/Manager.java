package model;

import java.util.List;
import java.util.ArrayList;

public class Manager extends Person {

    private int idAdministrator;
    private List<Client> clients;

    public Manager(int idAdministrator, String firstName, String lastName, String email, String password){
        super(firstName, lastName, email, password);
        this.idAdministrator = idAdministrator;
        this.clients = new ArrayList<Client>();
    }

    public void setIdAdministrator(int idAdministrator){
        this.idAdministrator = idAdministrator;
    }

    public int getIdAdministrator(){
        return this.idAdministrator;
    }

    public List<Client> getClients(){
        return this.clients;
    }

    public void setClients(List<Client> clients){
        this.clients = clients;
    }


}
