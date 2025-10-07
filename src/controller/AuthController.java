package controller;

import model.Person;
import service.AuthService;
import view.LoginView;
import view.MessageView;

import java.util.Optional;

public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    public Optional<Person> handleLogin(LoginView loginView) {
        System.out.println("\nPlease enter your credentials:");
        String[] credentials = loginView.getCredentials();
        Optional<Person> loggedInUser = authService.login(credentials[0], credentials[1]);

        if (loggedInUser.isPresent()) {
            MessageView.displaySuccess("Login successful!");
        } else {
            MessageView.displayError("Invalid credentials!");
        }

        return loggedInUser;
    }
}