package main;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

public class Daily extends JFrame {

    public Daily() {
        setTitle("Daily Questions - Happy Coding ");
        setSize(900, 600);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Title label
        JLabel dailyTitle = new JLabel("Daily Questions - Sponsored by CodeForces");
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(dailyTitle, gbc);

        // Question label
        JLabel question = new JLabel("Fetching question .....");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        panel.add(question, gbc);

        String problemname = fetchCodeForceProblems();
        question.setText(problemname);

        // Attend test button
        JButton attendTest = new JButton("Go To CodeChef");
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        panel.add(attendTest, gbc);

        // Time label
        JLabel time = new JLabel("time");
        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(time, gbc);


        attendTest.addActionListener(e->{
            try{
                String url = "https://codeforces.com/contest/"+contestId+"/problems/"+index;
                Desktop.getDesktop().browse(URI.create(url));
            }catch(Exception ex){
                ex.printStackTrace() ; 
            }
        });

        add(panel);



    }
    
    public static fetchCodeForceProblems(){
        String jsonResponse = CodeforcesProblems.fetchProblems();
        if(jsonResponse != null){
            JSONObject jsonobj = new JSONObject(jsonResponse);
            JSONArray problems = jsonobj.getJSONObject("result").getJSONArray("problems");
            return problems.getJSONObject(0).getString("name");
        }
        return "failed to fetch problem";
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Daily daily = new Daily();
            daily.setVisible(true);
        });
    }
}
