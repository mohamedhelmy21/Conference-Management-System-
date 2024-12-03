package service;

import dto.UserDTO;
import enums.Role;
import repository.UserRepository;
import domain.User;
import exception.UserNotFoundException;
import exception.IncorrectPasswordException;
import exception.UserAlreadyExistsException;
import utility.IDGenerator;

import java.io.IOException;


public class LoginService {
    private final UserRepository userRepository;

    public LoginService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    //LogIn and authenticate user
    public UserDTO logIn(String email, String password) throws IOException, UserNotFoundException, IncorrectPasswordException{
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

        if (role == Role.SPEAKER){
            throw new UnsupportedOperationException("Speakers cannot self-register");
        }

        if (userRepository.findByEmail(email) != null){
            throw new UserAlreadyExistsException("User already exists");
        }

        int newUserId = IDGenerator.generateId("User");
        User newUser = new User(newUserId, name, email, password, role);
        userRepository.save(newUser);

        return mapToDTO(newUser);
    }

    public void logout(UserDTO userDTO){
        System.out.println(userDTO);
    }

    private UserDTO mapToDTO(User user){
        return new UserDTO(user.getUserID(), user.getName(), user.getEmail(), user.getRole());
    }
}
