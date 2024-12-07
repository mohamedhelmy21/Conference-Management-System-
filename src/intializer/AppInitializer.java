package intializer;

import repository.*;
import service.*;

public class AppInitializer {
    private UserRepository userRepository;
    private SessionRepository sessionRepository;
    private CertificateRepository certificateRepository;
    private FeedbackRepository feedbackRepository;
    private ConferenceRepository conferenceRepository;
    private ReportRepository reportRepository;


    private SessionService sessionService;
    private CertificateService certificateService;
    private FeedbackService feedbackService;
    private ConferenceService conferenceService;
    private SpeakerService speakerService;
    private AttendeeService attendeeService;
    private ConferenceManagerService conferenceManagerService;
    private LoginService loginService;
    private ReportService reportService;

    public void initialize() {
        // Initialize repositories
        userRepository = UserRepository.getInstance("data/users.json");
        sessionRepository = SessionRepository.getInstance("data/sessions.json");
        certificateRepository = CertificateRepository.getInstance("data/certificates.json");
        feedbackRepository = FeedbackRepository.getInstance("data/feedback.json");
        conferenceRepository = ConferenceRepository.getInstance("data/conferences.json");
        reportRepository = ReportRepository.getInstance("data/reports.json");

        loginService = new LoginService(userRepository);
        conferenceService = new ConferenceService(conferenceRepository);
        feedbackService = new FeedbackService(feedbackRepository);
        sessionService = new SessionService(sessionRepository, conferenceService, feedbackService);
        feedbackService.setSessionService(sessionService);

        certificateService = new CertificateService(certificateRepository, sessionService); // Placeholder for other dependencies

        speakerService = new SpeakerService(userRepository, sessionService, feedbackService);
        sessionService.setSpeakerService(speakerService);

        attendeeService = new AttendeeService(userRepository, sessionService, certificateService, feedbackService, conferenceService, speakerService);

        conferenceManagerService = new ConferenceManagerService(userRepository, conferenceService, sessionService, speakerService);

        reportService = new ReportService(reportRepository, feedbackService, conferenceService, sessionService, attendeeService);
    }

    // Getters for services
    public LoginService getLoginService() {
        return loginService;
    }

    public AttendeeService getAttendeeService() {
        return attendeeService;
    }

    public SessionService getSessionService() {
        return sessionService;
    }

    public FeedbackService getFeedbackService() {
        return feedbackService;
    }

    public CertificateService getCertificateService() {
        return certificateService;
    }

    public ConferenceService getConferenceService() {
        return conferenceService;
    }

    public SpeakerService getSpeakerService() {
        return speakerService;
    }

    public ConferenceManagerService getConferenceManagerService() {
        return conferenceManagerService;
    }

    public ReportService getReportService() {
        return reportService;
    }
}

