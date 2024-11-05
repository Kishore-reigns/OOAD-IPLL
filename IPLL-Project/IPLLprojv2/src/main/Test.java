package main;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.Font ; 
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URI;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import org.json.JSONObject;
import org.json.JSONArray;

public class Test extends JFrame {
    private JPanel panel;

    public Test() {
        setTitle("Attend Test - By Codeforces");
        setSize(900, 600);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(Color.decode("#2B2B2B"));
        
        JButton backButton = new JButton("<-");
        backButton.setBounds(10, 10, 70, 30); // Position top-left
        add(backButton);
        backButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Test.this.setVisible(false);
				MainPage mp = new MainPage();
				mp.setVisible(true);
				Test.this.dispose();
			}
        	
        	
        });

        panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBackground(Color.decode("#2B2B2B"));
        panel.setForeground(Color.white);

        
        
        JScrollPane scrollPane = new JScrollPane(panel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        fetchCodeForceProblems();

        add(scrollPane);
    }
    public void fetchCodeForceProblems() {
        try {
            String jsonResponse = CodeforcesProblems.fetchProblems();
            Font boldfont = new Font("Default",Font.BOLD, 12) ; 
            if (jsonResponse != null) {
                JSONObject jsonobj = new JSONObject(jsonResponse);
                JSONArray problems = jsonobj.getJSONObject("result").getJSONArray("problems");

                GridBagConstraints gbc = new GridBagConstraints();
                gbc.insets = new Insets(10, 10, 10, 10);
                gbc.fill = GridBagConstraints.HORIZONTAL;

                // Header row
                gbc.gridx = 0;
                gbc.gridy = 0;
                JLabel numberLabel = new JLabel("No.");
                numberLabel.setForeground(Color.WHITE);
                numberLabel.setFont(boldfont);
                panel.add(numberLabel, gbc);

                gbc.gridx = 1;
                JLabel contentIdLabel = new JLabel("Content ID");
                contentIdLabel.setForeground(Color.WHITE);
                contentIdLabel.setFont(boldfont);
                panel.add(contentIdLabel, gbc);
                
                gbc.gridx = 2;
                JLabel indexLabel = new JLabel("Index");
                indexLabel.setForeground(Color.WHITE);
                indexLabel.setFont(boldfont);
                panel.add(indexLabel, gbc);

                gbc.gridx = 3;
                JLabel problemLabel = new JLabel("Problem");
                problemLabel.setForeground(Color.WHITE);
                problemLabel.setFont(boldfont);
                panel.add(problemLabel, gbc);

                gbc.gridx = 4;
                JLabel actionLabel = new JLabel("Action");
                actionLabel.setForeground(Color.WHITE);
                actionLabel.setFont(boldfont);
                panel.add(actionLabel, gbc);

                // Loop through the first 20 problems and display them in a grid
                for (int i = 0; i < 20; i++) {
                    JSONObject problem = problems.getJSONObject(i);
                    String contestId = Integer.toString(problem.getInt("contestId"));
                    String problemIndex = problem.getString("index");
                    String problemName = problem.getString("name");

                    // Row for each problem
                    gbc.gridy = i + 1;

                    // Problem number
                    gbc.gridx = 0;
                    JLabel problemNumberLabel = new JLabel(String.valueOf(i + 1));
                    problemNumberLabel.setForeground(Color.WHITE);
                    panel.add(problemNumberLabel, gbc);

                    // Contest ID
                    gbc.gridx = 1;
                    JLabel contestIdLabel = new JLabel(contestId);
                    contestIdLabel.setForeground(Color.WHITE);
                    panel.add(contestIdLabel, gbc);

                    // Index
                    gbc.gridx = 2;
                    JLabel problemIndexLabel = new JLabel(problemIndex);
                    problemIndexLabel.setForeground(Color.WHITE);
                    panel.add(problemIndexLabel, gbc);

                    // Problem name
                    gbc.gridx = 3;
                    JLabel problemNameLabel = new JLabel(problemName);
                    problemNameLabel.setForeground(Color.WHITE);
                    panel.add(problemNameLabel, gbc);

                    // Button to view the problem
                    JButton viewButton = new JButton("View Problem");

                    // Capture the current contestId and index
                    final String currentContestId = contestId;
                    final String currentIndex = problemIndex;

                    viewButton.addActionListener(e -> {
                        try {
                            String url = "https://codeforces.com/contest/" + currentContestId + "/problem/" + currentIndex;
                            Desktop.getDesktop().browse(URI.create(url));
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    });

                    viewButton.setBackground(Color.decode("#FED008"));

                    viewButton.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseEntered(MouseEvent e) {
                            viewButton.setBackground(Color.decode("#E5D309"));
                            viewButton.setForeground(Color.BLACK);
                        }

                        @Override
                        public void mouseExited(MouseEvent e) {
                            viewButton.setBackground(Color.decode("#FED008"));
                            viewButton.setForeground(Color.BLACK);
                        }
                    });

                    gbc.gridx = 4;
                    panel.add(viewButton, gbc);
                }
                panel.revalidate();
                panel.repaint();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Test test = new Test();
            test.setVisible(true);
        });
    }
}
