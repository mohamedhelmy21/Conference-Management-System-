package view;

import controller.AttendeeController;
import controller.SpeakerController;
import controller.UserController;
import dto.FeedbackDTO;
import dto.SessionDTO;
import dto.SpeakerDTO;
import dto.UserDTO;
import enums.Rating;
import enums.Role;
import intializer.AppInitializer;
import service.AttendeeService;
import service.LoginService;
import service.SpeakerService;
import utility.LogoutHelper;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class SpeakerPortalUI extends JFrame {
    private JPanel mainPanel;
    private JTabbedPane tabbedPane;
    private JTable assignedSessionsTable;
    private JLabel assignedSessionsHeaderLabel;
    private JComboBox<String> sessionDropdown;
    private JTable sessionFeedbackTable;
    private JLabel sessionFeedbackHeaderLabel;
    private JLabel averageRatingLabel;
    private JTextArea bioField;
    private JTextField expertiseField;
    private JTextField organizationField;
    private JButton saveProfileButton;
    private JLabel editProfileHeaderLabel;
    private JLabel speakerNameLabel;
    private JLabel speakerIDLabel;
    private JLabel speakerEmailLabel;
    private JButton logoutButton;

    private final LogoutHelper logoutHelper;
    private final SpeakerController speakerController;
    private final UserController userController;
    private int speakerID;
    private String speakerName;
    private String speakerEmail;
    private UserDTO speaker;

    public SpeakerPortalUI(SpeakerController speakerController, UserController userController, UserDTO speaker) {
        this.logoutHelper = new LogoutHelper(userController);
        this.speakerController = speakerController;
        this.userController = userController;
        this.speaker = speaker;
        this.speakerID = speaker.getUserID();
        this.speakerName = speaker.getName();
        this.speakerEmail = speaker.getEmail();

        setTitle("Speaker Portal");
        setContentPane(mainPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        initializeTabs();
        initializeListeners();
        loadProfileDetails();
    }

    private void initializeTabs() {
        loadAssignedSessions();
        populateSessionDropdown();
    }

    private void loadAssignedSessions() {
        DefaultTableModel model = new DefaultTableModel(new Object[]{"Session ID", "Name", "Date/Time", "Room", "Average Rating"}, 0);
        List<SessionDTO> sessions = speakerController.listSpeakerSessions(speakerID);
        for (SessionDTO session : sessions) {
            model.addRow(new Object[]{
                    session.getSessionID(),
                    session.getName(),
                    session.getDateTime(),
                    session.getRoom(),
                    speakerController.viewSessionAverageRating(session.getSessionID())
            });
        }
        assignedSessionsTable.setModel(model);
    }

    private void populateSessionDropdown() {
        List<SessionDTO> sessions = speakerController.listSpeakerSessions(speakerID);
        for (SessionDTO session : sessions) {
            sessionDropdown.addItem(session.getName());
        }
    }

    private void initializeListeners() {
        sessionDropdown.addActionListener(e -> loadSessionFeedback());
        saveProfileButton.addActionListener(e -> saveProfileDetails());
        logoutButton.addActionListener(e -> logout());
    }

    private void loadSessionFeedback() {
        String sessionName = (String) sessionDropdown.getSelectedItem();
        if (sessionName == null) return;

        List<SessionDTO> sessions = speakerController.listSpeakerSessions(speakerID);
        int sessionID = sessions.stream()
                .filter(session -> session.getName().equals(sessionName))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Session not found"))
                .getSessionID();

        List<FeedbackDTO> feedbackList = speakerController.viewSessionFeedback(sessionID);
        DefaultTableModel model = new DefaultTableModel(new Object[]{"Comments", "Rating", "Anonymous"}, 0);
        for (FeedbackDTO feedback : feedbackList) {
            model.addRow(new Object[]{
                    feedback.getComments(),
                    feedback.getRating(),
                    feedback.isAnonymous()
            });
        }
        sessionFeedbackTable.setModel(model);
        averageRatingLabel.setText("Average Rating: " + speakerController.viewSessionAverageRating(sessionID));
    }

    private void saveProfileDetails() {
        String newBio = bioField.getText();
        String newExpertise = expertiseField.getText();
        String newOrganization = organizationField.getText();
        speakerController.updateSpeakerBio(speakerID, newBio);
        speakerController.updateSpeakerExpertise(speakerID, newExpertise);
        speakerController.updateSpeakerOrganization(speakerID, newOrganization);
        JOptionPane.showMessageDialog(this, "Profile updated successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    private void loadProfileDetails() {
        SpeakerDTO speaker = speakerController.viewSpeakerProfile(speakerID);
        speakerNameLabel.setText("Name: " + speaker.getName());
        speakerIDLabel.setText("ID: " + speaker.getSpeakerID());
        speakerEmailLabel.setText("Email: " + speaker.getEmail());
        bioField.setText(speaker.getBio());
        expertiseField.setText(speaker.getExpertise());
        organizationField.setText(speaker.getOrganization());
    }

    private void logout() {
        logoutHelper.performLogout(this, speakerID, speakerName, speakerEmail, Role.SPEAKER);
    }
}


