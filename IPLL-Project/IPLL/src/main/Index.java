package main;


import java.util.*;
import java.io.*;
import javax.swing.*;
import java.awt.*; 

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;



public class Index extends JFrame {
	
	public JTextField usernameField ;
	public JPasswordField passwordField ; 
	private JButton loginbutton ; 
	
	Font ipllfont = new Font("Times New Roman", Font.BOLD, 16);
	
	public Index() {
		setTitle("Login Page");
		setSize(400,300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    setLocationRelativeTo(null); // Center the frame on the screen
			
	    usernameField = new JTextField(15);
	    passwordField = new JPasswordField(15);
	    loginbutton = new JButton("Login");
	    
	  
	    
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
	    
	    gbc.gridx = 2 ; ;
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
	    	
	    	public void mouseEntered(MouseEvent e) {
	    		 loginbutton.setBackground(Color.decode("#BA1A47"));
	    		    loginbutton.setForeground(Color.black);
	    	}
	    	
	    	public void mouseExited(MouseEvent e) {
	    		 loginbutton.setBackground(Color.decode("#1ABA8D"));
	    		    loginbutton.setForeground(Color.black);
	    	}
	    });
	    
	    
	    
	    add(loginpanel);
	    
	    
	}
	
	public void handleLogin() {
		
		String username = "test" ; 
		String password = "test123" ;
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
