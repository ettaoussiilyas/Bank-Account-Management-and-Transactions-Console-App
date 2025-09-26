package repository.interfaces;

import model.Person;
import java.util.Optional;

public interface AuthRepository {
    Optional<Person> login(String email, String password);
    boolean isEmailExists(String email);
}