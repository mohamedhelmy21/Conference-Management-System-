package utility;

import intializer.AppInitializer;
import service.*;
import controller.*;
import dto.*;
import enums.Role;

import javax.swing.*;
import view.LoginUI;

public class LogoutHelper {
    private final UserController userController;

    public LogoutHelper(UserController userController) {
        this.userController = userController;
    }

    public void performLogout(JFrame currentFrame, int userID, String userName, String email, Role role) {
        try {
            UserDTO userDTO = new UserDTO(userID, userName, email, role);
            userController.logout(userDTO);

            currentFrame.dispose();

            // Reinitialize the application and show the Login UI
            AppInitializer appInitializer = new AppInitializer();
            appInitializer.initialize();

            LoginService loginService = appInitializer.getLoginService();
            UserController newUserController = new UserController(loginService);
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

            LoginUI loginUI = new LoginUI(newUserController);
            loginUI.attendeeController = attendeeController;
            loginUI.speakerController = speakerController;
            loginUI.conferenceManagerController = conferenceManagerController;
            loginUI.conferenceController = conferenceController;
            loginUI.sessionController = sessionController;
            loginUI.reportController = reportController;

            loginUI.setVisible(true);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(currentFrame, "Error logging out: " + ex.getMessage(),
                    "Logout Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}

