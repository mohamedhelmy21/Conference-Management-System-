package test;

import domain.Attendee;
import domain.ConferenceManager;
import dto.ConferenceDTO;
import dto.SessionDTO;
import dto.SpeakerDTO;
import dto.UserDTO;
import enums.Role;
import repository.*;
import service.*;
import utility.IDGenerator;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class ConferenceManagerServiceTest {
    public static void main(String[] args) {
        try {
            // Initialize repositories
            UserRepository userRepository = UserRepository.getInstance("data/users.json");
            SessionRepository sessionRepository = SessionRepository.getInstance("data/sessions.json");
            ConferenceRepository conferenceRepository = ConferenceRepository.getInstance("data/conferences.json");
            FeedbackRepository feedbackRepository = FeedbackRepository.getInstance("data/feedback.json");
            CertificateRepository certificateRepository = CertificateRepository.getInstance("data/certificates.json");

            // Step 2: Initialize services
            ConferenceService conferenceService = new ConferenceService(conferenceRepository);
            FeedbackService feedbackService = new FeedbackService(feedbackRepository);
            SessionService sessionService = new SessionService(sessionRepository, conferenceService, feedbackService);
            feedbackService.setSessionService(sessionService);

            CertificateService certificateService = new CertificateService(certificateRepository, sessionService); // Placeholder for other dependencies

            SpeakerService speakerService = new SpeakerService(userRepository, sessionService, feedbackService);
            sessionService.setSpeakerService(speakerService);

            AttendeeService attendeeService = new AttendeeService(
                    userRepository,
                    sessionService,
                    certificateService,
                    feedbackService,
                    conferenceService,
                    speakerService
            );

            ConferenceManagerService managerService = new ConferenceManagerService(
                    userRepository,
                    conferenceService,
                    sessionService,
                    speakerService
            );

            // Step 1: Create a Conference Manager
            System.out.println("Creating a Conference Manager...");
            ConferenceManager manager = new ConferenceManager(2, "Nancy Manager", "nancy.manager@example.com", "password", Role.MANAGER);
            userRepository.save(manager);
            System.out.println("Conference Manager created: " + manager);

            // Step 2: Create a Conference
            System.out.println("\nCreating a Conference...");
            ConferenceDTO conference = managerService.createConference(
                    "AI Summit 2025",
                    "",
                    LocalDateTime.of(2024, 1, 1, 9, 0),
                    LocalDateTime.of(2024, 1, 7, 18, 0),
                    manager.getUserID()
            );
            System.out.println("Conference created: " + conference);

            // Step 3: Create a Speaker Account
            System.out.println("\nCreating a Speaker Account...");
            SpeakerDTO speaker = managerService.createSpeakerAccount(
                    manager.getUserID(),
                    "Dr. Smith",
                    "dr.smith@example.com",
                    "password123",
                    "Expert in AI",
                    "Machine Learning",
                    "Tech University"
            );
            System.out.println("Speaker created: " + speaker);

            // Step 4: Create a Session
            System.out.println("\nCreating a Session...");
            SessionDTO session = managerService.createSession(
                    manager.getUserID(),
                    "Introduction to AI",
                    LocalDateTime.of(2025, 1, 2, 10, 0),
                    "Room A",
                    100,
                    speaker.getSpeakerID(),
                    "A comprehensive introduction to Artificial Intelligence.",
                    conference.getConferenceID()
            );
            System.out.println("Session created: " + session);

            Attendee attendee = new Attendee(
                    3,
                    "John Doe",
                    "john.doe@example.com",
                    "password123",
                    Role.ATTENDEE
            );
            userRepository.save(attendee);
            attendeeService.registerAttendeeToConference(3, 1);
            attendeeService.addSessionToSchedule(3, 1);



            // Step 5: Remove an Attendee from the Conference


            // Step 6: Send Conference Update
            System.out.println("\nSending Conference Update...");
            managerService.sendConferenceUpdate(conference.getConferenceID(), "New session added: Advanced AI Applications.");
            System.out.println("Conference update sent.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Helper method to map UserDTO to a domain object for saving
    private static domain.ConferenceManager mapToDomain(UserDTO dto) {
        return new domain.ConferenceManager(dto.getUserID(), dto.getName(), dto.getEmail(), "password123", dto.getRole());
    }
}
