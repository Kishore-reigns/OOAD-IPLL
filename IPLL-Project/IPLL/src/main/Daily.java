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
        JLabel dailyTitle = new JLabel("Daily Questions - Sponsored by CodeChef");
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(dailyTitle, gbc);

        // Question label
        JLabel question = new JLabel("Lorem Ipsum");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        panel.add(question, gbc);

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

        add(panel);
    }

    public static void main(String... args) {
        SwingUtilities.invokeLater(() -> {
            Daily daily = new Daily();
            daily.setVisible(true);
        });
    }
}
