package main;

import java.awt.Desktop;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
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

        panel = new JPanel();
        panel.setLayout(new GridBagLayout());

        JScrollPane scrollPane = new JScrollPane(panel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        fetchCodeForceProblems();

        add(scrollPane);
    }

    public void fetchCodeForceProblems() {
        try {
            String jsonResponse = CodeforcesProblems.fetchProblems();
            if (jsonResponse != null) {
                JSONObject jsonobj = new JSONObject(jsonResponse);
                JSONArray problems = jsonobj.getJSONObject("result").getJSONArray("problems");

                GridBagConstraints gbc = new GridBagConstraints();
                gbc.insets = new Insets(10, 10, 10, 10);
                gbc.fill = GridBagConstraints.HORIZONTAL;

                // Header row
                gbc.gridx = 0;
                gbc.gridy = 0;
                panel.add(new JLabel("No."), gbc);

                gbc.gridx = 1;
                panel.add(new JLabel("Content ID"), gbc);

                gbc.gridx = 2;
                panel.add(new JLabel("Index"), gbc);

                gbc.gridx = 3;
                panel.add(new JLabel("Problem"), gbc);

                gbc.gridx = 4;
                panel.add(new JLabel("Action"), gbc);

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
                    panel.add(new JLabel(String.valueOf(i + 1)), gbc);

                    // Contest ID
                    gbc.gridx = 1;
                    panel.add(new JLabel(contestId), gbc);

                    // Index
                    gbc.gridx = 2;
                    panel.add(new JLabel(problemIndex), gbc);

                    // Problem name
                    gbc.gridx = 3;
                    panel.add(new JLabel(problemName), gbc);

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

                    gbc.gridx = 4; // Button goes in the final column
                    panel.add(viewButton, gbc);
                }

                // Revalidate and repaint the panel after adding components
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
