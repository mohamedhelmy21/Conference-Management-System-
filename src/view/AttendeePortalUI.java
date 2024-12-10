package view;

import controller.AttendeeController;
import controller.SpeakerController;
import controller.UserController;
import dto.*;
import enums.Rating;
import enums.Role;
import intializer.AppInitializer;
import service.AttendeeService;
import service.LoginService;
import service.SpeakerService;
import utility.LogoutHelper;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class AttendeePortalUI extends JFrame {
    private JTabbedPane tabbedPane;
    private JPanel mainPanel;
    private JTable conferenceScheduleTable;
    private JTable personalizedScheduleTable;
    private JButton registerButton;
    private JButton unregisterButton;
    private JButton viewSpeakerDetailsButton;
    private JButton viewSpeakerDetailsButtonMySchedule;
    private JComboBox<String> sessionDropdown;
    private JTextArea commentField;
    private JComboBox<Integer> ratingDropdown;
    private JButton submitFeedbackButton;
    private JLabel certificateHeader;
    private JTextArea certificateDetails;
    private JButton viewCertificateButton;
    private JLabel feedbackHeader;
    private JTable conferenceTable;
    private JButton signUpButton;
    private JButton logoutButton;
    private JLabel attendeeInfoLabel;
    private JLabel attendeeIDLabel;
    private JLabel attendeeNameLabel;
    private JLabel attendeeEmailLabel;

    private final LogoutHelper logoutHelper;

    private final AttendeeController attendeeController;
    private final UserController userController;
    private final int attendeeID;
    private final String attendeeName;
    private final String attendeeEmail;
    private int conferenceID;
    private UserDTO attendee;

    public AttendeePortalUI(AttendeeController attendeeController, UserController userController, UserDTO attendee) {
        this.logoutHelper = new LogoutHelper(userController);
        this.attendeeController = attendeeController;
        this.userController = userController;
        this.attendee = attendee;
        this.attendeeID = attendee.getUserID();
        this.attendeeName = attendee.getName();
        this.attendeeEmail = attendee.getEmail();
        this.conferenceID = attendeeController.getAttendeeConferenceID(attendeeID);



        setTitle("Attendee Portal");
        setContentPane(mainPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        initializeTabs();
        initializeListeners();
    }

    private void initializeTabs() {

        attendeeInfoLabel.setText("Logged in as: " + attendeeName + " (ID: " + attendeeID + ")");

        initializeProfileDetailsTab();

        // Initialize Conference Selection Table
        DefaultTableModel conferenceTableModel = new DefaultTableModel(
                new Object[]{"ID", "Name", "Description", "Start Date", "End Date"}, 0
        );
        conferenceTable.setModel(conferenceTableModel);
        loadAvailableConferences();

// Sign Up Button Action
        signUpButton.addActionListener(e -> signUpForConference());

        logoutButton.addActionListener(e -> logout());

        // Initialize Conference Schedule Table
        DefaultTableModel conferenceScheduleTableModel = new DefaultTableModel(
                new Object[]{"ID", "Name", "Speaker", "Date", "Time", "Room", "Description"}, 0
        );
        conferenceScheduleTable.setModel(conferenceScheduleTableModel);
        loadConferenceSchedule();

// Initialize Personalized Schedule Table
        DefaultTableModel personalizedTableModel = new DefaultTableModel(
                new Object[]{"ID", "Name", "Speaker", "Date", "Time", "Room", "Description"}, 0
        );
        personalizedScheduleTable.setModel(personalizedTableModel);
        loadPersonalizedSchedule();

        // Initialize Feedback Tab
        initializeFeedbackTab();

        // Initialize Certificate Tab
        certificateDetails.setEditable(false);

        if (conferenceID != -1) {
            // Load data if already signed up
            enableTabs();
            refreshUI();
        } else {
            // Disable tabs if not signed up
            disableTabs();
        }
    }

    private void initializeListeners() {

        sessionDropdown.addActionListener(e -> enableFeedbackButton());

        // Register for a Session
        registerButton.addActionListener(e -> registerForSession());

        // Unregister from a Session
        unregisterButton.addActionListener(e -> unregisterFromSession());

        // View Speaker Details (Conference Schedule)
        viewSpeakerDetailsButton.addActionListener(e -> viewSpeakerDetails(conferenceScheduleTable));

        // View Speaker Details (My Schedule)
        viewSpeakerDetailsButtonMySchedule.addActionListener(e -> viewSpeakerDetails(personalizedScheduleTable));

        // Submit Feedback
        submitFeedbackButton.addActionListener(e -> submitFeedback());

        // View Certificate
        viewCertificateButton.addActionListener(e -> viewCertificate());
    }

    private void loadAvailableConferences() {
        DefaultTableModel model = (DefaultTableModel) conferenceTable.getModel();
        model.setRowCount(0); // Clear existing rows
        List<ConferenceDTO> conferences = attendeeController.getAvailableConferences();
        for (ConferenceDTO conference : conferences) {
            model.addRow(new Object[]{
                    conference.getConferenceID(),
                    conference.getName(),
                    conference.getDescription(),
                    conference.getStartDate(),
                    conference.getEndDate()
            });
        }
    }


    private void loadConferenceSchedule() {
        if (conferenceID == -1) {
            return; // No conference selected
        }
        DefaultTableModel model = (DefaultTableModel) conferenceScheduleTable.getModel();
        model.setRowCount(0); // Clear existing rows
        List<SessionDTO> sessions = attendeeController.getAvailableSessions(conferenceID);
        for (SessionDTO session : sessions) {
            String speakerName = attendeeController.viewSpeakerName(session.getSpeakerID());
            model.addRow(new Object[]{
                    session.getSessionID(),
                    session.getName(),
                    speakerName,
                    session.getDateTime().toLocalDate(),
                    session.getDateTime().toLocalTime(),
                    session.getRoom(),
                    session.getDescription()
            });
        }
    }


    private void loadPersonalizedSchedule() {
        DefaultTableModel model = (DefaultTableModel) personalizedScheduleTable.getModel();
        model.setRowCount(0); // Clear existing rows
        ScheduleDTO schedule = attendeeController.viewSchedule(attendeeID);
        for (int sessionID : schedule.getSessionsIDs()) {
            SessionDTO session = attendeeController.viewSessionDetails(sessionID);
            String speakerName = attendeeController.viewSpeakerName(session.getSpeakerID());
            model.addRow(new Object[]{
                    session.getSessionID(),
                    session.getName(),
                    speakerName,
                    session.getDateTime().toLocalDate(),
                    session.getDateTime().toLocalTime(),
                    session.getRoom(),
                    session.getDescription()
            });
        }
    }

    private void initializeProfileDetailsTab() {

        // Add Labels for Profile Details
        attendeeIDLabel.setText("ID: " + attendeeID);
        attendeeNameLabel.setText("Name: " + attendeeName);
        attendeeEmailLabel.setText("Email: " + attendeeEmail);

    }


    private void initializeFeedbackTab() {
        // Load sessions into the dropdown
        sessionDropdown.removeAllItems();
        List<SessionDTO> sessions = attendeeController.getSessionsAttendedByAttendee(attendeeID);
        for (SessionDTO session : sessions) {
            sessionDropdown.addItem(session.getName());
        }

        // Initialize rating dropdown
        ratingDropdown.removeAllItems();
        for (int i = 1; i <= 5; i++) {
            ratingDropdown.addItem(i);
        }
    }

    private void logout() {
        logoutHelper.performLogout(this, attendeeID, attendeeName, attendeeEmail, Role.ATTENDEE);
    }

    private void signUpForConference() {
        int selectedRow = conferenceTable.getSelectedRow();

        if (conferenceID != -1) {
            JOptionPane.showMessageDialog(this, "You are already registered for a conference with ID: " + conferenceID, "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a conference to sign up.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int selectedConferenceID = (int) conferenceTable.getValueAt(selectedRow, 0);
        try {
            if (attendeeController.registerForConference(attendeeID, selectedConferenceID)) {
                this.conferenceID = selectedConferenceID;
                JOptionPane.showMessageDialog(this, "Successfully signed up for the conference!", "Success", JOptionPane.INFORMATION_MESSAGE);
                enableTabs();
                refreshUI();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

    }


    private void registerForSession() {
        int selectedRow = conferenceScheduleTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a session to register.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int sessionID = (int) conferenceScheduleTable.getValueAt(selectedRow, 0);
        if (attendeeController.addSessionToSchedule(attendeeID, sessionID)) {
            JOptionPane.showMessageDialog(this, "Successfully registered for the session!", "Success", JOptionPane.INFORMATION_MESSAGE);
            loadPersonalizedSchedule();
        } else {
            JOptionPane.showMessageDialog(this, "Failed to register for the session.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void unregisterFromSession() {
        int selectedRow = personalizedScheduleTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a session to unregister.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int sessionID = (int) personalizedScheduleTable.getValueAt(selectedRow, 0);
        if (attendeeController.removeSessionFromSchedule(attendeeID, sessionID)) {
            JOptionPane.showMessageDialog(this, "Successfully unregistered from the session.", "Success", JOptionPane.INFORMATION_MESSAGE);
            loadPersonalizedSchedule();
        } else {
            JOptionPane.showMessageDialog(this, "Failed to unregister from the session.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void viewSpeakerDetails(JTable table) {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a session to view speaker details.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int sessionID = (int) table.getValueAt(selectedRow, 0);
        SessionDTO session = attendeeController.viewSessionDetails(sessionID);
        String speakerBio = attendeeController.viewSpeakerBio(session.getSpeakerID());
        JOptionPane.showMessageDialog(this, speakerBio, "Speaker Details", JOptionPane.INFORMATION_MESSAGE);
    }

    private void submitFeedback() {
      try {  // Validate session selection
        if (sessionDropdown.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Please select a session before submitting feedback.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String sessionName = (String) sessionDropdown.getSelectedItem();
        int sessionID = attendeeController.getSessionIDByName(sessionName);
        String comments = commentField.getText();
        Rating rating = Rating.values()[ratingDropdown.getSelectedIndex()];
        boolean isAnonymous = false; // Extend if needed

        if (attendeeController.submitFeedback(attendeeID, sessionID, comments, rating, isAnonymous)) {
            JOptionPane.showMessageDialog(this, "Feedback submitted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);}

        } catch (IllegalArgumentException e) {
        // Handle specific errors like duplicate feedback
        JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.WARNING_MESSAGE);
        } catch (Exception e) {
        // Handle generic errors
        JOptionPane.showMessageDialog(this, "Failed to submit feedback: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void viewCertificate() {
        CertificateDTO certificate = attendeeController.getCertificateDetails(attendeeID);
        if (certificate == null) {
            JOptionPane.showMessageDialog(this, "No certificate available.", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            certificateDetails.setText(certificate.getCertificateText());
        }
    }

    private void enableTabs() {
        for (int i = 1; i < tabbedPane.getTabCount(); i++) {
            tabbedPane.setEnabledAt(i, true);
        }
    }

    private void disableTabs() {
        for (int i = 2; i < tabbedPane.getTabCount(); i++) {
            tabbedPane.setEnabledAt(i, false);
        }
    }

    private void refreshUI() {
        loadConferenceSchedule();
        loadPersonalizedSchedule();
    }

    private void enableFeedbackButton() {
        if (sessionDropdown.getSelectedItem() != null) {
            submitFeedbackButton.setEnabled(true);
        } else {
            submitFeedbackButton.setEnabled(false);
        }
    }

}
