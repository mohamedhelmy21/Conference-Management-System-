package view;

import javax.swing.*;

public class SpeakerPortalUI extends JFrame {
    public SpeakerPortalUI(int userID, String name) {
        setTitle("Speaker Portal");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JLabel welcomeLabel = new JLabel("Welcome to the Speaker Portal, " + name + "!");
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);

        add(welcomeLabel);
        setVisible(true);
    }
}

