//package test;
//
//import controller.ConferenceManagerController;
//import domain.Attendee;
//import domain.ConferenceManager;
//import dto.ConferenceDTO;
//import dto.SessionDTO;
//import dto.SpeakerDTO;
//import enums.Role;
//import repository.*;
//import service.*;
//
//import java.time.LocalDateTime;
//import java.util.List;
//
//public class ConferenceManagerControllerTest {
//
//    public static void main(String[] args) {
//        try {
//            UserRepository userRepository = UserRepository.getInstance("data/users.json");
//            SessionRepository sessionRepository = SessionRepository.getInstance("data/sessions.json");
//            CertificateRepository certificateRepository = CertificateRepository.getInstance("data/certificates.json");
//            FeedbackRepository feedbackRepository = FeedbackRepository.getInstance("data/feedback.json");
//            ConferenceRepository conferenceRepository = ConferenceRepository.getInstance("data/conferences.json");
//            ReportRepository reportRepository = ReportRepository.getInstance("data/reports.json");
//
//
//            ConferenceService conferenceService = new ConferenceService(conferenceRepository);
//            FeedbackService feedbackService = new FeedbackService(feedbackRepository);
//            SessionService sessionService = new SessionService(sessionRepository, conferenceService, feedbackService);
//            feedbackService.setSessionService(sessionService);
//
//            CertificateService certificateService = new CertificateService(certificateRepository, sessionService); // Placeholder for other dependencies
//
//            SpeakerService speakerService = new SpeakerService(userRepository, sessionService, feedbackService);
//            sessionService.setSpeakerService(speakerService);
//
//            AttendeeService attendeeService = new AttendeeService(userRepository, sessionService, certificateService, feedbackService, conferenceService, speakerService);
//
//            ConferenceManagerService conferenceManagerService = new ConferenceManagerService(userRepository, conferenceService, sessionService, speakerService);
//
//            ReportService reportService = new ReportService(reportRepository, feedbackService, conferenceService, sessionService, attendeeService);
//            ConferenceManagerController conferenceManagerController = new ConferenceManagerController(conferenceManagerService);
//
//            // Test 1: Create a conference
//            ConferenceManager conferenceManager = new ConferenceManager(2, "Tony Stark", "tonystark@gmail.com", "jarvis", Role.MANAGER);
//            userRepository.save(conferenceManager);
//
//            Attendee attendee = new Attendee(3, "Peter Parker", "spiderman@gmail.com", "password123", Role.ATTENDEE);
//            userRepository.save(attendee);
//
//
//            System.out.println("Creating a new conference...");
//            ConferenceDTO conference = conferenceManagerController.createConference(
//                    "GAF-AI 2025",
//                    "A conference on AI advancements",
//                    LocalDateTime.of(2025, 5, 1, 9, 0),
//                    LocalDateTime.of(2025, 5, 5, 17, 0),
//                    2 // Manager ID
//            );
//            System.out.println("Conference created: " + conference);
//
//            // Test 5: Create a speaker
//            System.out.println("Creating a speaker...");
//            SpeakerDTO speaker = conferenceManagerController.createSpeakerAccount(
//                    2, // Manager ID
//                    "Dr. Alice Smith",
//                    "alice.smith@example.com",
//                    "securepassword",
//                    "Expert in AI Ethics",
//                    "AI Ethics and Philosophy",
//                    "Tech University"
//            );
//            System.out.println("Speaker created: " + speaker);
//
//            // Test 2: Create a session
//            System.out.println("Creating a session...");
//            SessionDTO session = conferenceManagerController.createSession(
//                    2, // Manager ID
//                    "AI in Healthcare",
//                    LocalDateTime.of(2025, 5, 2, 10, 0),
//                    "Room 101",
//                    50, // Capacity
//                    1, // Speaker ID
//                    "Discussing AI advancements in healthcare.",
//                    conference.getConferenceID() // Conference ID
//            );
//            System.out.println("Session created: " + session);
//
//            attendeeService.addSessionToSchedule(3, 1);
//
//            // Test 3: List all sessions in a conference
//            System.out.println("Listing all sessions for the conference...");
//            List<SessionDTO> sessions = conferenceManagerController.listSessionsByConference(conference.getConferenceID());
//            sessions.forEach(System.out::println);
//
//            // Test 4: View conference details
//            System.out.println("Viewing conference details...");
//            ConferenceDTO conferenceDetails = conferenceManagerController.viewConferenceDetails(conference.getConferenceID());
//            System.out.println("Conference details: " + conferenceDetails);
//
//            System.out.println("Marking attendance for a session...");
//            int sessionID = session.getSessionID();
//            int attendeeID = 3; // Assume attendee with ID 3 exists
//            conferenceManagerController.markAttendance(sessionID, attendeeID);
//            System.out.println("Attendance marked for attendee ID " + attendeeID + " in session ID " + sessionID);
//
//
//            // Test 6: Remove an attendee from a conference
//            //System.out.println("Removing an attendee from the conference...");
//            //conferenceManagerController.removeAttendeeFromConference(conference.getConferenceID(), 3); // Conference ID and Attendee ID
//            //System.out.println("Attendee removed from the conference.");
//
//            // Test 7: Send conference update
//            System.out.println("Sending conference update...");
//            conferenceManagerController.sendConferenceUpdate(conference.getConferenceID(), "Conference agenda updated!");
//            System.out.println("Conference update sent.");
//
//        } catch (Exception e) {
//            System.err.println("Error: " + e.getMessage());
//            e.printStackTrace();
//        }
//    }
//}
//
