package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

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
        setTitle("Interactive Programming Language Learner v1.3s");
        setSize(900, 600);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(240, 248, 255)); // Light blue background
        panel.setPreferredSize(new Dimension(900, 600)); // Set panel to match frame size
        panel.setSize(900, 600);
        panel.setBackground(Color.decode("#2B2B2B"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 20, 10, 20); // Padding around components

        // Profile Button - Top Left Corner
        JButton profileButton = createButton("Profile", Color.decode("#D40B0B"), Color.WHITE);
        profileButton.setPreferredSize(new Dimension(120,40));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(profileButton, gbc);
        profileButton.addMouseListener(new MouseAdapter() {
        	public void mouseEntered(MouseEvent e) {
        		profileButton.setBackground(Color.decode("#B30B0B"));
        		profileButton.setForeground(Color.white);
	    	}

	    	@Override
			public void mouseExited(MouseEvent e) {
	    		profileButton.setBackground(Color.decode("#D40B0B"));
	    		 profileButton.setForeground(Color.white);
        	
        }
	    });

        // Centered IPLL Label at the top
        JLabel ipllLabel = new JLabel("IPLL");
        ipllLabel.setFont(new Font("Times New Roman", Font.BOLD, 36));
        ipllLabel.setForeground(Color.decode("#FED008"));
        
        gbc.gridx = 1;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(ipllLabel, gbc);

        // Settings Button - Top Right Corner
        JButton settingsButton = createButton("Settings", Color.decode("#D40B0B"), Color.WHITE);
        settingsButton.setPreferredSize(new Dimension(120,40));
        settingsButton.addMouseListener(new MouseAdapter() {
        	public void mouseEntered(MouseEvent e) {
        		settingsButton.setBackground(Color.decode("#B30B0B"));
        		settingsButton.setForeground(Color.white);
	    	}

	    	@Override
			public void mouseExited(MouseEvent e) {
	    		settingsButton.setBackground(Color.decode("#D40B0B"));
	    		settingsButton.setForeground(Color.white);
        	
        }
	    });
        gbc.gridx = 3;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(settingsButton, gbc);

        // Reset grid width and anchor for other elements
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.CENTER;

        // Setting button size for Learning, Test, and Daily
        Dimension buttonSize = new Dimension(120, 60);

        // Learning Button
        JButton learnButton = createButton("Learning", Color.decode("#1ABA8D"), Color.BLACK);
        learnButton.setPreferredSize(buttonSize);
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(learnButton, gbc);
        learnButton.addMouseListener(new MouseAdapter() {
        	public void mouseEntered(MouseEvent e) {
        		learnButton.setBackground(Color.decode("#40B30E"));
        		learnButton.setForeground(Color.white);
	    	}

	    	@Override
			public void mouseExited(MouseEvent e) {
	    		learnButton.setBackground(Color.decode("#1ABA8D"));
	    		learnButton.setForeground(Color.black);
        	
        }
	    });

        // Test Button
        JButton testButton = createButton("Test", Color.decode("#1ABA8D"), Color.BLACK);
        testButton.setPreferredSize(buttonSize);
        gbc.gridy = 2;
        panel.add(testButton, gbc);
        testButton.addMouseListener(new MouseAdapter() {
        	public void mouseEntered(MouseEvent e) {
        		testButton.setBackground(Color.decode("#40B30E"));
        		testButton.setForeground(Color.white);
	    	}

	    	@Override
			public void mouseExited(MouseEvent e) {
	    		testButton.setBackground(Color.decode("#1ABA8D"));
	    		testButton.setForeground(Color.black);
        	
        }
	    });

        // Daily Button with combined height of Learning and Test
        JButton dailyButton = createButton("Daily", Color.decode("#1ABA8D"), Color.BLACK);
        dailyButton.setPreferredSize(new Dimension(123, 140));
        gbc.gridx = 3;
        gbc.gridy = 1;
        gbc.gridheight = 2;
        panel.add(dailyButton, gbc);
        dailyButton.addMouseListener(new MouseAdapter() {
        	public void mouseEntered(MouseEvent e) {
        		dailyButton.setBackground(Color.decode("#40B30E"));
        		dailyButton.setForeground(Color.white);
	    	}

	    	@Override
			public void mouseExited(MouseEvent e) {
	    		dailyButton.setBackground(Color.decode("#1ABA8D"));
	    		dailyButton.setForeground(Color.black);
        	
        }
	    });

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
