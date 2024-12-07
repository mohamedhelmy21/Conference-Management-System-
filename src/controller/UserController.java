package controller;

import dto.UserDTO;
import enums.Role;
import exception.IncorrectPasswordException;
import exception.UserAlreadyExistsException;
import exception.UserNotFoundException;
import service.LoginService;

import java.io.IOException;

public class UserController {
    private final LoginService loginService;

    public UserController(LoginService loginService) {
        this.loginService = loginService;
    }

    // Login method
    public UserDTO login(String email, String password) {
        try {
            return loginService.logIn(email, password);
        } catch (UserNotFoundException | IncorrectPasswordException e) {
            // Log the error and rethrow for the UI to handle
            System.err.println(e.getMessage());
            throw e; // Re-throwing to be caught in the UI layer
        } catch (IOException e) {
            System.err.println("Error accessing the user repository: " + e.getMessage());
            return null;
        }
    }

    // Register an attendee
    public UserDTO registerAttendee(String name, String email, String password) {
        try {
            UserDTO newUser = loginService.register(name, email, password, Role.ATTENDEE);

            if (newUser != null) {
                System.out.println("Registration successful for attendee: " + newUser.getName());
                return newUser;
            }

            System.err.println("Registration failed: Unknown error.");
            return null;

        } catch (UserAlreadyExistsException e) {
            System.err.println("Registration failed: User already exists.");
            return null;
        } catch (IOException e) {
            System.err.println("Registration failed: Unable to access data.");
            return null;
        }
    }

    // Logout method
    public void logout(UserDTO userDTO) {
        try {
            loginService.logout(userDTO);
            System.out.println("User logged out successfully: " + userDTO.getName());
        } catch (Exception e) {
            System.err.println("Logout failed: " + e.getMessage());
        }
    }
}

