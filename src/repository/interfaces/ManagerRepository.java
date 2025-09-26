package repository.interfaces;

import model.Client;
import model.Manager;
import java.util.List;
import java.util.Optional;

public interface ManagerRepository {
    boolean addManager(int idManager, String firstName, String lastName, String email, String password, String department);
    boolean removeManager(String idManager);
    boolean updateManager(String idManager, String firstName, String lastName, String email, String password);
    Optional<Manager> getManagerById(String idManager);
    Optional<Manager> getManagerByEmail(String email);
    List<Manager> getAllManagers();
    List<Client> getClientsByManagerId(int idManager);
    boolean addClientToManager(int idManager, int idClient);
    boolean removeClientFromManager(int idManager, int idClient);
}