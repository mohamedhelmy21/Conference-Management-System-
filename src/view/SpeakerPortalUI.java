package view;

import controller.SpeakerController;
import controller.UserController;
import dto.FeedbackDTO;
import dto.SessionDTO;
import dto.SpeakerDTO;
import dto.UserDTO;
import enums.Rating;
import enums.Role;

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

    private final SpeakerController speakerController;
    private final UserController userController;
    private final int speakerID;
    private final String speakerName;

    public SpeakerPortalUI(SpeakerController speakerController, UserController userController, int speakerID, String speakerName) {
        this.speakerController = speakerController;
        this.userController = userController;
        this.speakerID = speakerID;
        this.speakerName = speakerName;

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
        speakerController.updateSpeakerBio(speakerID, newBio);
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

    private void logout(){
        try {
            // Create a temporary UserDTO with attendee details
            UserDTO userDTO = new UserDTO(speakerID, speakerName, "", Role.SPEAKER);

            // Perform logout
            userController.logout(userDTO);

            // Close the Attendee UI
            dispose();

            // Redirect to Login UI
            LoginUI loginUI = new LoginUI(userController);
            loginUI.speakerController = speakerController;
            loginUI.setVisible(true);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(SpeakerPortalUI.this, "Error logging out: " + ex.getMessage(),
                    "Logout Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}


