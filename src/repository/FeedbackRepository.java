package repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import domain.Feedback;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FeedbackRepository {
    private final String filePath;

    public FeedbackRepository(String filePath) {
        this.filePath = filePath;
    }

    private ObjectMapper getObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules(); // Automatically registers JavaTimeModule
        objectMapper.registerModule(new JavaTimeModule());
        return objectMapper;
    }

    public List<Feedback> findAll() throws IOException {
        ObjectMapper objectMapper = getObjectMapper();
        File file = new File(filePath);

        if (!file.exists() || file.length() == 0) {
            return new ArrayList<>();
        }

        CollectionType listType = objectMapper.getTypeFactory().constructCollectionType(List.class, Feedback.class);
        return objectMapper.readValue(file, listType);
    }

    public int generateFeedbackID() throws IOException {
        List<Feedback> feedbacks = findAll();
        if (feedbacks.isEmpty()) {
            return 1;
        }
        return feedbacks.stream()
                .mapToInt(Feedback::getFeedbackID)
                .max()
                .orElse(0) + 1;
    }

    public Feedback findById(int feedbackId) throws IOException {
        return findAll().stream().filter(feedback -> feedback.getFeedbackID() == feedbackId).findFirst().orElse(null);
    }

    public void save(Feedback feedback) throws IOException {
        List<Feedback> feedbacks = findAll();
        feedbacks.removeIf(existingFeedback -> existingFeedback.getFeedbackID() == feedback.getFeedbackID());
        feedbacks.add(feedback);

        writeAll(feedbacks);
    }

    public void delete(int feedbackId) throws IOException {
        List<Feedback> feedbacks = findAll();
        feedbacks.removeIf(feedback -> feedback.getFeedbackID() == feedbackId);
        writeAll(feedbacks);
    }

    public List<Feedback> findBySessionID(int sessionID) throws IOException {
        return findAll().stream().filter(feedback -> feedback.getSessionID() == sessionID).collect(Collectors.toList());
    }

    public List<Feedback> findByAttendeeID(int attendeeID) throws IOException {
        return findAll().stream().filter(feedback -> feedback.getAttendeeID() == attendeeID).collect(Collectors.toList());
    }

    private void writeAll(List<Feedback> feedbacks) throws IOException {
        ObjectMapper objectMapper = getObjectMapper();
        objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(filePath), feedbacks);
    }
}

