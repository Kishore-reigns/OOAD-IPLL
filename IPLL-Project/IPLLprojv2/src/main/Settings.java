package main;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Updates;
import org.bson.Document;
import org.bson.conversions.Bson;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Settings extends JFrame {

    private MongoClient client;
    private MongoDatabase db;
    private MongoCollection<Document> collections;
    private Document user;

    JButton changeUsername, changePic, changePassword;

    Settings() {
        client = MongoClients.create("mongodb://localhost:27017");
        db = client.getDatabase("ipllDb");
        collections = db.getCollection("User");
        user = collections.find().first();

        setSize(900, 600);
        setTitle("Settings");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the frame on the screen
        getContentPane().setBackground(Color.decode("#2B2B2B"));
        
        JPanel settingspanel = new JPanel();
        settingspanel.setBackground(Color.decode("#2B2B2B"));
        settingspanel.setLayout(new GridBagLayout());
        JButton backButton = new JButton("<-");
        backButton.setBounds(10, 10, 70, 30); // Position top-left
        add(backButton);
        backButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Settings.this.setVisible(false);
				MainPage mp = new MainPage();
				mp.setVisible(true);
				Settings.this.dispose();
			}
        	
        	
        });

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // padding
        gbc.fill = GridBagConstraints.HORIZONTAL; // resize if larger

        gbc.gridx = 0;
        gbc.gridy = 0;
        changeUsername = new JButton("Change Username");
        changeUsername.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleChangeUsername();
            }
        });
        settingspanel.add(changeUsername, gbc);

        gbc.gridy = 1;
        changePassword = new JButton("Change Password");
        changePassword.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleChangePassword();
            }
        });
        settingspanel.add(changePassword, gbc);

        gbc.gridy = 2;
        changePic = new JButton("Change Avatar");
        changePic.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleChangePic();
            }
        });
        settingspanel.add(changePic, gbc);

        add(settingspanel);
    }

    public void handleChangeUsername() {
        JPanel changepanel = new JPanel(new GridLayout(3, 2, 10, 10));
        JLabel enterpassword = new JLabel("Enter Password:");
        JPasswordField passfield = new JPasswordField(15);
        JLabel enterNewUser = new JLabel("Enter New Username:");
        JTextField userfield = new JTextField(15);
        
        changepanel.add(enterpassword);
        changepanel.add(passfield);
        changepanel.add(enterNewUser);
        changepanel.add(userfield);

        int result = JOptionPane.showConfirmDialog(null, changepanel, "Change Username", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        
        if (result == JOptionPane.OK_OPTION) {
            String enteredpass = new String(passfield.getPassword());
            String entereduser = userfield.getText();

            if (enteredpass.equals(user.getString("password"))) {
                Bson update = Updates.set("username", entereduser);
                collections.updateOne(user, update);
                JOptionPane.showMessageDialog(this, "Username updated successfully!");
            } else {
                JOptionPane.showMessageDialog(this, "Incorrect password.");
            }
        }
    }

    public void handleChangePassword() {
        JPanel changepanel = new JPanel(new GridLayout(3, 2, 10, 10));
        JLabel oldPasswordLabel = new JLabel("Enter Old Password:");
        JPasswordField oldPassfield = new JPasswordField(15);
        JLabel newPasswordLabel = new JLabel("Enter New Password:");
        JPasswordField newPassfield = new JPasswordField(15);

        changepanel.add(oldPasswordLabel);
        changepanel.add(oldPassfield);
        changepanel.add(newPasswordLabel);
        changepanel.add(newPassfield);

        int result = JOptionPane.showConfirmDialog(null, changepanel, "Change Password", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        
        if (result == JOptionPane.OK_OPTION) {
            String enteredOldPass = new String(oldPassfield.getPassword());
            String enteredNewPass = new String(newPassfield.getPassword());

            if (enteredOldPass.equals(user.getString("password"))) {
                Bson update = Updates.set("password", enteredNewPass);
                collections.updateOne(user, update);
                JOptionPane.showMessageDialog(this, "Password updated successfully!");
            } else {
                JOptionPane.showMessageDialog(this, "Incorrect old password.");
            }
        }
    }

    public void handleChangePic() {
        // File chooser for image
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Choose Avatar");
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        int returnValue = fileChooser.showOpenDialog(this);

        if (returnValue == JFileChooser.APPROVE_OPTION) {
            String imagePath = fileChooser.getSelectedFile().getPath();

            Bson update = Updates.set("avatar", imagePath);
            collections.updateOne(user, update);

            JOptionPane.showMessageDialog(this, "Avatar updated successfully!");
        }
    }

    public static void main(String... args) {
        SwingUtilities.invokeLater(() -> {
            new Settings().setVisible(true);
        });
    }
}
