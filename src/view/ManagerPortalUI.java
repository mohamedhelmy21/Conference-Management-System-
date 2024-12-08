package view;

import javax.swing.*;

public class ManagerPortalUI extends JFrame {
    public ManagerPortalUI(int userID, String name) {
        setTitle("Manager Portal");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JLabel welcomeLabel = new JLabel("Welcome to the Manager Portal, " + name + "!");
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);

        add(welcomeLabel);
        setVisible(true);
    }
}

