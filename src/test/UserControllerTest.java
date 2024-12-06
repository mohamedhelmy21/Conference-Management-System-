package test;

import controller.UserController;
import dto.UserDTO;
import enums.Role;
import repository.UserRepository;
import service.LoginService;

import java.io.File;
import java.io.IOException;

public class UserControllerTest {
    public static void main(String[] args) {
        try {
            // Prepare the repository with mock data
            File userFile = new File("data/users.json");
            if (userFile.exists()) {
                userFile.delete(); // Clear any existing data
            }
            UserRepository userRepository = UserRepository.getInstance("data/users.json");

            // Initialize services and controllers
            LoginService loginService = new LoginService(userRepository);
            UserController userController = new UserController(loginService);

            // Test registration of an attendee
            System.out.println("Registering attendee...");
            UserDTO newAttendee = userController.registerAttendee("John Doe", "john.doe@example.com", "password123");
            if (newAttendee != null) {
                System.out.println("Registration successful: " + newAttendee);
            } else {
                System.out.println("Registration failed.");
            }

            // Attempt to register the same attendee again (should fail)
            System.out.println("\nRegistering the same attendee again...");
            UserDTO duplicateAttendee = userController.registerAttendee("John Doe", "john.doe@example.com", "password123");
            if (duplicateAttendee == null) {
                System.out.println("Registration failed as expected: Duplicate email.");
            }

            // Test login for the registered attendee
            System.out.println("\nLogging in with correct credentials...");
            UserDTO loggedInUser = userController.login("john.doe@example.com", "password123");
            if (loggedInUser != null) {
                System.out.println("Login successful: " + loggedInUser);
            } else {
                System.out.println("Login failed.");
            }

            // Attempt login with incorrect password
            System.out.println("\nLogging in with incorrect password...");
            UserDTO wrongPasswordLogin = userController.login("john.doe@example.com", "wrongpassword");
            if (wrongPasswordLogin == null) {
                System.out.println("Login failed as expected: Incorrect password.");
            }

            // Test logout
            System.out.println("\nLogging out...");
            if (loggedInUser != null) {
                userController.logout(loggedInUser);
            }

        } catch (Exception e) {
            System.err.println("Unexpected error: " + e.getMessage());
        }
    }
}

