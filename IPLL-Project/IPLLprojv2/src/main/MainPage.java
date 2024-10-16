package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

public class MainPage extends JFrame {
    private String userName;
    private int points;
    public MainPage() {
       // this.userName = userName;
        //this.points = points;

        setTitle("Interactive Progamming Language Learner v1.1s");
        setSize(900, 600);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
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
        settingsButton.setOpaque(true);
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
               
            	MainPage.this.setVisible(false);
                Profile profile = new Profile() ; 
                profile.setVisible(true);
                MainPage.this.dispose();
            	//JOptionPane.showMessageDialog(MainPage.this, "Profile Button Clicked");
            }
        });

        settingsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	
            	MainPage.this.setVisible(false);
            	Settings settings = new Settings();
            	settings.setVisible(true);
            	MainPage.this.dispose();
               // JOptionPane.showMessageDialog(MainPage.this, "Settings Button Clicked");
            }
        });

        learnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                MainPage.this.setVisible(false);
                LearnHub lh = new LearnHub() ; 
                lh.setVisible(true);
                MainPage.this.dispose();
            	//JOptionPane.showMessageDialog(MainPage.this, "Learning Button Clicked");
            }
        });

        testButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	
            	MainPage.this.setVisible(false);
            	Test test = new Test() ; 
            	test.setVisible(true);
            	MainPage.this.dispose();
            
                //JOptionPane.showMessageDialog(MainPage.this, "Test Button Clicked");
            }
        });

        dailyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	
            	//new CodeforcesProblems() ; 
            	MainPage.this.setVisible(false);
            	Daily daily = new Daily() ;
            	daily.setVisible(true);
            	MainPage.this.dispose();
                //JOptionPane.showMessageDialog(MainPage.this, "Daily Button Clicked");
            }
        });
        add(panel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainPage mainPage = new MainPage();
            mainPage.setVisible(true);
        });
    }
}