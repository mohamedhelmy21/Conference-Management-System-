package view;

import controller.*;
import dto.*;
import enums.Role;
import utility.LogoutHelper;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ManagerPortalUI extends JFrame {

    private JTabbedPane tabbedPane;
    private JPanel manageConferencesPanel;
    private JPanel manageSessionsPanel;
    private JPanel manageSpeakersPanel;
    private JPanel manageAttendeesPanel;
    private JPanel reportsPanel;
    private JPanel profilePanel;


    // Manage Conferences Fields
    private JTextField txtConferenceName;
    private JTextField txtConferenceDescription;
    private JSpinner spinnerStartDate;
    private JSpinner spinnerEndDate;
    private JButton createConferenceButton;
    private JTable conferencesTable;
    private JButton updateConferenceButton;
    private JButton deleteConferenceButton;

    // Manage Sessions Fields
    private JTextField txtSessionName;
    private JTextField txtSessionDescription;
    private JSpinner spinnerSessionDateTime;
    private JTextField txtSessionRoom;
    private JPanel mainPanel;
    private JTextField txtSessionCapacity;
    private JComboBox<String> comboSessionSpeaker;
    private JComboBox<String> comboSessionConference;
    private JButton createSessionButton;
    private JTable sessionsTable;
    private JButton deleteSessionButton;
    private JButton updateSessionButton;

    // Manage Speakers Fields
    private JTextField txtSpeakerName;
    private JTextField txtSpeakerEmail;
    private JTextField txtSpeakerPassword;
    private JTextField txtSpeakerBio;
    private JTextField txtSpeakerExpertise;
    private JTextField txtSpeakerOrganization;
    private JButton createSpeakerButton;
    private JTable speakersTable;
    private JButton editSpeakerButton;
    private JButton deleteSpeakerButton;


    // Manage Attendees Fields
    private JComboBox<String> conferenceDropdown;
    private JComboBox<String> sessionDropdown;
    private JTable sessionAttendeesTable;
    private JButton markAttendanceButton;
    private JTable conferenceAttendeesTable;
    private JButton removeAttendeeButton;

    // Reports Fields
    private JButton generateFeedbackReportButton;
    private JButton generateAttendanceReportButton;
    private JTable reportsTable;
    private JComboBox reportConferenceDropdown;
    private JComboBox reportSessionDropdown;
    private JButton generateSessionAttendanceReportButton;

    // Profile Fields
    private JLabel lblManagerID;
    private JLabel lblManagerName;
    private JLabel lblManagerEmail;
    private JButton logoutButton;
    private JButton viewReportButton;
    private JButton generateCertificatesButton;


    // Controllers
    private final LogoutHelper logoutHelper;
    private ConferenceManagerController conferenceManagerController;
    private ConferenceController conferenceController;
    private SessionController sessionController;
    private SpeakerController speakerController;
    private ReportController reportController;
    private AttendeeController attendeeController;
    private CertificateController certificateController;
    private UserDTO manager;

    private int managerID;
    private String managerName;
    private String managerEmail;

    public ManagerPortalUI(UserController userController, ConferenceManagerController conferenceManagerController,
                           ConferenceController conferenceController,
                           SessionController sessionController, SpeakerController speakerController,
                           ReportController reportController, AttendeeController attendeeController,
                           CertificateController certificateController,
                           UserDTO manager) {
        this.logoutHelper = new LogoutHelper(userController);
        this.conferenceManagerController = conferenceManagerController;
        this.conferenceController = conferenceController;
        this.sessionController = sessionController;
        this.speakerController = speakerController;
        this.reportController = reportController;
        this.attendeeController = attendeeController;
        this.certificateController = certificateController;
        this.manager = manager;
        this.managerID = manager.getUserID();
        this.managerName = manager.getName();
        this.managerEmail = manager.getEmail();

        setTitle("Manager Portal");
        setSize(1200, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initializeUI();
    }

    private void initializeUI() {
        // Initialize all panels
        initializeManageConferencesPanel();
        initializeManageSessionsPanel();
        initializeManageSpeakersPanel();
        initializeManageAttendeesPanel();
        initializeReportsPanel();
        initializeProfilePanel(managerID, managerName, managerEmail);

        add(tabbedPane);
    }

    private void initializeManageConferencesPanel() {
        spinnerStartDate.setModel(new SpinnerDateModel());

        spinnerEndDate.setModel(new SpinnerDateModel());

        createConferenceButton.setText("Create Conference");

        conferencesTable.setModel(new DefaultTableModel(
                new Object[]{"ID", "Name", "Start Date", "End Date", "Actions"}, 0));

        createConferenceButton.addActionListener(e -> createConference());
        updateConferenceButton.addActionListener(e -> updateSelectedConference());
        deleteConferenceButton.addActionListener(e -> deleteSelectedConference());

        // Load initial data
        loadConferencesIntoTable();
    }

    // Create Conference
    private void createConference() {
        try {
            String name = txtConferenceName.getText();
            String description = txtConferenceDescription.getText();
            LocalDateTime startDate = LocalDateTime.ofInstant(((Date) spinnerStartDate.getValue()).toInstant(), ZoneId.systemDefault());
            LocalDateTime endDate = LocalDateTime.ofInstant(((Date) spinnerEndDate.getValue()).toInstant(), ZoneId.systemDefault());

            conferenceManagerController.createConference(name, description, startDate, endDate, managerID);

            JOptionPane.showMessageDialog(this, "Conference created successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            loadConferencesIntoTable();
            loadConferencesIntoDropdown();
            populateConferenceDropdown();
            refreshReportDropdowns();
            clearFormFields();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error creating conference: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateSelectedConference() {
        try {
            int selectedRow = conferencesTable.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Please select a conference to update.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Retrieve the selected conference ID
            int conferenceID = (int) conferencesTable.getValueAt(selectedRow, 0);

            // Fetch conference details
            ConferenceDTO conference = conferenceManagerController.viewConferenceDetails(conferenceID);
            if (conference == null) {
                JOptionPane.showMessageDialog(this, "Conference not found.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Show Update Dialog
            JDialog updateDialog = new JDialog(this, "Update Conference", true);
            updateDialog.setLayout(new GridLayout(5, 2, 10, 10));
            updateDialog.setSize(400, 300);

            // Form Fields
            JTextField txtName = new JTextField(conference.getName());
            JTextField txtDescription = new JTextField(conference.getDescription());

            // Convert LocalDateTime to java.util.Date for Spinners
            Date startDate = Date.from(conference.getStartDate().atZone(ZoneId.systemDefault()).toInstant());
            Date endDate = Date.from(conference.getEndDate().atZone(ZoneId.systemDefault()).toInstant());

            JSpinner spinnerStart = new JSpinner(new SpinnerDateModel(startDate, null, null, Calendar.DAY_OF_MONTH));
            JSpinner spinnerEnd = new JSpinner(new SpinnerDateModel(endDate, null, null, Calendar.DAY_OF_MONTH));

            updateDialog.add(new JLabel("Conference Name:"));
            updateDialog.add(txtName);

            updateDialog.add(new JLabel("Description:"));
            updateDialog.add(txtDescription);

            updateDialog.add(new JLabel("Start Date:"));
            updateDialog.add(spinnerStart);

            updateDialog.add(new JLabel("End Date:"));
            updateDialog.add(spinnerEnd);

            // Save Button
            JButton saveButton = new JButton("Save");
            updateDialog.add(new JLabel()); // Spacer
            updateDialog.add(saveButton);

            saveButton.addActionListener(e -> {
                try {
                    String updatedName = txtName.getText();
                    String updatedDescription = txtDescription.getText();

                    // Convert java.util.Date back to LocalDateTime
                    LocalDateTime updatedStartDate = ((Date) spinnerStart.getValue()).toInstant()
                            .atZone(ZoneId.systemDefault())
                            .toLocalDateTime();
                    LocalDateTime updatedEndDate = ((Date) spinnerEnd.getValue()).toInstant()
                            .atZone(ZoneId.systemDefault())
                            .toLocalDateTime();

                    // Update the conference
                    conferenceController.updateConference(conferenceID, updatedName, updatedDescription, updatedStartDate, updatedEndDate);
                    JOptionPane.showMessageDialog(this, "Conference updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);

                    // Refresh table and close dialog
                    loadConferencesIntoTable();
                    updateDialog.dispose();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(updateDialog, "Error saving changes: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            });

            // Show Dialog
            updateDialog.setLocationRelativeTo(this);
            updateDialog.setVisible(true);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error updating conference: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }



    // Delete Conference
    private void deleteSelectedConference() {
        try {
            int selectedRow = conferencesTable.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Please select a conference to delete.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int conferenceID = (int) conferencesTable.getValueAt(selectedRow, 0);

            int confirm = JOptionPane.showConfirmDialog(this,
                    "Are you sure you want to delete this conference?",
                    "Confirm Delete",
                    JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                conferenceController.deleteConference(conferenceID);
                JOptionPane.showMessageDialog(this, "Conference deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                loadConferencesIntoTable();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error deleting conference: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Load Conferences into Table
    private void loadConferencesIntoTable() {
        try {
            DefaultTableModel model = (DefaultTableModel) conferencesTable.getModel();
            model.setRowCount(0); // Clear existing rows
            List<ConferenceDTO> conferences = conferenceController.listAllConferences();

            for (ConferenceDTO conference : conferences) {
                model.addRow(new Object[]{
                        conference.getConferenceID(),
                        conference.getName(),
                        conference.getStartDate(),
                        conference.getEndDate()
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading conferences: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Clear Form Fields
    private void clearFormFields() {
        txtConferenceName.setText("");
        txtConferenceDescription.setText("");
        spinnerStartDate.setValue(new Date());
        spinnerEndDate.setValue(new Date());
    }


    private void initializeManageSessionsPanel() {
        comboSessionConference.addActionListener(e -> loadConferenceSessions());

        createSessionButton.addActionListener(e -> createSession());

        spinnerSessionDateTime.setModel( new SpinnerDateModel());
        loadSpeakersIntoCombo(comboSessionSpeaker);
        sessionsTable.setModel(new DefaultTableModel(
                new Object[]{"ID", "Name", "Date & Time", "Room", "Speaker", "Capacity"}, 0));

        updateSessionButton.addActionListener(e -> updateSelectedSession());
        deleteSessionButton.addActionListener(e -> deleteSelectedSession());

        // Load initial data
        loadConferencesIntoDropdown();
    }

    // Load Conferences into Dropdown
    private void loadConferencesIntoDropdown() {
        comboSessionConference.removeAllItems();
        List<ConferenceDTO> conferences = conferenceController.listAllConferences();
        for (ConferenceDTO conference : conferences) {
            comboSessionConference.addItem("ID: " + conference.getConferenceID() + " - " + conference.getName());
        }
    }

    // Load Sessions for Selected Conference
    private void loadConferenceSessions() {
        if (comboSessionConference.getSelectedItem() == null) return;

        int conferenceID = getSelectedConferenceID();
        List<SessionDTO> sessions = conferenceManagerController.listSessionsByConference(conferenceID);

        DefaultTableModel model = (DefaultTableModel) sessionsTable.getModel();
        model.setRowCount(0); // Clear existing rows

        for (SessionDTO session : sessions) {
            String speakerName = speakerController.viewSpeakerProfile(session.getSpeakerID()).getName();
            model.addRow(new Object[]{
                    session.getSessionID(), session.getName(), session.getDateTime(),
                    session.getRoom(), speakerName, session.getCapacity()
            });
        }
    }

    // Get Selected Conference ID
    private int getSelectedConferenceID() {
        try {
            String selectedConference = (String) comboSessionConference.getSelectedItem();
            System.out.println("Selected Conference: " + selectedConference); // Debugging
            if (selectedConference == null || selectedConference.isEmpty()) {
                throw new IllegalArgumentException("No conference selected.");
            }

            // Assuming the format is "ID: [number] - Conference Name"
            String[] parts = selectedConference.split(":");
            if (parts.length < 2) {
                throw new IllegalArgumentException("Invalid conference format.");
            }

            String idPart = parts[1].trim().split("-")[0].trim(); // Extract the ID part
            return Integer.parseInt(idPart);
        } catch (Exception e) {
            System.err.println("Error parsing conference ID: " + e.getMessage());
            return -1; // Default error value
        }
    }


    // Create Session
    private void createSession() {
        try {
            String name = txtSessionName.getText();
            LocalDateTime dateTime = ((Date) spinnerSessionDateTime.getValue()).toInstant()
                    .atZone(ZoneId.systemDefault()).toLocalDateTime();
            String description = txtSessionDescription.getText();
            String room = txtSessionRoom.getText();
            int capacity = Integer.parseInt(txtSessionCapacity.getText());
            int speakerID = getSelectedSpeakerID();
            int conferenceID = getSelectedConferenceID();

            conferenceManagerController.createSession(
                    managerID, name, dateTime, room, capacity, speakerID, description, conferenceID
            );
            JOptionPane.showMessageDialog(this, "Session created successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            loadConferenceSessions();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error creating session: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Get Selected Speaker ID
    private int getSelectedSpeakerID() {
        String selectedSpeaker = (String) comboSessionSpeaker.getSelectedItem();

        if (selectedSpeaker == null || selectedSpeaker.isEmpty()) {
            throw new IllegalArgumentException("Please select a speaker.");
        }

        // Extract numeric ID from the formatted string
        try {
            return Integer.parseInt(selectedSpeaker.replaceAll("[^0-9]", ""));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid speaker selection format.");
        }
    }


    // Update Selected Session
    private void updateSelectedSession() {
        try {
            int selectedRow = sessionsTable.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Please select a session to update.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int sessionID = (int) sessionsTable.getValueAt(selectedRow, 0);
            String speakerName = (String) sessionsTable.getValueAt(selectedRow, 4);
            SessionDTO session = sessionController.viewSessionDetails(sessionID);

            JDialog updateDialog = new JDialog(this, "Update Session", true);
            updateDialog.setLayout(new GridLayout(7, 2, 10, 10));
            updateDialog.setSize(400, 300);

            // Populate Form
            JTextField txtName = new JTextField(session.getName());
            JSpinner spinnerDateTime = new JSpinner(new SpinnerDateModel(
                    Date.from(session.getDateTime().atZone(ZoneId.systemDefault()).toInstant()), null, null, Calendar.DAY_OF_MONTH
            ));
            JTextField txtRoom = new JTextField(session.getRoom());
            JTextField txtCapacity = new JTextField(String.valueOf(session.getCapacity()));
            JLabel lblSpeaker = new JLabel(speakerName);
            JTextField txtDescription = new JTextField(session.getDescription());

            updateDialog.add(new JLabel("Session Name:"));
            updateDialog.add(txtName);

            updateDialog.add(new JLabel("Date & Time:"));
            updateDialog.add(spinnerDateTime);

            updateDialog.add(new JLabel("Room:"));
            updateDialog.add(txtRoom);

            updateDialog.add(new JLabel("Capacity:"));
            updateDialog.add(txtCapacity);

            updateDialog.add(new JLabel("Speaker:"));
            updateDialog.add(lblSpeaker);

            updateDialog.add(new JLabel("Description:"));
            updateDialog.add(txtDescription);

            JButton saveButton = new JButton("Save");
            updateDialog.add(saveButton);

            saveButton.addActionListener(e -> {
                try {
                    // Collect Updated Data
                    String name = txtName.getText();
                    LocalDateTime dateTime = ((Date) spinnerDateTime.getValue()).toInstant()
                            .atZone(ZoneId.systemDefault()).toLocalDateTime();
                    String room = txtRoom.getText();
                    int capacity = Integer.parseInt(txtCapacity.getText());
                    int speakerID = getSelectedSpeakerID();
                    String description = txtDescription.getText();

                    // Update Session
                    sessionController.updateSession(sessionID, name, dateTime, room, capacity, description);
                    JOptionPane.showMessageDialog(this, "Session updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    loadConferenceSessions();
                    updateDialog.dispose();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(updateDialog, "Error updating session: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            });

            updateDialog.setVisible(true);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error updating session: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Delete Selected Session
    private void deleteSelectedSession() {
        try {
            int selectedRow = sessionsTable.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Please select a session to delete.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int sessionID = (int) sessionsTable.getValueAt(selectedRow, 0);
            int confirm = JOptionPane.showConfirmDialog(this,
                    "Are you sure you want to delete this session?",
                    "Confirm Delete",
                    JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                conferenceManagerController.deleteSession(managerID, sessionID, getSelectedConferenceID());
                JOptionPane.showMessageDialog(this, "Session deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                loadConferenceSessions();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error deleting session: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Load Speakers into ComboBox
    private void loadSpeakersIntoCombo(JComboBox<String> comboSpeaker) {
        comboSpeaker.removeAllItems();
        List<SpeakerDTO> speakers = speakerController.listAllSpeakers();
        for (SpeakerDTO speaker : speakers) {
            comboSpeaker.addItem(speaker.getName() + " (ID: " + speaker.getSpeakerID() + ")");
        }
    }

    private void initializeManageSpeakersPanel() {

        createSpeakerButton.addActionListener(e -> createSpeaker());
        deleteSpeakerButton.addActionListener(e -> deleteSelectedSpeaker());
        editSpeakerButton.addActionListener(e -> editSelectedSpeaker());
        loadSpeakersIntoTable();
    }

    private void createSpeaker() {
        try {
            String name = txtSpeakerName.getText();
            String email = txtSpeakerEmail.getText();
            String password = txtSpeakerPassword.getText();
            String bio = txtSpeakerBio.getText();
            String expertise = txtSpeakerExpertise.getText();
            String organization = txtSpeakerOrganization.getText();

            // Validation
            if (name.isEmpty() || email.isEmpty() || password.isEmpty() || bio.isEmpty() || expertise.isEmpty() || organization.isEmpty()) {
                throw new IllegalArgumentException("All fields are required.");
            }

            conferenceManagerController.createSpeakerAccount(managerID, name, email, password, bio, expertise, organization);

            JOptionPane.showMessageDialog(this, "Speaker created successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            loadSpeakersIntoTable();
            loadSpeakersIntoCombo(comboSessionSpeaker);
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Input Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error creating speaker: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadSpeakersIntoTable() {
        DefaultTableModel model = new DefaultTableModel(new Object[]{"ID", "Name", "Email", "Bio", "Expertise", "Organization"}, 0);
        speakersTable.setModel(model);

        try {
            List<SpeakerDTO> speakers = speakerController.listAllSpeakers();
            for (SpeakerDTO speaker : speakers) {
                model.addRow(new Object[]{
                        speaker.getSpeakerID(),
                        speaker.getName(),
                        speaker.getEmail(),
                        speaker.getBio(),
                        speaker.getExpertise(),
                        speaker.getOrganization(),
                });
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading speakers: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private boolean showUpdateSpeakerDialog(int speakerID) {
        SpeakerDTO speaker = speakerController.viewSpeakerProfile(speakerID);


        JTextField bioField = new JTextField(speaker.getBio());
        JTextField expertiseField = new JTextField(speaker.getExpertise());
        JTextField organizationField = new JTextField(speaker.getOrganization());

        Object[] message = {
                "Name:", speaker.getName(),
                "Email:", speaker.getEmail(),
                "Bio:", bioField,
                "Expertise:", expertiseField,
                "Organization:", organizationField
        };

        int option = JOptionPane.showConfirmDialog(this, message, "Update Speaker", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                speakerController.updateSpeakerBio(speakerID, bioField.getText());
                speakerController.updateSpeakerExpertise(speakerID, expertiseField.getText());
                speakerController.updateSpeakerOrganization(speakerID, organizationField.getText());
                JOptionPane.showMessageDialog(this, "Speaker updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                return true;
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error updating speaker: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

        return false;
    }

    private void editSelectedSpeaker() {
        int selectedRow = speakersTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a speaker to edit.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Fetch the speaker ID from the selected row
        int speakerID = (int) speakersTable.getValueAt(selectedRow, 0);

        // Call the showUpdateDialog method and pass the speaker ID
        boolean updated = showUpdateSpeakerDialog(speakerID);

        // If the dialog returns true (indicating an update was made), refresh the table
        if (updated) {
            loadSpeakersIntoTable();
            JOptionPane.showMessageDialog(this, "Speaker updated successfully.");
        }
    }


    private void deleteSelectedSpeaker() {
        int selectedRow = speakersTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a speaker to delete.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int speakerID = (int) speakersTable.getValueAt(selectedRow, 0);
        int confirmation = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to delete this speaker and all their sessions?",
                "Confirm Deletion",
                JOptionPane.YES_NO_OPTION);

        if (confirmation == JOptionPane.YES_OPTION) {
            speakerController.deleteSpeaker(speakerID);
            loadSpeakersIntoTable();
            JOptionPane.showMessageDialog(this, "Speaker deleted successfully.");
        }
    }

    private void initializeManageAttendeesPanel() {

        // Session Attendees Table
        sessionAttendeesTable.setModel(new DefaultTableModel(new Object[]{"Attendee ID", "Name", "Email", "Attendance"}, 0) {
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                return columnIndex == 3 ? Boolean.class : String.class;
            }
        });


        conferenceAttendeesTable.setModel(new DefaultTableModel(new Object[]{"Attendee ID", "Name", "Email"}, 0));

        // Populate Conference Dropdown and Add Listeners
        populateConferenceDropdown();

        conferenceDropdown.addActionListener(e -> handleConferenceSelection());
        sessionDropdown.addActionListener(e -> handleSessionSelection());
        markAttendanceButton.addActionListener(e -> markAttendance());
        removeAttendeeButton.addActionListener(e -> removeAttendeeFromConference());
        generateCertificatesButton.addActionListener(e -> {
            try {
                int selectedConferenceID = getSelectedConferenceID();
                certificateController.generateCertificatesForConference(selectedConferenceID);
                JOptionPane.showMessageDialog(this, "Certificates generated successfully for conference ID: " + selectedConferenceID,
                        "Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (IllegalArgumentException ex) {
                if (ex.getMessage().contains("already generated")) {
                    JOptionPane.showMessageDialog(this, "Certificates have already been generated for this conference.",
                            "Information", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "Error generating certificates: " + ex.getMessage(),
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error generating certificates: " + ex.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        });

    }

    // Populate Conference Dropdown
    private void populateConferenceDropdown() {
        conferenceDropdown.removeAllItems();
        List<ConferenceDTO> conferences = conferenceController.listAllConferences();
        for (ConferenceDTO conference : conferences) {
            conferenceDropdown.addItem("ID: " + conference.getConferenceID() + " - " + conference.getName());
        }
    }

    // Handle Conference Selection
    private void handleConferenceSelection() {
        String selectedConference = (String) conferenceDropdown.getSelectedItem();
        if (selectedConference != null) {
            int conferenceID = Integer.parseInt(selectedConference.split(":")[1].trim().split(" ")[0]);
            populateSessionDropdown(conferenceID);
            refreshConferenceAttendeesTable(conferenceID);
        }
    }

    // Populate Session Dropdown
    private void populateSessionDropdown(int conferenceID) {
        sessionDropdown.removeAllItems();
        List<SessionDTO> sessions = sessionController.listSessionsByConference(conferenceID);
        for (SessionDTO session : sessions) {
            sessionDropdown.addItem("ID: " + session.getSessionID() + " - " + session.getName());
        }
    }

    // Handle Session Selection
    private void handleSessionSelection() {
        String selectedSession = (String) sessionDropdown.getSelectedItem();
        if (selectedSession != null) {
            int sessionID = Integer.parseInt(selectedSession.split(":")[1].trim().split(" ")[0]);
            refreshSessionAttendeesTable(sessionID);
        }
    }

    // Refresh Session Attendees Table
    private void refreshSessionAttendeesTable(int sessionID) {
        DefaultTableModel model = (DefaultTableModel) sessionAttendeesTable.getModel();
        model.setRowCount(0); // Clear table
        List<Integer> attendeeIDs = sessionController.getSignedUpAttendees(sessionID);
        for (int attendeeID : attendeeIDs) {
            String attendeeName = attendeeController.getAttendeeName(attendeeID);
            String attendeeEmail = attendeeController.getAttendeeEmail(attendeeID);
            model.addRow(new Object[]{attendeeID, attendeeName, attendeeEmail, false}); // Unchecked by default
        }
    }

    // Refresh Conference Attendees Table
    private void refreshConferenceAttendeesTable(int conferenceID) {
        DefaultTableModel model = (DefaultTableModel) conferenceAttendeesTable.getModel();
        model.setRowCount(0); // Clear table
        List<Integer> attendeeIDs = conferenceController.getConferenceAttendees(conferenceID);
        for (int attendeeID : attendeeIDs) {
            String attendeeName = attendeeController.getAttendeeName(attendeeID);
            String attendeeEmail = attendeeController.getAttendeeEmail(attendeeID);
            model.addRow(new Object[]{attendeeID, attendeeName, attendeeEmail});
        }
    }

    private void markAttendance() {
        String selectedSession = (String) sessionDropdown.getSelectedItem();
        if (selectedSession == null) {
            JOptionPane.showMessageDialog(this, "Please select a session.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int sessionID = Integer.parseInt(selectedSession.split(":")[1].trim().split(" ")[0]);
        DefaultTableModel model = (DefaultTableModel) sessionAttendeesTable.getModel();

        for (int i = 0; i < model.getRowCount(); i++) {
            boolean attended = (boolean) model.getValueAt(i, 3); // Checkbox value
            int attendeeID = (int) model.getValueAt(i, 0); // Attendee ID

            // Ensure attendance is only marked once
            if (attended && !sessionController.hasAttendeeMarkedAttendance(sessionID, attendeeID)) {
                conferenceManagerController.markAttendance(sessionID, attendeeID);
            }
        }

        JOptionPane.showMessageDialog(this, "Attendance updated successfully!");
        refreshSessionAttendeesTable(sessionID);
    }

    // Remove Attendee
    private void removeAttendeeFromConference() {
        int selectedRow = conferenceAttendeesTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select an attendee to remove.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int attendeeID = (int) conferenceAttendeesTable.getValueAt(selectedRow, 0);
        String selectedConference = (String) conferenceDropdown.getSelectedItem();
        int conferenceID = Integer.parseInt(selectedConference.split(":")[1].trim().split(" ")[0]);

        // Remove attendee from the conference and update their conference ID
        conferenceManagerController.removeAttendeeFromConference(conferenceID, attendeeID);
        attendeeController.updateAttendeeConferenceID(attendeeID, -1);

        JOptionPane.showMessageDialog(this, "Attendee removed successfully!");
        refreshConferenceAttendeesTable(conferenceID);
    }

    private void initializeReportsPanel() {

        reportsTable.setModel(new DefaultTableModel(new Object[]{"ID", "Type", "Generated Date"}, 0));

        // Event Listeners
        refreshReportDropdowns();
        initializeReportActions();
        refreshReportsTable();

    }

    // Initialize Event Listeners for Reports Tab
    private void initializeReportActions() {
        // Generate Feedback Report
        generateFeedbackReportButton.addActionListener(e -> generateFeedbackReport());

        // Generate Session Attendance Report
        generateSessionAttendanceReportButton.addActionListener(e -> generateSessionAttendanceReport());


        // Generate Attendance Report
        generateAttendanceReportButton.addActionListener(e -> generateConferenceAttendanceReport());

        // View Selected Report
        viewReportButton.addActionListener(e -> viewSelectedReport());
    }

    // Refresh Dropdowns
    private void refreshReportDropdowns() {
        try {
            // Remove existing listeners to avoid conflicts
            for (ActionListener listener : reportConferenceDropdown.getActionListeners()) {
                reportConferenceDropdown.removeActionListener(listener);
            }

            // Populate Conference Dropdown
            List<ConferenceDTO> conferences = conferenceController.listAllConferences();
            reportConferenceDropdown.removeAllItems();
            for (ConferenceDTO conference : conferences) {
                reportConferenceDropdown.addItem(conference.getName() + " (ID: " + conference.getConferenceID() + ")");
            }

            // Add ActionListener
            reportConferenceDropdown.addActionListener(e -> {
                try {
                    if (reportConferenceDropdown.getSelectedItem() != null) {
                        int selectedConferenceID = getSelectedReportConferenceID();
                        populateReportSessionDropdown(selectedConferenceID);
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error refreshing report dropdowns: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }



    private void populateReportSessionDropdown(int conferenceID) {
        try {
            System.out.println("Populating reportSessionDropdown for Conference ID: " + conferenceID);

            List<SessionDTO> sessions = sessionController.listSessionsByConference(conferenceID);
            reportSessionDropdown.removeAllItems();
            for (SessionDTO session : sessions) {
                reportSessionDropdown.addItem(session.getName() + " (ID: " + session.getSessionID() + ")");
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error populating session dropdown: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }




    // Get Selected Conference ID
    private int getSelectedReportConferenceID() {
        try {
            String selectedConference = (String) reportConferenceDropdown.getSelectedItem();
            System.out.println("Selected Conference Dropdown Item: " + selectedConference);

            if (selectedConference == null || selectedConference.isEmpty()) {
                throw new IllegalArgumentException("No conference selected.");
            }

            // Use regex to extract the number inside "ID: X"
            String idString = selectedConference.replaceAll(".*\\(ID: (\\d+)\\).*", "$1");
            System.out.println("Extracted Conference ID: " + idString);

            if (idString.isEmpty()) {
                throw new IllegalArgumentException("Invalid conference selection.");
            }

            return Integer.parseInt(idString);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error fetching selected conference ID: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            throw e; // Re-throw to prevent further processing
        }
    }




    // Get Selected Session ID
    private int getSelectedReportSessionID() {
        try {
            String selectedSession = (String) reportSessionDropdown.getSelectedItem();
            if (selectedSession == null || selectedSession.isEmpty()) {
                throw new IllegalArgumentException("No session selected.");
            }

            // Extract the numeric ID from the selected item
            String idString = selectedSession.replaceAll(".*\\(ID: (\\d+)\\).*", "$1");
            if (idString.isEmpty()) {
                throw new IllegalArgumentException("Invalid session selection.");
            }

            return Integer.parseInt(idString);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error fetching selected session ID: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            throw e; // Re-throw to prevent further processing
        }
    }



    // Generate Feedback Report
    private void generateFeedbackReport() {
        try {
            int sessionID = getSelectedReportSessionID();
            ReportDTO report = reportController.generateSessionFeedbackReport(sessionID, managerName);
            JOptionPane.showMessageDialog(this, "Feedback Report Generated: " + report.getReportID(),
                    "Success", JOptionPane.INFORMATION_MESSAGE);
            refreshReportsTable();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error generating feedback report: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Generate Session Attendance Report
    private void generateSessionAttendanceReport() {
        try {
            int sessionID = getSelectedReportSessionID();
            if (sessionID <= 0) {
                JOptionPane.showMessageDialog(this, "Please select a session.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Call the report controller to generate the report
            ReportDTO report = reportController.generateSessionAttendanceReport(sessionID, managerName);
            if (report != null) {
                JOptionPane.showMessageDialog(this, "Session Attendance Report Generated: " + report.getReportID(),
                        "Success", JOptionPane.INFORMATION_MESSAGE);
                refreshReportsTable();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to generate session attendance report.",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error generating session attendance report: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }


    // Generate Attendance Report
    private void generateConferenceAttendanceReport() {
        try {
            int conferenceID = getSelectedReportConferenceID();
            if (conferenceID <= 0) {
                JOptionPane.showMessageDialog(this, "Invalid conference selected.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            ReportDTO report = reportController.generateConferenceAttendanceReport(conferenceID, managerName);
            if (report != null) {
                JOptionPane.showMessageDialog(this, "Attendance Report Generated: " + report.getReportID(),
                        "Success", JOptionPane.INFORMATION_MESSAGE);
                refreshReportsTable();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to generate attendance report.",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error generating attendance report: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    // Refresh Reports Table
    private void refreshReportsTable() {
        try {
            List<ReportDTO> reports = reportController.listAllReports();
            DefaultTableModel model = (DefaultTableModel) reportsTable.getModel();
            model.setRowCount(0); // Clear existing rows

            if (reports == null){
                return;
            }
            for (ReportDTO report : reports) {
                model.addRow(new Object[]{
                        report.getReportID(),
                        report.getReportType(),
                        report.getGeneratedDate()
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error refreshing reports table: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // View Selected Report
    private void viewSelectedReport() {
        try {
            int selectedRow = reportsTable.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Please select a report to view.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int reportID = (int) reportsTable.getValueAt(selectedRow, 0);
            ReportDTO report = reportController.viewReportDetails(reportID);

            if (report != null) {
                JOptionPane.showMessageDialog(this, "Report Content:\n" + report.getContent(),
                        "Report Details", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Report not found or invalid.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error viewing report: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void initializeProfilePanel(int managerID, String managerName, String managerEmail) {
        lblManagerID.setText("Manager ID: " + managerID);
        lblManagerName.setText("Manager Name: " + managerName);
        lblManagerEmail.setText("Manager Email: " + managerEmail);

        logoutButton.addActionListener(e -> logout());
    }


    private void logout() {
        logoutHelper.performLogout(this, managerID, managerName, managerEmail, Role.ATTENDEE);
    }




}
