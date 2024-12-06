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
            UserDTO user = loginService.logIn(email, password);

            if (user == null) {
                System.err.println("Login failed: User not found.");
                return null;
            }

            // Log success
            System.out.println("Login successful for user: " + user.getName() + ", Role: " + user.getRole());
            return user;

        } catch (UserNotFoundException e) {
            System.err.println("Login failed: User not found.");
            return null;
        } catch (IncorrectPasswordException e) {
            System.err.println("Login failed: Incorrect password.");
            return null;
        } catch (IOException e) {
            System.err.println("Login failed: Unable to access data.");
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

