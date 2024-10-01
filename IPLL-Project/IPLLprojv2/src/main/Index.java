package main;


import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Updates;
import org.bson.Document;
import org.bson.conversions.Bson;



public class Index extends JFrame {

	public JTextField usernameField ;
	public JPasswordField passwordField ;
	private JButton loginbutton ;
	
	 private MongoClient client;
	 private MongoDatabase database;
	 private MongoCollection<Document> collection ;
	 Document user ; 

	Font ipllfont = new Font("Times New Roman", Font.BOLD, 16);

	public Index() {
		setTitle("Login Page");
		setSize(400,300);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	    setLocationRelativeTo(null); // Center the frame on the screen

	    usernameField = new JTextField(15);
	    passwordField = new JPasswordField(15);
	    loginbutton = new JButton("Login");
	    
	     client =  MongoClients.create("mongodb://localhost:27017");
	     database = client.getDatabase("ipllDb");
	      collection = database.getCollection("User");
	      user = collection.find().first();



	    JPanel loginpanel = new JPanel() ;
	    loginpanel.setLayout(new GridBagLayout());


	    GridBagConstraints gbc = new GridBagConstraints();
	    gbc.insets = new Insets(10,10,10,10); // padding
	    gbc.fill =  GridBagConstraints.HORIZONTAL;// resize if larger

	    gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        JLabel ipll = new JLabel("I   P   L   L");
        ipll.setFont(ipllfont);
        loginpanel.add(ipll, gbc);

	    gbc.gridx = 0 ;
	    gbc.gridy = 1 ;
	    JLabel usernamelabel = new JLabel("Username : ");
	    loginpanel.add(usernamelabel,gbc);

	    gbc.gridx = 2 ;
	    gbc.gridy = 1 ;
	    loginpanel.add(usernameField,gbc);

	    gbc.gridx = 0 ;
	    gbc.gridy = 2 ;
	    JLabel passwordlabel = new JLabel("Password : ");
	    loginpanel.add(passwordlabel,gbc);

	    gbc.gridx = 2 ;
	    gbc.gridy = 2 ;
	    loginpanel.add(passwordField, gbc);

	    gbc.gridx = 1 ;
	    gbc.gridy = 3 ;
	    gbc.gridwidth = 3 ;
	    gbc.anchor = GridBagConstraints.CENTER ;
	    loginpanel.add(loginbutton,gbc);

	    loginbutton.addActionListener(new ActionListener() {
	    	@Override
			public void actionPerformed(ActionEvent e) {
	    		handleLogin();
	    	}
	    });



	    loginpanel.setBackground(Color.decode("#2B2B2B"));

	    usernamelabel.setForeground(Color.decode("#1ABA8D"));
	    passwordlabel.setForeground(Color.decode("#1ABA8D"));

	    ipll.setForeground(Color.decode("#E9B51E"));

	    loginbutton.setBackground(Color.decode("#1ABA8D"));
	    loginbutton.setForeground(Color.black);
	    loginbutton.setFocusPainted(false);

 loginbutton.addMouseListener(new MouseAdapter() {

	    	@Override
			public void mouseEntered(MouseEvent e) {
	    		 loginbutton.setBackground(Color.decode("#BA1A47"));
	    		    loginbutton.setForeground(Color.black);
	    	}

	    	@Override
			public void mouseExited(MouseEvent e) {
	    		 loginbutton.setBackground(Color.decode("#1ABA8D"));
	    		    loginbutton.setForeground(Color.black);
	    	}
	    });



	    add(loginpanel);


	}

	public void handleLogin() {

		String username = user.getString("username") ;
		String password = user.getString("password") ;
		String enteredusername = new String(usernameField.getText());
		String enteredpass = new String(passwordField.getPassword());

		if(username.equals(enteredusername) && enteredpass.equals(password)) {
			this.setVisible(false);

			MainPage mainpage = new MainPage() ;
			mainpage.setVisible(true);

		}

	}

	public static void main(String[] args) {
	    SwingUtilities.invokeLater(() -> {
	        Index index = new Index();
	        index.setVisible(true);
	    });
	}

}
