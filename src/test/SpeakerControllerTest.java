//package test;
//
//import controller.AttendeeController;
//import controller.SpeakerController;
//import dto.FeedbackDTO;
//import dto.SessionDTO;
//import dto.SpeakerDTO;
//import enums.Role;
//import repository.*;
//import service.*;
//import utility.IDGenerator;
//
//import java.util.List;
//
//public class SpeakerControllerTest {
//    public static void main(String[] args) {
//        UserRepository userRepository = UserRepository.getInstance("data/users.json");
//        SessionRepository sessionRepository = SessionRepository.getInstance("data/sessions.json");
//        CertificateRepository certificateRepository = CertificateRepository.getInstance("data/certificates.json");
//        FeedbackRepository feedbackRepository = FeedbackRepository.getInstance("data/feedback.json");
//        ConferenceRepository conferenceRepository = ConferenceRepository.getInstance("data/conferences.json");
//        ReportRepository reportRepository = ReportRepository.getInstance("data/reports.json");
//
//
//        ConferenceService conferenceService = new ConferenceService(conferenceRepository);
//        FeedbackService feedbackService = new FeedbackService(feedbackRepository);
//        SessionService sessionService = new SessionService(sessionRepository, conferenceService, feedbackService);
//        feedbackService.setSessionService(sessionService);
//
//        CertificateService certificateService = new CertificateService(certificateRepository, sessionService); // Placeholder for other dependencies
//
//        SpeakerService speakerService = new SpeakerService(userRepository, sessionService, feedbackService);
//        sessionService.setSpeakerService(speakerService);
//
//        AttendeeService attendeeService = new AttendeeService(userRepository, sessionService, certificateService, feedbackService, conferenceService, speakerService);
//
//        ConferenceManagerService conferenceManagerService = new ConferenceManagerService(userRepository, conferenceService, sessionService, speakerService);
//
//        ReportService reportService = new ReportService(reportRepository, feedbackService, conferenceService, sessionService, attendeeService);
//
//        AttendeeController attendeeController = new AttendeeController(attendeeService);
//
//        // Initialize controller
//        SpeakerController speakerController = new SpeakerController(speakerService);
//
//        try {
//            System.out.println("Testing SpeakerController...");
//
//            // Register a new speaker
//            System.out.println("\nRegistering a new speaker...");
//            SpeakerDTO speaker = speakerController.registerSpeaker(
//                    "Dr. Sarah Johnson",
//                    "sarah.johnson@example.com",
//                    "securepassword",
//                    "Expert in Artificial Intelligence",
//                    "AI and Robotics",
//                    "Tech University"
//            );
//            System.out.println("Speaker registered: " + speaker);
//
//            // View speaker profile
//            System.out.println("\nViewing speaker profile...");
//            SpeakerDTO speakerProfile = speakerController.viewSpeakerProfile(speaker.getSpeakerID());
//            System.out.println("Speaker Profile: " + speakerProfile);
//
//            // Update speaker bio
//            System.out.println("\nUpdating speaker bio...");
//            speakerController.updateSpeakerBio(speaker.getSpeakerID(), "Renowned expert in AI and ML.");
//            System.out.println("Updated bio: " + speakerController.viewSpeakerBio(speaker.getSpeakerID()));
//
//            // Assign a session to the speaker
//            System.out.println("\nAssigning a session to the speaker...");
//            int sessionID = IDGenerator.generateId("Session");
//            speakerController.assignSessionToSpeaker(speaker.getSpeakerID(), sessionID);
//            System.out.println("Session assigned to speaker.");
//
//            // List speaker sessions
//            System.out.println("\nListing speaker sessions...");
//            List<SessionDTO> sessions = speakerController.listSpeakerSessions(speaker.getSpeakerID());
//            System.out.println("Speaker Sessions: " + sessions);
//
//            // View session feedback
//            System.out.println("\nViewing session feedback...");
//            List<FeedbackDTO> feedback = speakerController.viewSessionFeedback(sessionID);
//            System.out.println("Session Feedback: " + feedback);
//
//            // View session average rating
//            System.out.println("\nViewing session average rating...");
//            double avgRating = speakerController.viewSessionAverageRating(sessionID);
//            System.out.println("Average Rating: " + avgRating);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//}
//
