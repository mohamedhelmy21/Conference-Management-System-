package view;

import controller.UserController;
import intializer.AppInitializer;
import service.LoginService;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegisterUI extends JFrame {
    private JPanel mainPanel;
    private JPanel namePanel;
    private JLabel nameLabel;
    private JTextField nameField;
    private JTextField emailField;
    private JPasswordField passwordField;
    private JLabel emailLabel;
    private JLabel passwordLabel;
    private JButton registerButton;
    private final UserController userController;

    public RegisterUI(UserController userController) {
        this.userController = userController;

        setTitle("Conference Management System - Register");
        setContentPane(mainPanel);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);

        // Register button action
        registerButton.addActionListener(new RegisterAction());
    }

    private class RegisterAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String name = nameField.getText();
            String email = emailField.getText();
            String password = new String(passwordField.getPassword());

            try {
                if (userController.registerAttendee(name, email, password) != null) {
                    JOptionPane.showMessageDialog(RegisterUI.this, "Registration successful! Please log in.", "Success", JOptionPane.INFORMATION_MESSAGE);
                    dispose(); // Close the registration window
                } else {
                    JOptionPane.showMessageDialog(RegisterUI.this, "Registration failed. User already exists.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(RegisterUI.this, "An error occurred: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        };
    }

    public static void main(String[] args) {
        AppInitializer appInitializer = new AppInitializer();
        appInitializer.initialize();
        LoginService loginService = appInitializer.getLoginService();
        UserController userController = new UserController(loginService);
        SwingUtilities.invokeLater(() -> new RegisterUI(userController).setVisible(true));
    }
}
