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
        if (email == null || email.trim().isEmpty() || password == null || password.trim().isEmpty()) {
            throw new IllegalArgumentException("Email and password cannot be empty");
        }

        return authRepository.login(email, password);
    }

    public boolean isEmailAvailable(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email cannot be empty");
        }

        return !authRepository.isEmailExists(email);
    }

}