package repository.interfaces;

import model.Manager;
import java.util.List;

import java.util.Optional;

public interface ManagerRepository {

    public boolean addManager(int idManager, String firstName, String lastName, String email, String password, String department);
    public boolean removeManager(String idManager);
    public boolean updateManager(String idManager, String firstName, String lastName, String email, String password);
    public Optional<Manager> getManagerById(String idManager);
    public Optional<Manager> getManagerByEmail(String email);
    public List<Manager> getAllManagers();
    public List<Manager> getClientsByManagerId(int idManager);
    public boolean addClientToManager(int idManager, int idClient);
    public boolean removeClientFromManager(int idManager, int idClient);

}
