package test;

import domain.Attendee;
import domain.Conference;
import domain.Session;
import domain.Speaker;
import dto.*;
import enums.Rating;
import enums.Role;
import repository.*;
import service.*;
import utility.IDGenerator;

import java.time.LocalDateTime;

public class AttendeeServiceTest {
    public static void main(String[] args) {
        try {
            // Step 1: Initialize repositories
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

            certificateService.setAttendeeService(attendeeService);

            Conference conference1 = conferenceService.createConference(
                    "GAF-AI 2025",
                    "AI advancements",
                    LocalDateTime.of(2025, 5, 10, 9, 0),
                    LocalDateTime.of(2025, 5, 15, 18, 0),
                    1
            );

            Speaker speaker = new Speaker(
                    2,
                    "Jane Doe",
                    "jane.doe@example.com",
                    "password123",
                    Role.SPEAKER,
                    "AI Expert",
                    "Artificial Intelligence",
                    "TechCorp"
            );
            userRepository.save(speaker);

            // Step 3: Add test attendee
            Attendee attendee = new Attendee(
                    IDGenerator.generateId("User"),
                    "John Doe",
                    "john.doe@example.com",
                    "password123",
                    Role.ATTENDEE
            );
            userRepository.save(attendee);
            System.out.println("Test attendee added: " + attendee);

            attendeeService.registerAttendeeToConference(attendee.getUserID(), conference1.getConferenceID());

            // Step 4: Add test session
            SessionDTO session = sessionService.createSession(
                    "Intro to AI",
                    LocalDateTime.now().plusDays(1),
                    "Room A",
                    50,
                    2, // Speaker ID
                    "An overview of AI concepts",
                    1 // Conference ID
            );
            System.out.println("Test session added: " + session);

            // Step 5: Test addSessionToSchedule
            System.out.println("\nTesting addSessionToSchedule...");
            attendeeService.addSessionToSchedule(attendee.getUserID(), session.getSessionID());
            System.out.println("Session added to attendee schedule.");

            // Step 6: Test viewSchedule
            System.out.println("\nTesting viewSchedule...");
            ScheduleDTO schedule = attendeeService.viewSchedule(attendee.getUserID());
            System.out.println("Attendee Schedule: " + schedule);

            // Step 7: Test registerAttendance
            System.out.println("\nTesting registerAttendance...");
            attendeeService.registerAttendance(attendee.getUserID(), session.getSessionID());
            System.out.println("Attendance registered for session.");

            // Step 8: Test submitFeedback
            System.out.println("\nTesting submitFeedback...");
            attendeeService.submitFeedback(attendee.getUserID(), session.getSessionID(), "Great session!", Rating.FIVE, false);
            System.out.println("Feedback submitted.");

            // Step 9: Test requestCertificate (mock logic)

            System.out.println("\nTesting requestCertificate...");
            CertificateDTO certificateDTO = certificateService.generateCertificate(attendee.getUserID());
            attendeeService.getCertificate(certificateDTO.getCertificateID());
            System.out.println("This step requires a working CertificateService and proper session attendance tracking logic.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
