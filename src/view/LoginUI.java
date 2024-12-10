package view;

import controller.*;
import dto.UserDTO;
import exception.IncorrectPasswordException;
import exception.UserNotFoundException;
import intializer.AppInitializer;
import service.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class LoginUI extends JFrame {
    private JTextField emailField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JPanel mainPanel;
    private JLabel emailLabel;
    private JLabel passwordLabel;
    private JButton registerButton;

    private UserController userController;
    public AttendeeController attendeeController;
    public SpeakerController speakerController;
    public ConferenceManagerController conferenceManagerController;
    public ConferenceController conferenceController;
    public SessionController sessionController;
    public ReportController reportController;
    public CertificateController certificateController;


    public LoginUI(UserController userController) {
        this.userController = userController;

        setTitle("Login Page");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Add panel to frame
        add(mainPanel);

        // Add action listeners
        loginButton.addActionListener(new LoginAction());
        registerButton.addActionListener(new RegisterAction());

        setVisible(true);
    }

    // Login action
    private class LoginAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String email = emailField.getText().trim();
            String password = new String(passwordField.getPassword()).trim();

            try {
                // Attempt to log in
                UserDTO user = userController.login(email, password);

                // Redirect based on role
                switch (user.getRole()) {
                    case ATTENDEE:
                        AttendeePortalUI attendeeUI = new AttendeePortalUI(attendeeController, userController, user);
                        attendeeUI.setVisible(true);
                        break;
                    case SPEAKER:
                        SpeakerPortalUI speakerUI = new SpeakerPortalUI(speakerController, userController, user);
                        speakerUI.setVisible(true);
                        break;
                    case MANAGER:
                        ManagerPortalUI managerUI = new ManagerPortalUI(userController, conferenceManagerController, conferenceController,  sessionController, speakerController, reportController, attendeeController, certificateController, user);
                        managerUI.setVisible(true);
                        break;
                    default:
                        throw new IllegalArgumentException("Unknown role: " + user.getRole());
                }

                dispose(); // Close login UI on success

            } catch (UserNotFoundException ex) {
                // Handle user not found
                JOptionPane.showMessageDialog(LoginUI.this, "User not found. Please register.", "Login Failed", JOptionPane.ERROR_MESSAGE);
            } catch (IncorrectPasswordException ex) {
                // Handle incorrect password
                JOptionPane.showMessageDialog(LoginUI.this, "Incorrect password. Please try again.", "Login Failed", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                // Handle unexpected exceptions
                JOptionPane.showMessageDialog(LoginUI.this, "An unexpected error occurred: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Register action
    private class RegisterAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            RegisterUI registerUI = new RegisterUI(userController);
            registerUI.setVisible(true);
        }
    }
}
