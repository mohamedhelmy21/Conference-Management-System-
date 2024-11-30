package repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class BaseRepository<T> {
    private final String filePath;
    private final Class<T> entityType;

    public BaseRepository(String filePath, Class<T> entityType) {
        this.filePath = filePath;
        this.entityType = entityType;
    }

    protected ObjectMapper getObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        return objectMapper;
    }

    public List<T> findAll() throws IOException {
        ObjectMapper objectMapper = getObjectMapper();
        File file = new File(filePath);

        if (!file.exists() || file.length() == 0) {
            return new ArrayList<>();
        }

        CollectionType listType = objectMapper.getTypeFactory().constructCollectionType(List.class, entityType);
        return objectMapper.readValue(file, listType);
    }

    public T findById(int id) throws IOException {
        return findAll().stream()
                .filter(entity -> getId(entity).equals(id))
                .findFirst()
                .orElse(null);
    }

    public void save(T entity) throws IOException {
        List<T> entities = findAll();

        entities.removeIf(existingEntity -> getId(existingEntity).equals(getId(entity)));
        entities.add(entity);

        writeAll(entities);
    }

    public void delete(int id) throws IOException {
        List<T> entities = findAll();

        entities.removeIf(entity -> getId(entity).equals(id));

        writeAll(entities);
    }

    private void writeAll(List<T> entities) throws IOException {
        ObjectMapper objectMapper = getObjectMapper();
        objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(filePath), entities);
    }

    protected abstract Integer getId(T entity);
}
