package test;

import domain.Conference;
import repository.ConferenceRepository;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

public class ConferenceRepoTest {
    public static void main(String[] args) {
        try {
            // Initialize repository
            String filePath = "data/conferences.json"; // Adjust path if necessary
            ConferenceRepository conferenceRepository = ConferenceRepository.getInstance(filePath);

            // Test 1: Add conferences
            System.out.println("=== Test 1: Add Conferences ===");
            Conference conference1 = new Conference(1, "GAF-AI 2025", "AI advancements",
                    LocalDateTime.of(2025, 5, 10, 9, 0),
                    LocalDateTime.of(2025, 5, 15, 18, 0)
            );
            conference1.setManagersIDs(List.of(1, 2)); // Add managers
            Conference conference2 = new Conference(2, "Quantum Summit", "Quantum breakthroughs",
                    LocalDateTime.of(2025, 6, 1, 10, 0),
                    LocalDateTime.of(2025, 6, 5, 17, 0)
            );
            conference2.setManagersIDs(List.of(3)); // Add managers

            conferenceRepository.save(conference1);
            conferenceRepository.save(conference2);

            // Test 2: Retrieve all conferences
            System.out.println("\n=== Test 2: Retrieve All Conferences ===");
            List<Conference> allConferences = conferenceRepository.findAll();
            for (Conference conference : allConferences) {
                System.out.println(conference);
            }

            // Test 3: Find a conference by ID
            System.out.println("\n=== Test 3: Find Conference by ID ===");
            Conference foundConference = conferenceRepository.findById(1);
            System.out.println(foundConference != null ? foundConference : "Conference not found!");

            // Test 4: Update a conference
            System.out.println("\n=== Test 4: Update a Conference ===");
            conference1.setDescription("Updated AI advancements description");
            conferenceRepository.save(conference1);
            System.out.println("Updated Conference: " + conferenceRepository.findById(1));

            // Test 5: Find conferences by manager ID
            System.out.println("\n=== Test 5: Find Conferences by Manager ID ===");
            List<Conference> managerConferences = conferenceRepository.findByManagerID(1);
            System.out.println("Conferences managed by Manager ID 1:");
            for (Conference conference : managerConferences) {
                System.out.println(conference);
            }

            // Test 6: Delete a conference
            System.out.println("\n=== Test 6: Delete a Conference ===");
            conferenceRepository.delete(2);
            System.out.println("Conferences after deletion:");
            for (Conference conference : conferenceRepository.findAll()) {
                System.out.println(conference);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
