package test;

import domain.User;
import enums.Role;
import repository.UserRepository;

import java.io.IOException;
import java.util.List;

public class UserRepoTest {
    public static void main(String[] args) {
        try {
            // Initialize repository
            String filePath = "data/users.json"; // Adjust path as necessary
            UserRepository userRepository = new UserRepository(filePath);

            // Test 1: Add users
            System.out.println("=== Test 1: Add Users ===");
            User attendee = new User(1, "Alice", "alice@example.com", "password123", Role.ATTENDEE);
            User speaker = new User(2, "Bob", "bob@example.com", "securepassword", Role.SPEAKER);
            User manager = new User(3, "Nancy", "nancy@example.com", "admin123", Role.MANAGER);
            userRepository.save(attendee);
            userRepository.save(speaker);
            userRepository.save(manager);

            // Test 2: Retrieve all users
            System.out.println("\n=== Test 2: Retrieve All Users ===");
            List<User> allUsers = userRepository.findAll();
            for (User user : allUsers) {
                System.out.println(user);
            }

            // Test 3: Find user by ID
            System.out.println("\n=== Test 3: Find User by ID ===");
            User foundUser = userRepository.findById(2);
            System.out.println(foundUser != null ? foundUser : "User not found!");

            // Test 4: Update a user
            System.out.println("\n=== Test 4: Update a User ===");
            attendee.setEmail("alice.newemail@example.com");
            userRepository.save(attendee);
            System.out.println("Updated User: " + userRepository.findById(1));

            // Test 5: Find users by role
            System.out.println("\n=== Test 5: Find Users by Role ===");
            List<User> speakers = userRepository.findByRole(Role.SPEAKER);
            System.out.println("Speakers:");
            for (User user : speakers) {
                System.out.println(user);
            }

            // Test 6: Delete a user
            System.out.println("\n=== Test 6: Delete a User ===");
            userRepository.delete(3);
            System.out.println("Users after deletion:");
            for (User user : userRepository.findAll()) {
                System.out.println(user);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

