package repository;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
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
    protected ObjectMapper getObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();

        // Register JavaTimeModule for date/time handling
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        // Include non-empty fields during serialization
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);

        return objectMapper;
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
