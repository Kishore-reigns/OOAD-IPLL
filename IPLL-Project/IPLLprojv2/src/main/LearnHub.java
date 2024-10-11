package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LearnHub extends JFrame {

    public LearnHub() {
        // Set up the frame
        setTitle("Interactive Programming Language Learner");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);

        // Set the background color of the content panel to black
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BorderLayout());
        contentPanel.setBackground(Color.BLACK); // Set background to black
        setContentPane(contentPanel);

        // Create a panel for the language buttons with horizontal alignment
        JPanel languagePanel = new JPanel();
        languagePanel.setLayout(new BoxLayout(languagePanel, BoxLayout.X_AXIS));
        languagePanel.setBackground(Color.decode("#2B2B2B"));  // Set background to black

        // Add horizontal glue for centering the entire panel
        languagePanel.add(Box.createHorizontalGlue());

        // Add 'C' language button (image inside button)
        JButton cButton = createLanguageButton("C", "K:\\OOADdb\\c.png");
        languagePanel.add(cButton);

        // Add some horizontal space between the buttons
        languagePanel.add(Box.createRigidArea(new Dimension(20, 0)));

        // Add 'Java' language button (image inside button)
        JButton javaButton = createLanguageButton("Java", "K:\\OOADdb\\java.png");
        languagePanel.add(javaButton);

        // Add some horizontal space between the buttons
        languagePanel.add(Box.createRigidArea(new Dimension(20, 0)));

        // Add 'More Languages' button (image inside button)
        JButton moreLanguagesButton = createLanguageButton("More Languages", "K:\\OOADdb\\more.png");
        languagePanel.add(moreLanguagesButton);

        // Add horizontal glue for centering the entire panel
        languagePanel.add(Box.createHorizontalGlue());

        // Create a panel for the back button
        JPanel backPanel = new JPanel(new FlowLayout(FlowLayout.LEFT)); // Align to the left
        backPanel.setBackground(Color.decode("#2B2B2B"));  // Set background to black
        JButton backButton = new JButton("<-");
        backButton.setBackground(Color.DARK_GRAY);  // Set back button background color
        backButton.setForeground(Color.WHITE);      // Set text color to white
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle back button action here
            }
        });
        backPanel.add(backButton); // Add back button to its panel

        // Create a panel to hold the title label and language panel together
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout());
        topPanel.setBackground(Color.decode("#2B2B2B")); // Set background to black

        // Add "Start Learning" label at the top with vertical space
        JLabel titleLabel = new JLabel("Start Learning", SwingConstants.CENTER);
        titleLabel.setForeground(Color.YELLOW);  // Set color to violet
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));  // Set font style and size
        titleLabel.setBackground(Color.decode("#2B2B2B"));  // Set the label background to black
        titleLabel.setOpaque(true);  // Make label background visible

        // Adjust the vertical space for the title label to be lower
        topPanel.add(Box.createRigidArea(new Dimension(0, 40)), BorderLayout.NORTH); // Increase vertical space
        topPanel.add(titleLabel, BorderLayout.NORTH);
        topPanel.add(languagePanel, BorderLayout.CENTER);

        // Add the top panel (title + buttons) to the center of the content panel
        contentPanel.add(topPanel, BorderLayout.CENTER);
        contentPanel.add(backPanel, BorderLayout.WEST); // Add the back button panel

        // Add action listener to the 'C' button
        cButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	
            	LearnHub.this.setVisible(false);
            	LearnC lc = new LearnC();
            	lc.setVisible(true);
            	LearnHub.this.dispose();
            	
                //JOptionPane.showMessageDialog(LearnHub.this, "Learn C!");
            }
        });

        // Add action listener to the 'Java' button
        javaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
            	LearnHub.this.setVisible(false);
            	LearnJava lj = new LearnJava();
            	lj.setVisible(true);
            	LearnHub.this.dispose();
            	//JOptionPane.showMessageDialog(LearnHub.this, "Learn Java!");
            }
        });

        // Add action listener to the 'More Languages' button
        moreLanguagesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(LearnHub.this, "More Languages coming soon!");
            }
        });
    }

    // Helper method to create a language button with an image inside
    private JButton createLanguageButton(String text, String iconPath) {
        JButton button = new JButton(text);

        // Resize the image to make all logos of equal size
        ImageIcon icon = new ImageIcon(iconPath);
        Image scaledImage = icon.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH); // Resize to 80x80 pixels
        button.setIcon(new ImageIcon(scaledImage));

        // Center text and image inside the button
        button.setHorizontalTextPosition(SwingConstants.CENTER); // Center the text horizontally
        button.setVerticalTextPosition(SwingConstants.BOTTOM);   // Place the text below the icon

        // Set button border and background
        button.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2)); // Add white border
        button.setContentAreaFilled(true);           // Allow background fill
        button.setFocusPainted(false);               // No focus rectangle
        button.setOpaque(true);                      // Make it opaque
        button.setBackground(Color.DARK_GRAY);       // Set button background color
        button.setForeground(Color.WHITE);           // Set text color to white

        return button;
    }

    public static void main(String[] args) {
        // Create and show the frame
        LearnHub app = new LearnHub();
        app.setVisible(true);
    }
}
