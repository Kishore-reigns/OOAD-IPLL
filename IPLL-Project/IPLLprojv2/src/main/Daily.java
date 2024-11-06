package main;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URI;
import java.time.Duration;
import java.time.LocalTime;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import org.json.JSONObject;
import org.json.JSONArray;

public class Daily extends JFrame {

    public static String contentId;
    public static String index;
    private JLabel questionLabel;
    private JLabel countdownLabel;

    public Daily() {
        setTitle("Daily Questions - by CodeForces -- Happy Coding ");
        setSize(900, 600);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
       


        // Set up panel with GridBagLayout
       
        JPanel panel = new JPanel();
        setContentPane(panel);
        panel.setLayout(new GridBagLayout());
        panel.setOpaque(true);
        panel.setBackground(Color.decode("#2B2B2B"));


        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.CENTER;
        gbc.anchor = GridBagConstraints.CENTER;

        // Title label
        JLabel dailyTitle = new JLabel("Daily Questions - Sponsored by CodeForces");
        dailyTitle.setBackground(Color.white);
        dailyTitle.setForeground(Color.white);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        panel.add(dailyTitle, gbc);

        // Question label
        questionLabel = new JLabel("Fetching question ....."); // Set as instance variable
        gbc.gridy = 1;
        questionLabel.setBackground(Color.white);
        questionLabel.setForeground(Color.white);
        panel.add(questionLabel, gbc);

        // Back Button - Position at the top left corner
     // Back Button - Position at the top left corner
        
        JPanel backbuttonpanel = new JPanel(new GridBagLayout());
        backbuttonpanel.setOpaque(false);
        
        JButton backButton = new JButton("<-");
        backButton.setBackground(Color.DARK_GRAY);
        backButton.setForeground(Color.white);
        
        GridBagConstraints backButtonGbc = new GridBagConstraints();
        backButtonGbc.gridx = 0;
        backButtonGbc.gridy = 0;
        backButtonGbc.anchor = GridBagConstraints.NORTHWEST; // Position top-left
        backButtonGbc.insets = new Insets(10, 10, 0, 0); // Add padding for better positioning
        backbuttonpanel.add(backButton, backButtonGbc);
        
        
        GridBagConstraints mainPanelGbc = new GridBagConstraints();
        mainPanelGbc.gridx = 0;
        mainPanelGbc.gridy = 0;
        mainPanelGbc.anchor = GridBagConstraints.NORTHWEST;
        panel.setSize(900, 600);
        panel.add(backbuttonpanel, mainPanelGbc);
        
        
        System.out.println(panel.getWidth() + ", " + panel.getHeight());
        
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Daily.this.setVisible(false);
                MainPage mp = new MainPage();
                mp.setVisible(true);
                Daily.this.dispose();
            }
        });


        // Fetch and display the initial question
        String problemName = fetchCodeForceProblems();
        questionLabel.setText(problemName);

        // Attend test button
        JButton attendTest = new JButton("Go To CodeForces");
        gbc.gridy = 2;
        panel.add(attendTest, gbc);
        attendTest.setBackground(Color.decode("#E5D309"));
        attendTest.setForeground(Color.black);

        // Time label for countdown
        countdownLabel = new JLabel("Time until next question: calculating...");
        countdownLabel.setBackground(Color.white);
        countdownLabel.setForeground(Color.white);
        
        gbc.gridy = 3;
        panel.add(countdownLabel, gbc);

        attendTest.addActionListener(e -> {
            try {
                String url = "https://codeforces.com/contest/" + contentId + "/problem/" + index;
                Desktop.getDesktop().browse(URI.create(url));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        
        attendTest.addMouseListener(new MouseAdapter() {
        	public void mouseEntered(MouseEvent e) {
        		attendTest.setBackground(Color.decode("#FED008"));
        		attendTest.setForeground(Color.BLACK);
        	}
        	public void mouseExited(MouseEvent e) {
        		attendTest.setBackground(Color.decode("#E5D309"));
                attendTest.setForeground(Color.black);
        	}
        } );

        //add(panel);

        // Schedule the reset at 12 PM daily and update the countdown
        scheduleQuestionReset();
        scheduleCountdownUpdate();
    }

    public static String fetchCodeForceProblems() {
        try {
            String jsonResponse = CodeforcesProblems.fetchProblems();
            if (jsonResponse != null) {
                JSONObject jsonobj = new JSONObject(jsonResponse);
                JSONArray problems = jsonobj.getJSONObject("result").getJSONArray("problems");
                Random random = new Random();
                JSONObject firstProblem = problems.getJSONObject(0);

                int contestIdInt = firstProblem.getInt("contestId");
                contentId = Integer.toString(contestIdInt); // Convert to string
                index = firstProblem.getString("index");

                return firstProblem.getString("name");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Failed to fetch problem";
    }

    private void scheduleQuestionReset() {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

        // Schedule the task to run every minute
        scheduler.scheduleAtFixedRate(() -> {
            LocalTime now = LocalTime.now();

            // Check if it's 12 PM (noon)
            if (now.getHour() == 0 && now.getMinute() == 0) {
                // Fetch the new problem at 12 PM and update the question label
                String problemName = fetchCodeForceProblems();
                SwingUtilities.invokeLater(() -> questionLabel.setText(problemName));
            }

        }, 0, 1, TimeUnit.MINUTES); // Check every minute
    }

    private void scheduleCountdownUpdate() {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

        scheduler.scheduleAtFixedRate(() -> {
            LocalTime now = LocalTime.now();
            LocalTime resetTime = LocalTime.of(23, 59, 59);

            if (now.isAfter(resetTime)) {
                resetTime = resetTime.plusHours(24);
            }

            // Calculate the remaining time until 12 PM
            Duration duration = Duration.between(now, resetTime);
            long hours = duration.toHours();
            long minutes = duration.toMinutesPart();
            long seconds = duration.toSecondsPart();

            // Update the countdown label
            String countdownText = String.format("Time until next question: %02d : %02d : %02d ", hours, minutes, seconds);
            SwingUtilities.invokeLater(() -> countdownLabel.setText(countdownText));

        }, 0, 1, TimeUnit.SECONDS); // Update every second
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Daily daily = new Daily();
            daily.setVisible(true);
        });
    }
}
