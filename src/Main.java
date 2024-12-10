import controller.*;
import intializer.AppInitializer;
import service.*;
import view.LoginUI;
import dto.*;

public class Main {
    public static void main(String[] args) {
        AppInitializer appInitializer = new AppInitializer();
        appInitializer.initialize();
        LoginService loginService = appInitializer.getLoginService();
        UserController userController = new UserController(loginService);
        AttendeeService attendeeService = appInitializer.getAttendeeService();
        AttendeeController attendeeController = new AttendeeController(attendeeService);
        SpeakerService speakerService = appInitializer.getSpeakerService();
        SpeakerController speakerController = new SpeakerController(speakerService);
        ConferenceManagerService conferenceManagerService = appInitializer.getConferenceManagerService();
        ConferenceManagerController conferenceManagerController = new ConferenceManagerController(conferenceManagerService);
        SessionService sessionService = appInitializer.getSessionService();
        FeedbackService feedbackService = appInitializer.getFeedbackService();
        SessionController sessionController = new SessionController(sessionService, feedbackService);
        ReportService reportService = appInitializer.getReportService();
        ReportController reportController = new ReportController(reportService);
        ConferenceService conferenceService = appInitializer.getConferenceService();
        ConferenceController conferenceController = new ConferenceController(conferenceService);
        LoginUI loginUI = new LoginUI(userController);
        loginUI.attendeeController = attendeeController;
        loginUI.speakerController = speakerController;
        loginUI.conferenceManagerController = conferenceManagerController;
        loginUI.conferenceController = conferenceController;
        loginUI.sessionController = sessionController;
        loginUI.reportController = reportController;
    }
}