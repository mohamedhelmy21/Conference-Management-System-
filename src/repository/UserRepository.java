package repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import domain.User;
import enums.Role;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class UserRepository {
    private final String filePath;

    public UserRepository(String filePath) {
        this.filePath = filePath;
    }

    // Retrieve all users
    public List<User> findAll() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        File file = new File(filePath);
        if (!file.exists() || file.length() == 0) { // Check if the file is empty
            return new ArrayList<>();
        }
        CollectionType listType = objectMapper.getTypeFactory().constructCollectionType(List.class, User.class);
        return objectMapper.readValue(file, listType);
    }

    /**
     * Generates a unique user ID.
     *
     * @return A new unique user ID.
     * @throws IOException If there is an issue reading from the repository.
     */
    public int generateUserID() throws IOException {
        List<User> users = findAll();
        if (users.isEmpty()) {
            return 1; // Start with ID 1 if the repository is empty
        }
        return users.stream()
                .mapToInt(User::getUserID)
                .max()
                .orElse(0) + 1; // Increment the highest existing ID
    }

    // Retrieve a user by ID
    public User findById(int userId) throws IOException {
        return findAll().stream().filter(user -> user.getUserID() == userId).findFirst().orElse(null);
    }

    // Save or update a user
    public void save(User user) throws IOException {
        List<User> users = findAll();
        users.removeIf(existingUser -> existingUser.getUserID() == user.getUserID());
        users.add(user);
        writeAll(users);
    }

    // Save or update multiple users
    public void saveAll(List<User> usersToSave) throws IOException {
        List<User> users = findAll();
        for (User user : usersToSave) {
            users.removeIf(existingUser -> existingUser.getUserID() == user.getUserID());
        }
        users.addAll(usersToSave);
        writeAll(users);
    }

    // Delete a user by ID
    public void delete(int userId) throws IOException {
        List<User> users = findAll();
        users.removeIf(user -> user.getUserID() == userId);
        writeAll(users);
    }

    // Find users by role (e.g., attendees, speakers, managers)
    public List<User> findByRole(Role role) throws IOException {
        return findAll().stream().filter(user -> user.getRole() == role).collect(Collectors.toList());
    }

    // Write updated users back to the JSON file
    private void writeAll(List<User> users) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(filePath), users);
    }
}

