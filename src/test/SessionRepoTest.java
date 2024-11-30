package test;

import domain.Session;
import repository.SessionRepository;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

public class SessionRepoTest {
    public static void main(String[] args) {
        try {
            // Initialize repository
            String filePath = "data/sessions.json"; // Adjust path as necessary
            SessionRepository sessionRepository = new SessionRepository(filePath);

            // Test 1: Add sessions
            System.out.println("=== Test 1: Add Sessions ===");
            Session session1 = new Session(
                    201, "Introduction to LLMs", LocalDateTime.of(2025, 5, 10, 10, 0),
                    "Room A", 100, 1, "Learn the basics of Large Language Models"
            );
            Session session2 = new Session(
                    202, "Advances in AI Ethics", LocalDateTime.of(2025, 5, 11, 14, 0),
                    "Room B", 50, 2, "Discussion on ethical challenges in AI"
            );

            sessionRepository.save(session1);
            sessionRepository.save(session2);

            // Test 2: Retrieve all sessions
            System.out.println("\n=== Test 2: Retrieve All Sessions ===");
            List<Session> allSessions = sessionRepository.findAll();
            for (Session session : allSessions) {
                System.out.println(session);
            }

            // Test 3: Find session by ID
            System.out.println("\n=== Test 3: Find Session by ID ===");
            Session foundSession = sessionRepository.findById(201);
            System.out.println(foundSession != null ? foundSession : "Session not found!");

            // Test 4: Update a session
            System.out.println("\n=== Test 4: Update a Session ===");
            session1.setRoom("Room C");
            sessionRepository.save(session1);
            System.out.println("Updated Session: " + sessionRepository.findById(201));

            // Test 5: Find sessions by speaker ID
            System.out.println("\n=== Test 5: Find Sessions by Speaker ID ===");
            List<Session> speakerSessions = sessionRepository.findBySpeakerID(2);
            System.out.println("Sessions by Speaker ID 2:");
            for (Session session : speakerSessions) {
                System.out.println(session);
            }

            // Test 6: Delete a session
            System.out.println("\n=== Test 6: Delete a Session ===");
            sessionRepository.delete(202);
            System.out.println("Sessions after deletion:");
            for (Session session : sessionRepository.findAll()) {
                System.out.println(session);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
