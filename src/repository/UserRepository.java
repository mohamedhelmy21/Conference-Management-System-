package repository;


import domain.User;
import enums.Role;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import exception.RepositoryException;

public class UserRepository extends BaseRepository<User> {
    public UserRepository(String filePath) {
        super(filePath, User.class);
    }

    @Override
    protected Integer getId(User user) {
        return user.getUserID();
    }

    public List<User> findByRole(Role role) throws IOException {
        return findAll().stream().filter(user -> user.getRole() == role).collect(Collectors.toList());
    }

    public User findByEmail(String email) throws IOException {
        return findAll().stream()
                .filter(user -> user.getEmail().equalsIgnoreCase(email))
                .findFirst()
                .orElse(null);
    }

}
