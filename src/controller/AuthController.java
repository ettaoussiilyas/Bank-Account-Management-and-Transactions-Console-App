package controller;

import model.Person;
import service.AuthService;
import view.LoginView;
import view.MessageView;

import java.util.Optional;

public class AuthController {
    private final AuthService authService;
    private final LoginView loginView;

    public AuthController(AuthService authService) {
        this.authService = authService;
        this.loginView = new LoginView();
    }

    public Optional<Person> handleLogin() {
        loginView.display();
        int choice = loginView.getChoice();
        
        if (choice == 1) {
            String[] credentials = loginView.getCredentials();
            Optional<Person> loggedInUser = authService.login(credentials[0], credentials[1]);

            if (loggedInUser.isPresent()) {
                MessageView.displaySuccess("Login successful!");
            } else {
                MessageView.displayError("Invalid credentials!");
            }

            return loggedInUser;
        } else {
            return Optional.empty();
        }
    }
}