package model;

import java.util.List;
import java.util.ArrayList;

public class Manager extends Person {

    private int idManager;
    private String department;
    private List<Client> clients;

    public Manager(int idManager, String firstName, String lastName, String email, String password, String department) {
        super(firstName, lastName, email, password);
        this.idManager = idManager;
        this.clients = new ArrayList<Client>();
        this.department = department;
    }

    public void setIdAdministrator(int idManager){
        this.idManager = idManager;
    }

    public int getIdAdministrator(){
        return this.idManager;
    }

    public List<Client> getClients(){
        return this.clients;
    }

    public void setClients(List<Client> clients){
        this.clients = clients;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }


}
