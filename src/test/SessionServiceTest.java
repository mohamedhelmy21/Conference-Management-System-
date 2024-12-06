package test;

import domain.Session;
import repository.ConferenceRepository;
import repository.FeedbackRepository;
import repository.SessionRepository;
import service.ConferenceService;
import service.FeedbackService;
import service.SessionService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class SessionServiceTest {
    public static void main(String[] args) {
        ConferenceRepository conferenceRepository = ConferenceRepository.getInstance("data/conferences.json");
        SessionRepository sessionRepository = SessionRepository.getInstance("data/sessions.json");
        FeedbackRepository feedbackRepository = FeedbackRepository.getInstance("data/feedback.json");

        // Initialize services with circular dependencies
        FeedbackService feedbackService = new FeedbackService(feedbackRepository);
        ConferenceService conferenceService = new ConferenceService(conferenceRepository);
        SessionService sessionService = new SessionService(sessionRepository, conferenceService, feedbackService);

        // Resolve circular dependency
        feedbackService.setSessionService(sessionService);



        try {

            // Ensure the conference with ID 1 exists
            System.out.println("Retrieving conference...");
            var conference = conferenceService.getConferenceDetails(1);
            System.out.println("Conference retrieved: " + conference);

            // 1. Test session creation
            System.out.println("\nCreating a session...");
            var sessionDTO = sessionService.createSession(
                    "Deep Learning Workshop",
                    LocalDateTime.now().plusDays(3), // Scheduled for 3 days from now
                    "Room C",
                    40, // Capacity
                    1, // Speaker ID
                    "A comprehensive deep learning workshop",
                    1 // Conference ID
            );
            System.out.println("Session created: " + sessionDTO);

            // 2. Test listing sessions by conference
            System.out.println("\nListing sessions for conference ID 1...");
            var sessions = sessionService.listSessionsByConference(1);
            System.out.println("Sessions: ");
            sessions.forEach(System.out::println);

            // 3. Test view session details
            System.out.println("\nViewing details for session ID: " + sessionDTO.getSessionID());
            var sessionDetails = sessionService.viewSessionDetails(sessionDTO.getSessionID());
            System.out.println("Session details: " + sessionDetails);


            // 5. Test adding an attendee to the session
            System.out.println("\nAdding attendee to the session...");
            sessionService.addAttendeeToSession(sessionDTO.getSessionID(), 1); // Attendee ID 1
            System.out.println("Attendee 2001 added to the session.");

            // 4. Test registering attendance
            System.out.println("\nRegistering attendance...");
            sessionService.registerAttendance(1, sessionDTO.getSessionID()); // Attendee ID 1
            System.out.println("Attendance registered for attendee 2001.");



            // 6. Test schedule conflicts
            System.out.println("\nChecking schedule conflicts...");
            List<Integer> existingSessions = new ArrayList<>();
            existingSessions.add(sessionDTO.getSessionID());
            sessionService.checkScheduleConflicts(existingSessions, sessionDTO.getSessionID());
            System.out.println("No schedule conflicts detected.");

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
