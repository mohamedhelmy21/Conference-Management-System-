package test;

import controller.AttendeeController;
import dto.CertificateDTO;
import dto.ScheduleDTO;
import dto.SessionDTO;
import enums.Rating;
import service.*;
import repository.*;

import java.util.List;

public class AttendeeControllerTest {
    public static void main(String[] args) {
        UserRepository userRepository = UserRepository.getInstance("data/users.json");
        SessionRepository sessionRepository = SessionRepository.getInstance("data/sessions.json");
        CertificateRepository certificateRepository = CertificateRepository.getInstance("data/certificates.json");
        FeedbackRepository feedbackRepository = FeedbackRepository.getInstance("data/feedback.json");
        ConferenceRepository conferenceRepository = ConferenceRepository.getInstance("data/conferences.json");
        ReportRepository reportRepository = ReportRepository.getInstance("data/reports.json");


        ConferenceService conferenceService = new ConferenceService(conferenceRepository);
        FeedbackService feedbackService = new FeedbackService(feedbackRepository);
        SessionService sessionService = new SessionService(sessionRepository, conferenceService, feedbackService);
        feedbackService.setSessionService(sessionService);

        CertificateService certificateService = new CertificateService(certificateRepository, sessionService); // Placeholder for other dependencies

        SpeakerService speakerService = new SpeakerService(userRepository, sessionService);
        sessionService.setSpeakerService(speakerService);

        AttendeeService attendeeService = new AttendeeService(userRepository, sessionService, certificateService, feedbackService, conferenceService, speakerService);

        ConferenceManagerService conferenceManagerService = new ConferenceManagerService(userRepository, conferenceService, sessionService, speakerService);

        ReportService reportService = new ReportService(reportRepository, feedbackService, conferenceService, sessionService, attendeeService);

        AttendeeController attendeeController = new AttendeeController(attendeeService);

        try {
            // Test: View available sessions for a conference
            System.out.println("Fetching available sessions for conference ID 1...");
            List<SessionDTO> availableSessions = attendeeController.getAvailableSessions(1);
            for (SessionDTO session : availableSessions) {
                System.out.println("Session: " + session.getName());
            }

            // Test: Add session to attendee's schedule
            System.out.println("Adding session to attendee's schedule...");
            attendeeController.addSessionToSchedule(1, 1);
            System.out.println("Session added to attendee's schedule successfully.");

            // Test: View attendee's schedule
            System.out.println("Fetching attendee's schedule...");
            ScheduleDTO schedule = attendeeController.viewSchedule(1);
            System.out.println("Attendee's schedule: " + schedule.getSessionsIDs());

            // Test: Submit feedback for a session
            System.out.println("Submitting feedback...");
            attendeeController.submitFeedback(1, 1, "Great session!", Rating.FIVE, false);
            System.out.println("Feedback submitted successfully.");

            // Test: Fetch attendee's certificate
            System.out.println("Fetching certificate for attendee...");
            CertificateDTO certificate = attendeeController.getCertificateDetails(1);
            System.out.println("Certificate: " + certificate.getCertificateText());

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}

