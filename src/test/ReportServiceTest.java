//package test;
//
//import domain.Report;
//import dto.ReportDTO;
//import repository.*;
//import service.*;
//
//public class ReportServiceTest {
//    public static void main(String[] args) {
//        try {
//            // Initialize repositories
//            ReportRepository reportRepository = ReportRepository.getInstance("data/reports.json");
//            UserRepository userRepository = UserRepository.getInstance("data/users.json");
//            SessionRepository sessionRepository = SessionRepository.getInstance("data/sessions.json");
//            ConferenceRepository conferenceRepository = ConferenceRepository.getInstance("data/conferences.json");
//            FeedbackRepository feedbackRepository = FeedbackRepository.getInstance("data/feedback.json");
//            CertificateRepository certificateRepository = CertificateRepository.getInstance("data/certificates.json");
//
//            // Step 2: Initialize services
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
//            AttendeeService attendeeService = new AttendeeService(
//                    userRepository,
//                    sessionService,
//                    certificateService,
//                    feedbackService,
//                    conferenceService,
//                    speakerService
//            );
//
//            ConferenceManagerService managerService = new ConferenceManagerService(
//                    userRepository,
//                    conferenceService,
//                    sessionService,
//                    speakerService
//            );
//
//            ReportService reportService = new ReportService(reportRepository, feedbackService, conferenceService, sessionService, attendeeService);
//
//            // Test: Generate Session Feedback Report
//            System.out.println("Generating Session Feedback Report...");
//            ReportDTO sessionReport = reportService.generateSessionFeedbackReport(1, "Manager");
//            System.out.println("Session Feedback Report:\n" + sessionReport.getContent());
//
//            // Test: Generate Conference Attendance Report
//            System.out.println("\nGenerating Conference Attendance Report...");
//            ReportDTO attendanceReport = reportService.generateConferenceAttendanceReport(1, "Manager");
//            System.out.println("Conference Attendance Report:\n" + attendanceReport.getContent());
//
//            // Test: Retrieve Report
//            System.out.println("\nRetrieving Report...");
//            ReportDTO retrievedReport = reportService.getReportById(sessionReport.getReportID());
//            System.out.println("Retrieved Report:\n" + retrievedReport.getContent());
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//}
//
