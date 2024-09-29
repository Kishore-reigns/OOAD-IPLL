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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LearnJava extends JFrame {
    private JPanel buttonPanel;
    private MongoClient mongoClient;
    private MongoDatabase database;
    private int currentSlide = 0;
    private int score = 0;

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
        buttonPanel.removeAll(); // Clear previous buttons if any
        List<String> list = new ArrayList<>();
        for (String name : database.listCollectionNames()) {
            list.add(name);
        }
        Collections.sort(list);
        for (String collectionName : list) {
            if (collectionName.contains("Clesson") || collectionName.contains("User")) continue;

            JButton lessonButton = new JButton(collectionName);
            lessonButton.setPreferredSize(new Dimension(150, 50));
            lessonButton.setFocusable(false);
            lessonButton.setBorder(BorderFactory.createEmptyBorder());

            lessonButton.addActionListener(e -> openLesson(collectionName));
            buttonPanel.add(lessonButton);
        }
        buttonPanel.revalidate();
        buttonPanel.repaint();
    }

    private void openLesson(String collectionName) {
        MongoCollection<Document> collection = database.getCollection(collectionName);
        Document lesson = collection.find().first(); // Get the lesson document

        // Check if slides are available
        if (lesson == null || !lesson.containsKey("slides")) {
            JOptionPane.showMessageDialog(this, "Slides not available for this lesson.");
            return;
        }

        List<Document> slides = (List<Document>) lesson.get("slides");

        if (lesson.getBoolean("prevCompleted")) {
            currentSlide = 0;
            score = 0;

            JFrame lessonFrame = new JFrame("Lesson: " + lesson.getString("lessonName"));
            lessonFrame.setSize(900, 600);
            lessonFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            lessonFrame.setLocationRelativeTo(this);

            JPanel contentPanel = new JPanel();
            contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
            lessonFrame.add(contentPanel);

            loadSlide(contentPanel, slides, collectionName, lessonFrame);

            lessonFrame.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(LearnJava.this, "Complete the previous lesson to unlock " + lesson.getString("lessonName"));
        }
    }

    private void loadSlide(JPanel contentPanel, List<Document> slides, String collectionName, JFrame lessonFrame) {
        // Make sure slides list is not empty
        if (slides == null || slides.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No slides available.");
            lessonFrame.dispose();
            return;
        }

        contentPanel.removeAll();
        Document currentSlideDoc = slides.get(currentSlide);

        // Display the content/question
        JTextArea contentArea = new JTextArea(currentSlideDoc.getString("content"));
        contentArea.setLineWrap(true);
        contentArea.setWrapStyleWord(true);
        contentArea.setEditable(false);
        contentPanel.add(new JScrollPane(contentArea));

        // Panel for options (quiz answers) in a 2x2 grid layout
        JPanel optionsPanel = new JPanel();
        optionsPanel.setLayout(new GridLayout(2, 2, 10, 10));  // 2 rows, 2 columns, with 10px gap
        contentPanel.add(optionsPanel);

        // Handle quiz options if available
        if (currentSlideDoc.containsKey("options")) {
            List<Object> options = (List<Object>) currentSlideDoc.get("options");
            int correctIndex = currentSlideDoc.getInteger("ansindex");

            ButtonGroup buttonGroup = new ButtonGroup();
            for (int i = 0; i < options.size(); i++) {
                int optionIndex = i;
                JRadioButton optionButton = new JRadioButton(options.get(i).toString());
                buttonGroup.add(optionButton);
                optionsPanel.add(optionButton);  // Adding to the options panel in a grid

                optionButton.addActionListener(e -> {
                    if (optionIndex == correctIndex) {
                        score++;
                    }
                });
            }
        }

        // Panel for navigation buttons (Next/Submit)
        JPanel buttonPanel = new JPanel();
        contentPanel.add(buttonPanel);

        // If it's the last slide, show Submit button, otherwise Next
        if (currentSlide == slides.size() - 1) {
            JButton submitButton = new JButton("Submit");
            buttonPanel.add(submitButton);
            submitButton.addActionListener(e -> {
                submitLesson(collectionName, slides.size(), lessonFrame);
            });
        } else {
            JButton nextButton = new JButton("Next");
            buttonPanel.add(nextButton);
            nextButton.addActionListener(e -> {
                // Ensure we don't go past the last slide
                if (currentSlide < slides.size() - 1) {
                    currentSlide++;
                    loadSlide(contentPanel, slides, collectionName, lessonFrame);
                }
            });
        }

        contentPanel.revalidate();
        contentPanel.repaint();
    }

    private void submitLesson(String collectionName, int totalSlides, JFrame lessonFrame) {
        // Update lesson as completed
        MongoCollection<Document> collection = database.getCollection(collectionName);
        Document lesson = collection.find().first();
        Bson updateLesson = Updates.set("lessonCompleted", true);
        collection.updateOne(lesson, updateLesson);

        // Update user score in the database
        MongoCollection<Document> userCollection = database.getCollection("User");
        Document user = userCollection.find().first();
        Bson updateUser = Updates.set("points", user.getInteger("points") + score);
        userCollection.updateOne(user, updateUser);

        SaveProgress(collectionName);

        lessonFrame.dispose(); // Close the lesson window
        loadCollections(); // Return to the lessons page
    }

    private void SaveProgress(String collectionName) {
        // Find the next lesson
        int num = Integer.parseInt(String.valueOf(collectionName.charAt(collectionName.length() - 1)));
        num += 1;

        String nextCollection = collectionName.substring(0, collectionName.length() - 1) + num;

        // Unlock the next lesson if available
        boolean found = false;
        for (String s : database.listCollectionNames()) {
            if (nextCollection.equals(s)) {
                found = true;
                break;
            }
        }

        if (found) {
            MongoCollection<Document> nextLessonCollection = database.getCollection(nextCollection);
            Document nextLesson = nextLessonCollection.find().first();
            Bson updateNextLesson = Updates.set("prevCompleted", true);
            nextLessonCollection.updateOne(nextLesson, updateNextLesson);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LearnJava learnJava = new LearnJava();
            learnJava.setVisible(true);
        });
    }
}
