package test;

import domain.*;
import enums.Rating;
import enums.Role;

import java.time.LocalDateTime;
import java.util.Arrays;

import static enums.Role.*;

public class domainTest {
    public static void main(String[] args) {
        // Test User and Subclasses
        testUsers();

        // Test Conference and Session
        testConferenceAndSessions();

        // Test Feedback
        testFeedback();

        // Test Certificate
        testCertificate();

        // Test Schedule
        testSchedule();
    }

    private static void testUsers() {
        System.out.println("==== Testing Users ====");
        Attendee attendee = new Attendee(1, "Dr. Abram", "abram@example.com", "password123", ATTENDEE);
        Speaker speaker = new Speaker(2, "Dr. Jane", "jane@example.com", "password123", SPEAKER, "speaker bio", "speaker expertise", "Tech Corp");
        ConferenceManager manager = new ConferenceManager(3, "Nancy", "nancy@example.com", "admin123", MANAGER);

        System.out.println("Attendee: " + attendee);
        System.out.println("Speaker: " + speaker);
        System.out.println("Manager: " + manager);

        // Notifications
        attendee.notify("Session Update: 'Introduction to AI' starts at 10 AM.");
        speaker.notify("Your session 'AI Trends' is confirmed.");
    }

    private static void testConferenceAndSessions() {
        System.out.println("\n==== Testing Conference and Sessions ====");

        // Create Conference
        Conference conference = new Conference(101, "GAF-AI 2025", "Advances in AI",
                LocalDateTime.of(2025, 5, 10, 9, 0),
                LocalDateTime.of(2025, 5, 15, 18, 0));
        System.out.println("Conference: " + conference);

        // Create Sessions
        Session session1 = new Session(201, "Introduction to AI",
                LocalDateTime.of(2025, 5, 11, 10, 0), "Room A", 50, 2, "Fundementals of AI", 1);
        Session session2 = new Session(202, "Advanced Machine Learning",
                LocalDateTime.of(2025, 5, 12, 14, 0), "Room B", 30, 2, "Machine Learning Algorithms", 1);

        // Add Sessions to Conference
        conference.addSession(session1.getSessionID());
        conference.addSession(session2.getSessionID());

        // Register Attendees
        session1.addToSignedUp(1); // Attendee ID: 1
        session1.addToSignedUp(2); // Attendee ID: 2
        session2.addToSignedUp(1);

        // Display Session Details
        System.out.println("Session 1: " + session1.getName() + ", Attendees: " + session1.getSignedUpAttendees());
        System.out.println("Session 2: " + session2.getName() + ", Attendees: " + session2.getSignedUpAttendees());

        // Test Notifications
        session1.notifyObservers("Session 'Introduction to AI' is starting now.");
    }

    private static void testFeedback() {
        System.out.println("\n==== Testing Feedback ====");

        // Create Feedback
        Feedback feedback1 = new Feedback(301, 1, 201, "Great session!", Rating.FIVE, false);
        Feedback feedback2 = new Feedback(302, 1, 202, "Very informative.", Rating.FOUR, true);

        System.out.println("Feedback 1: " + feedback1);
        System.out.println("Feedback 2: " + feedback2);

        // Update Feedback
        feedback1.updateFeedback("Amazing session, learned a lot!", Rating.FIVE);
        System.out.println("Updated Feedback 1: " + feedback1);
    }

    private static void testCertificate() {
        System.out.println("\n==== Testing Certificates ====");

        // Create Sessions
        Session session1 = new Session(201, "Introduction to AI",
                LocalDateTime.of(2025, 5, 11, 10, 0), "Room A", 50, 2, "Fundementals of AI", 1);
        Session session2 = new Session(202, "Advanced Machine Learning",
                LocalDateTime.of(2025, 5, 12, 14, 0), "Room B", 30, 2, "Machine Learning Algorithms", 1);

        // Generate Certificate
        Certificate certificate = new Certificate(401, 1, Arrays.asList(session1.getSessionID(), session2.getSessionID()), "");
        System.out.println("Certificate: " + certificate);

        // Download Certificate
        certificate.downloadCertificate();
    }

    private static void testSchedule() {
        System.out.println("\n==== Testing Schedule ====");

        // Create Schedule
        Schedule schedule = new Schedule(1); // Attendee ID: 1
        schedule.addSession(201); // Session ID: 201
        schedule.addSession(202); // Session ID: 202

        System.out.println("Schedule: " + schedule);

        // Remove Session
        schedule.removeSession(201);
        System.out.println("Updated Schedule: " + schedule);
    }
}
