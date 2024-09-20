package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainPage extends JFrame {
    private String userName;
    private int points;
    public MainPage(String userName, int points) {
        this.userName = userName;
        this.points = points;
        setTitle("Main Application");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        JPanel panel = new JPanel();
        panel.setLayout(null); 
        JButton profileButton = new JButton("Profile");
        profileButton.setBounds(30, 30, 100, 30); 
        profileButton.setBackground(Color.RED);
        profileButton.setOpaque(true);
        profileButton.setBorderPainted(false); 
        profileButton.setFocusPainted(false);  
        panel.add(profileButton);
        JButton settingsButton = new JButton("Settings");
        settingsButton.setBounds(470, 30, 100, 30);
        settingsButton.setBackground(Color.RED); 
        settingsButton.setOpaque(true);e
        settingsButton.setBorderPainted(false); 
        settingsButton.setFocusPainted(false); 
        panel.add(settingsButton);
        JLabel ipllLabel = new JLabel("IPLL");
        ipllLabel.setFont(new Font("Times New Roman", Font.BOLD, 24));
        ipllLabel.setForeground(new Color(128, 0, 128)); 
        ipllLabel.setBounds(275, 50, 60, 30); 
        panel.add(ipllLabel);


        JButton learnButton = new JButton("Learning");
        learnButton.setBounds(90, 150, 100, 50); 
        learnButton.setBackground(Color.GREEN);
        learnButton.setOpaque(true);
        learnButton.setBorderPainted(false);
        learnButton.setFocusPainted(false);
        panel.add(learnButton);
        JButton testButton = new JButton("Test");
        testButton.setBounds(90, 220, 100, 50);
        testButton.setBackground(Color.GREEN);
        testButton.setBorderPainted(false);
        testButton.setFocusPainted(false);
        panel.add(testButton);

        JButton dailyButton = new JButton("Daily");
        dailyButton.setBounds(410, 150, 100, 50);
        dailyButton.setBackground(Color.GREEN);
        dailyButton.setOpaque(true);
        dailyButton.setBorderPainted(false);
        dailyButton.setFocusPainted(false);
        panel.add(dailyButton);

        JSeparator separator = new JSeparator(SwingConstants.VERTICAL);
        separator.setBounds(300, 100, 1, 200);
        panel.add(separator);

        profileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(MainPage.this, "Profile Button Clicked");
            }
        });

        settingsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(MainPage.this, "Settings Button Clicked");
            }
        });

        learnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(MainPage.this, "Learning Button Clicked");
            }
        });

        testButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(MainPage.this, "Test Button Clicked");
            }
        });

        dailyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(MainPage.this, "Daily Button Clicked");
            }
        });
        add(panel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainPage mainPage = new MainPage("User123", 50); // Example user and points
            mainPage.setVisible(true);
        });
    }
}
