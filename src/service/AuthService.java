package service;

import model.Person;
import repository.interfaces.AuthRepository;
import repository.impl.AuthRepositoryImpl;
import java.util.Optional;

public class AuthService {
    private final AuthRepository authRepository;

    public AuthService(AuthRepository authRepository) {
        this.authRepository = authRepository;
    }

    public Optional<Person> login(String email, String password) {

        return authRepository.login(email, password);
    }

    public boolean isEmailAvailable(String email) {

        return !authRepository.isEmailExists(email);
    }

}