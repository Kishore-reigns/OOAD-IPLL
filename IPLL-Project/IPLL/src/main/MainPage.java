package main;

import javax.swing.*;

public class MainPage extends JFrame {

    public MainPage() {
        setTitle("Main Application");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the frame on the screen

        // Add a label to the main frame
        JLabel label = new JLabel("HELLO WORLD", SwingConstants.CENTER);
        add(label);
    }

    public static void main(String[] args) {
        // Run the MainPage frame on the Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            MainPage mainPage = new MainPage();
            mainPage.setVisible(true);
        });
    }
}
