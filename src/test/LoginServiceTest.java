package test;

import dto.UserDTO;
import enums.Role;
import exception.IncorrectPasswordException;
import exception.UserAlreadyExistsException;
import exception.UserNotFoundException;
import repository.UserRepository;
import service.LoginService;

import java.io.IOException;

public class LoginServiceTest {
    public static void main(String[] args) {
        try {
            // Initialize repository and service
            String filePath = "data/users.json"; // Adjust the file path if necessary
            UserRepository userRepository = new UserRepository(filePath);
            LoginService loginService = new LoginService(userRepository);

            // Test 1: Register a new user
            System.out.println("=== Test 1: Register a New User ===");
            try {
                UserDTO newUser = loginService.register("John Doe", "john.doe@example.com", "password123", Role.ATTENDEE);
                System.out.println("Successfully registered: " + newUser);
            } catch (UserAlreadyExistsException e) {
                System.err.println(e.getMessage());
            }

            // Test 2: Attempt duplicate registration
            System.out.println("\n=== Test 2: Duplicate Registration ===");
            try {
                loginService.register("John Doe", "john.doe@example.com", "password123", Role.ATTENDEE);
            } catch (UserAlreadyExistsException e) {
                System.err.println(e.getMessage());
            }

            // Test 3: Successful login
            System.out.println("\n=== Test 3: Successful Login ===");
            try {
                UserDTO loggedInUser = loginService.logIn("john.doe@example.com", "password123");
                System.out.println("Successfully logged in: " + loggedInUser);
            } catch (UserNotFoundException | IncorrectPasswordException e) {
                System.err.println(e.getMessage());
            }

            // Test 4: Login with incorrect password
            System.out.println("\n=== Test 4: Login with Incorrect Password ===");
            try {
                loginService.logIn("john.doe@example.com", "wrongpassword");
            } catch (UserNotFoundException | IncorrectPasswordException e) {
                System.err.println(e.getMessage());
            }

            // Test 5: Login with non-existent email
            System.out.println("\n=== Test 5: Login with Non-Existent Email ===");
            try {
                loginService.logIn("nonexistent@example.com", "password123");
            } catch (UserNotFoundException | IncorrectPasswordException e) {
                System.err.println(e.getMessage());
            }

            // Test 6: Logout
            System.out.println("\n=== Test 6: Logout ===");
            UserDTO userToLogout = new UserDTO(1, "John Doe", "john.doe@example.com", Role.ATTENDEE);
            loginService.logout(userToLogout);
            System.out.println("Logout successful for user: " + userToLogout.getName());

        } catch (IOException e) {
            System.err.println("Error interacting with the repository: " + e.getMessage());
        }
    }
}

