package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

public class MainPage extends JFrame {
    public MainPage() {
        setTitle("Interactive Programming Language Learner v1.1s");
        setSize(900, 600);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(240, 248, 255)); // Light blue background
        panel.setPreferredSize(new Dimension(900, 600)); // Set panel to match frame size
        panel.setSize(900, 600);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 20, 10, 20); // Padding around components

        // Profile Button - Top Left Corner
        JButton profileButton = createButton("Profile", Color.RED, Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(profileButton, gbc);

        // Centered IPLL Label at the top
        JLabel ipllLabel = new JLabel("IPLL");
        ipllLabel.setFont(new Font("Times New Roman", Font.BOLD, 36));
        ipllLabel.setForeground(new Color(128, 0, 128));
        gbc.gridx = 1;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(ipllLabel, gbc);

        // Settings Button - Top Right Corner
        JButton settingsButton = createButton("Settings", Color.RED, Color.WHITE);
        gbc.gridx = 3;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(settingsButton, gbc);

        // Reset grid width and anchor for other elements
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.CENTER;

        // Setting button size for Learning, Test, and Daily
        Dimension buttonSize = new Dimension(120, 60);

        // Learning Button
        JButton learnButton = createButton("Learning", Color.GREEN, Color.BLACK);
        learnButton.setPreferredSize(buttonSize);
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(learnButton, gbc);

        // Test Button
        JButton testButton = createButton("Test", Color.GREEN, Color.BLACK);
        testButton.setPreferredSize(buttonSize);
        gbc.gridy = 2;
        panel.add(testButton, gbc);

        // Daily Button with combined height of Learning and Test
        JButton dailyButton = createButton("Daily", Color.GREEN, Color.BLACK);
        dailyButton.setPreferredSize(new Dimension(120, 130));
        gbc.gridx = 3;
        gbc.gridy = 1;
        gbc.gridheight = 2;
        panel.add(dailyButton, gbc);

        // Vertical Separator with added space around it
        JSeparator separator = new JSeparator(SwingConstants.VERTICAL);
        separator.setForeground(Color.GRAY);
        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.gridheight = 2;
        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.insets = new Insets(0, 30, 0, 30); // Space around separator
        panel.add(separator, gbc);

        // Reset insets for other components
        gbc.insets = new Insets(10, 20, 10, 20);

        // Action Listeners
        profileButton.addActionListener(e -> openNewPage(new Profile()));
        settingsButton.addActionListener(e -> openNewPage(new Settings()));
        learnButton.addActionListener(e -> openNewPage(new LearnHub()));
        testButton.addActionListener(e -> openNewPage(new Test()));
        dailyButton.addActionListener(e -> openNewPage(new Daily()));

        add(panel);
    }

    private JButton createButton(String text, Color bgColor, Color fgColor) {
        JButton button = new JButton(text);
        button.setBackground(bgColor);
        button.setForeground(fgColor);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setOpaque(true);
        return button;
    }

    private void openNewPage(JFrame page) {
        this.setVisible(false);
        page.setVisible(true);
        this.dispose();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainPage mainPage = new MainPage();
            mainPage.setVisible(true);
        });
    }
}
