//package test;
//
//import repository.CertificateRepository;
//import repository.SessionRepository;
//import repository.UserRepository;
//import repository.ConferenceRepository;
//import service.AttendeeService;
//import service.CertificateService;
//import service.SessionService;
//import service.ConferenceService;
//import utility.IDGenerator;
//
//import java.util.Set;
//
//public class CertificateServiceTest {
//    public static void main(String[] args) {
//        // Initialize repositories
//        CertificateRepository certificateRepository = CertificateRepository.getInstance("data/certificates.json");
//        SessionRepository sessionRepository = SessionRepository.getInstance("data/sessions.json");
//        UserRepository userRepository = UserRepository.getInstance("data/users.json");
//        ConferenceRepository conferenceRepository = ConferenceRepository.getInstance("data/conferences.json");
//
//        // Initialize services
//        ConferenceService conferenceService = new ConferenceService(conferenceRepository);
//        SessionService sessionService = new SessionService(sessionRepository, null, null);
//        AttendeeService attendeeService = new AttendeeService(userRepository, sessionService, null, null, null, null);
//        CertificateService certificateService = new CertificateService(certificateRepository, sessionService);
//        certificateService.setAttendeeService(attendeeService);
//
//        try {
//            // Test 1: Generate certificate for an eligible attendee
//            System.out.println("Generating certificate for attendee ID 1...");
//            var certificateDTO = certificateService.generateCertificate(1); // Attendee ID 1
//            System.out.println("Certificate generated: " + certificateDTO);
//
//            // Test 2: Retrieve the generated certificate
//            System.out.println("\nRetrieving the generated certificate...");
//            var retrievedCertificateDTO = certificateService.getCertificate(certificateDTO.getCertificateID());
//            System.out.println("Retrieved certificate: " + retrievedCertificateDTO);
//
//            // Test 3: Verify eligibility check (attendee not attending all scheduled sessions)
//            System.out.println("\nTesting certificate generation for ineligible attendee...");
//            try {
//                var ineligibleCertificateDTO = certificateService.generateCertificate(2); // Attendee ID 2
//                System.out.println("Certificate generated for ineligible attendee: " + ineligibleCertificateDTO);
//            } catch (IllegalArgumentException e) {
//                System.out.println("Expected error: " + e.getMessage());
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace(); // Print full stack trace for debugging
//        }
//    }
//}
//
