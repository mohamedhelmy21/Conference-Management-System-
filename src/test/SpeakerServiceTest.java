package test;

import domain.Speaker;
import dto.SpeakerDTO;
import dto.SessionDTO;
import enums.Role;
import repository.UserRepository;
import repository.SessionRepository;
import repository.ConferenceRepository;
import service.FeedbackService;
import service.SpeakerService;
import service.SessionService;
import service.ConferenceService;

import java.time.LocalDateTime;
import java.util.List;

public class SpeakerServiceTest {
    public static void main(String[] args) {
        try {
            // Initialize repositories
            UserRepository userRepository = UserRepository.getInstance("data/users.json");
            SessionRepository sessionRepository = SessionRepository.getInstance("data/sessions.json");
            ConferenceRepository conferenceRepository = ConferenceRepository.getInstance("data/conferences.json");


            // Initialize services
            ConferenceService conferenceService = new ConferenceService(conferenceRepository);
            SessionService sessionService = new SessionService(sessionRepository, conferenceService, null); // Passing null for unused services
            SpeakerService speakerService = new SpeakerService(userRepository, sessionService, null);

            sessionService.setSpeakerService(speakerService);

            // Add test speaker
            Speaker speaker = new Speaker(
                    1,
                    "Jane Doe",
                    "jane.doe@example.com",
                    "password123",
                    Role.SPEAKER,
                    "AI Expert",
                    "Artificial Intelligence",
                    "TechCorp"
            );
            userRepository.save(speaker);
            System.out.println("Speaker added to repository.");

            // Add test session for the speaker
            SessionDTO session = sessionService.createSession(
                    "AI and Ethics",
                    LocalDateTime.now().plusDays(1),
                    "Room A",
                    50,
                    1, // Speaker ID
                    "Exploring ethical challenges in AI",
                    1 // Conference ID
            );
            System.out.println("Test session added: " + session);

            // Test getSpeakerProfile
            System.out.println("\nTesting getSpeakerProfile...");
            SpeakerDTO speakerProfile = speakerService.getSpeakerProfile(1);
            System.out.println("Speaker Profile: " + speakerProfile);

            // Test updateSpeakerBio
            System.out.println("\nTesting updateSpeakerBio...");
            speakerService.updateSpeakerBio(1, "Expert in AI Ethics");
            SpeakerDTO updatedProfile = speakerService.getSpeakerProfile(1);
            System.out.println("Updated Speaker Bio: " + updatedProfile.getBio());

            // Test getSpeakerSessions
            System.out.println("\nTesting getSpeakerSessions...");
            List<SessionDTO> sessions = speakerService.getSpeakerSessions(1);
            System.out.println("Speaker Sessions:");
            sessions.forEach(System.out::println);

            // Test getSpeakerBio
            System.out.println("\nTesting getSpeakerBio...");
            String bio = speakerService.getSpeakerBio(1);
            System.out.println("Speaker Bio: " + bio);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

