package service;

import dto.UserDTO;
import enums.Role;
import repository.UserRepository;
import domain.User;
import exception.UserNotFoundException;
import exception.IncorrectPasswordException;
import exception.UserAlreadyExistsException;

import java.io.IOException;
import java.util.List;

public class LoginService {
    private final UserRepository userRepository;

    public LoginService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDTO authenticate(String email, String password) throws IOException, UserNotFoundException, IncorrectPasswordException{
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UserNotFoundException("User does not exist");
        }
        if (!user.getPassword().equals(password)) {
            throw new IncorrectPasswordException("Incorrect password");
        }
        return mapToDTO(user);
    }

    public UserDTO register(String name, String email, String password, Role role) throws IOException, UserAlreadyExistsException {
        if (role == Role.MANAGER){
            throw new UnsupportedOperationException("Managers cannot self-register");
        }

        if (userRepository.findByEmail(email) != null){
            throw new UserAlreadyExistsException("User already exists");
        }

        int newUserId = generateUserId();
        User newUser = new User(newUserId, name, email, password, role);
        userRepository.save(newUser);

        return mapToDTO(newUser);
    }

    public int generateUserId() throws IOException{
        List<User> users = userRepository.findAll();
        if (users.isEmpty()) {
            return 1; // Start IDs from 1 if no users exist
        }
        return users.stream()
                .mapToInt(User::getUserID)
                .max()
                .orElse(0) + 1;
    }

    public void logout(UserDTO userDTO){
        System.out.println(userDTO);
    }

    private UserDTO mapToDTO(User user){
        return new UserDTO(user.getUserID(), user.getName(), user.getEmail(), user.getRole());
    }
}
