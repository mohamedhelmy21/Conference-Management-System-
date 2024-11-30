package repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import domain.Conference;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ConferenceRepository {
    private final String filePath;

    public ConferenceRepository(String filePath) {
        this.filePath = filePath;
    }

    private ObjectMapper getObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule()); // Register JavaTimeModule for LocalDateTime
        return objectMapper;
    }

    public List<Conference> findAll() throws IOException {
        ObjectMapper objectMapper = getObjectMapper();
        File file = new File(filePath);
        if (!file.exists()) {
            return new ArrayList<>();
        }
        CollectionType listType = objectMapper.getTypeFactory().constructCollectionType(List.class, Conference.class);
        return objectMapper.readValue(file, listType);
    }

    public Conference findById(int conferenceId) throws IOException {
        return findAll().stream().filter(conference -> conference.getConferenceID() == conferenceId).findFirst().orElse(null);
    }

    public void save(Conference conference) throws IOException {
        List<Conference> conferences = findAll();
        conferences.removeIf(existingConference -> existingConference.getConferenceID() == conference.getConferenceID());
        conferences.add(conference);
        writeAll(conferences);
    }

    public void delete(int conferenceId) throws IOException {
        List<Conference> conferences = findAll();
        conferences.removeIf(conference -> conference.getConferenceID() == conferenceId);
        writeAll(conferences);
    }

    public List<Conference> findByManagerID(int managerID) throws IOException {
        return findAll().stream().filter(conference -> conference.getManagersIDs().contains(managerID)).collect(Collectors.toList());
    }

    private void writeAll(List<Conference> conferences) throws IOException {
        ObjectMapper objectMapper = getObjectMapper();
        objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(filePath), conferences);
    }
}
