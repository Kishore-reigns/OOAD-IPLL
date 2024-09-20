package main ; 

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.swing.*;
import org.json.JSONArray;
import org.json.JSONObject;


public class CodeforcesProblems {
    private static final String API_URL = "https://codeforces.com/api/problemset.problems";

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Codeforces Problems");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(900, 600);
            JPanel panel = new JPanel();
            JTextArea textArea = new JTextArea(20, 70);
            panel.add(new JScrollPane(textArea));
            frame.add(panel);
            frame.setVisible(true);

            // Fetch problems and display them
            fetchProblems(textArea);
        });
    }

    private static void fetchProblems(JTextArea textArea) {
        try {
            URL url = new URL(API_URL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            if (conn.getResponseCode() == 200) {
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                // Parse JSON response
                JSONObject jsonResponse = new JSONObject(response.toString());
                JSONArray problems = jsonResponse.getJSONObject("result").getJSONArray("problems");

                StringBuilder displayText = new StringBuilder();
                for (int i = 0; i < problems.length(); i++) {
                    JSONObject problem = problems.getJSONObject(i);
                    displayText.append("Problem: ")
                               .append(problem.getString("name"))
                               .append("\n")
                               .append("Difficulty: ")
                               .append(problem.getString("difficulty"))
                               .append("\n\n");
                }

                textArea.setText(displayText.toString());
            } else {
                textArea.setText("Error fetching problems: " + conn.getResponseCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
            textArea.setText("Exception: " + e.getMessage());
        }
    }
}
