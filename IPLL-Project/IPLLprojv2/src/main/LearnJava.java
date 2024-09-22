package main;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List; 

public class LearnJava extends JFrame {
    private JPanel buttonPanel;
    private MongoClient mongoClient;
    private MongoDatabase database;

    public LearnJava() {
        setTitle("Learn Java");
        setSize(900, 600);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());

        // Connect to MongoDB
        mongoClient = MongoClients.create("mongodb://localhost:27017");
        database = mongoClient.getDatabase("ipllDb");

        loadCollections();

        JScrollPane scrollPane = new JScrollPane(buttonPanel);
        add(scrollPane);
    }

    private void loadCollections() {
    	List<String> list = new ArrayList<>();
    	for(String name : database.listCollectionNames()) {
    		list.add(name);
    	}
    	Collections.sort(list);
        for (String collectionName : list) {
        	if(collectionName.contains("Clesson"))continue ; 
            JButton lessonButton = new JButton(collectionName);
            lessonButton.setPreferredSize(new Dimension(150, 50));
            lessonButton.setFocusable(false);
            lessonButton.setBorder(BorderFactory.createEmptyBorder());
            lessonButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    openLesson(collectionName);
                }
            });
            buttonPanel.add(lessonButton);
        }
    }

    private void openLesson(String collectionName) {
        MongoCollection<Document> collection = database.getCollection(collectionName);
        Document lesson = collection.find().first(); // Adjust to fetch the correct document

        // Display lesson content in a new window
        JFrame lessonFrame = new JFrame("Lesson: " + lesson.getString("lessonName"));
        lessonFrame.setSize(600, 400);
        lessonFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        lessonFrame.setLocationRelativeTo(this);
        
        // Create components for displaying slides here
        lessonFrame.setVisible(true);
        
        // Example: Load slides into lessonFrame
        // TODO: Implement slide navigation logic and update lesson completion status
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LearnJava learnJava = new LearnJava();
            learnJava.setVisible(true);
        });
    }
}
