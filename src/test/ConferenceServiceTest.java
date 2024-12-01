package test;

import domain.Conference;
import dto.ConferenceDTO;
import repository.ConferenceRepository;
import service.ConferenceService;

import java.time.LocalDateTime;

public class ConferenceServiceTest {
    public static void main(String[] args) {
        String filePath = "data/conferences.json"; // Adjust path as necessary
        ConferenceRepository conferenceRepository = new ConferenceRepository(filePath);
        ConferenceService conferenceService = new ConferenceService(conferenceRepository);

        try {
            // Test 1: Create a conference
            System.out.println("=== Test 1: Create Conference ===");
            Conference conference1 = conferenceService.createConference(
                    "GAF-AI 2025",
                    "AI advancements",
                    LocalDateTime.of(2025, 5, 10, 9, 0),
                    LocalDateTime.of(2025, 5, 15, 18, 0),
                    1
            );
            System.out.println("Created Conference: " + conference1);

            // Test 2: Retrieve a conference by ID
            System.out.println("\n=== Test 2: Find Conference by ID ===");
            ConferenceDTO foundConference = conferenceService.findConferenceByID(conference1.getConferenceID());
            System.out.println("Found Conference: " + foundConference);

            // Test 3: Update a conference
            System.out.println("\n=== Test 3: Update Conference ===");
            conferenceService.updateConference(
                    conference1.getConferenceID(),
                    "Updated GAF-AI 2025",
                    "Updated description of AI advancements",
                    LocalDateTime.of(2025, 5, 11, 9, 0),
                    LocalDateTime.of(2025, 5, 16, 18, 0)
            );
            ConferenceDTO updatedConference = conferenceService.findConferenceByID(conference1.getConferenceID());
            System.out.println("Updated Conference: " + updatedConference);

            // Test 4: Delete a conference
            System.out.println("\n=== Test 4: Delete Conference ===");
            conferenceService.deleteConference(conference1.getConferenceID());
            System.out.println("Conference deleted successfully.");
            try {
                conferenceService.findConferenceByID(conference1.getConferenceID());
            } catch (IllegalArgumentException e) {
                System.out.println("Expected Error: " + e.getMessage());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

