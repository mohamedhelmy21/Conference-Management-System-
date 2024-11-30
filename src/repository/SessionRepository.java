package repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.databind.SerializationFeature;
import domain.Session;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SessionRepository {
    private final String filePath;

    public SessionRepository(String filePath) {
        this.filePath = filePath;
    }

    private ObjectMapper getObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        return objectMapper;
    }

    public List<Session> findAll() throws IOException {
        ObjectMapper objectMapper = getObjectMapper();
        File file = new File(filePath);

        if (!file.exists() || file.length() == 0) {
            return new ArrayList<>();
        }

        CollectionType listType = objectMapper.getTypeFactory().constructCollectionType(List.class, Session.class);
        return objectMapper.readValue(file, listType);
    }

    public Session findById(int sessionId) throws IOException {
        return findAll().stream().filter(session -> session.getSessionID() == sessionId).findFirst().orElse(null);
    }

    public void save(Session session) throws IOException {
        List<Session> sessions = findAll();
        sessions.removeIf(existingSession -> existingSession.getSessionID() == session.getSessionID());
        sessions.add(session);

        writeAll(sessions);
    }

    public void delete(int sessionId) throws IOException {
        List<Session> sessions = findAll();
        sessions.removeIf(session -> session.getSessionID() == sessionId);
        writeAll(sessions);
    }

    public List<Session> findBySpeakerID(int speakerID) throws IOException {
        return findAll().stream().filter(session -> session.getSpeakerID() == speakerID).collect(Collectors.toList());
    }

    private void writeAll(List<Session> sessions) throws IOException {
        ObjectMapper objectMapper = getObjectMapper();
        objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(filePath), sessions);
    }
}

