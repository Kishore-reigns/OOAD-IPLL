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
        getContentPane().setBackground(Color.decode("#2B2B2B"));
        setLayout(new BorderLayout());  // Use BorderLayout to arrange components

        // Back button in a separate panel at the top
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout(FlowLayout.LEFT));  // Align to the left
        JButton backButton = new JButton("<-");
        backButton.setPreferredSize(new Dimension(70, 30));  // Size for the button
        backButton.setBackground(Color.DARK_GRAY);
        backButton.setForeground(Color.white);
        topPanel.add(backButton);
        JLabel info = new JLabel("More lessons are on the way ! stay tuned !!"); 
        info.setForeground(Color.white);     
        topPanel.add(info,BorderLayout.CENTER);
        topPanel.setBackground(Color.decode("#2B2B2B"));

        add(topPanel, BorderLayout.NORTH);  // Add topPanel to the top (NORTH)

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LearnJava.this.setVisible(false);
                LearnHub mp = new LearnHub();
                mp.setVisible(true);
                LearnJava.this.dispose();
            }
        });

        // Panel for the lesson buttons
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(0, 2, 20, 20));  // 2 columns with gaps
        buttonPanel.setBackground(Color.decode("#2B2B2B"));
        
        JScrollPane scrollPane = new JScrollPane(buttonPanel);

        add(scrollPane, BorderLayout.CENTER);  // Add the buttonPanel to the center (main area)

        // Connect to MongoDB
        mongoClient = MongoClients.create("mongodb://localhost:27017");
        database = mongoClient.getDatabase("ipllDb");

        loadCollections();  // Load the lessons/collections
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
            lessonButton.setPreferredSize(new Dimension(200, 100));  // Adjust button size
            lessonButton.setFocusable(false);
            lessonButton.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));  // Add padding
            lessonButton.setFont(new Font("Serif", Font.BOLD, 20));  // Set font style and size
            lessonButton.setBackground(new Color(173, 216, 230)); // Light blue background
            lessonButton.setForeground(Color.BLACK); // Black text
            lessonButton.setOpaque(true); // Make background visible
            lessonButton.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2)); // Border color
            lessonButton.setToolTipText(database.getCollection(collectionName).find().first().getString("lessonName"));

            lessonButton.addActionListener(e -> openLesson(collectionName));
            lessonButton.addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    lessonButton.setBackground(new Color(135, 206, 250)); // Change to a lighter blue
                    lessonButton.setForeground(Color.WHITE); // Change text color on hover
                }

                @Override
                public void mouseExited(java.awt.event.MouseEvent evt) {
                    lessonButton.setBackground(new Color(173, 216, 230)); // Revert to original background
                    lessonButton.setForeground(Color.BLACK); // Revert to original text color
                }
            });
            buttonPanel.add(lessonButton);
        }
        buttonPanel.revalidate();
        buttonPanel.repaint();
    }
    private void openLesson(String collectionName) {
        MongoCollection<Document> collection = database.getCollection(collectionName);
        Document lesson = collection.find().first(); // Get the lesson document

        // Check if the lesson exists
        if (lesson == null) {
            JOptionPane.showMessageDialog(this, "Lesson not found.");
            return;
        }

        // Check if the lesson has been completed before
        if (!lesson.getBoolean("prevCompleted")) {
            JOptionPane.showMessageDialog(this, "Complete the previous lesson to unlock " + lesson.getString("lessonName"));
            return;
        }

        // Check if the lesson is theory-based or quiz-based
        String lessonType = lesson.getString("type");

        if ("theory".equals(lessonType)) {
            displayTheoryLesson(lesson);
        } else if (lesson.containsKey("slides")) {
            List<Document> slides = (List<Document>) lesson.get("slides");
            if (slides != null && !slides.isEmpty()) {
                displayQuizLesson(lesson, slides, collectionName);
            } else {
                JOptionPane.showMessageDialog(this, "No slides available for this lesson.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Lesson content not available.");
        }
    }

    private void displayTheoryLesson(Document lesson) {
        JFrame lessonFrame = new JFrame("Lesson: " + lesson.getString("lessonName"));
        lessonFrame.setSize(900, 600);
        lessonFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        lessonFrame.setLocationRelativeTo(this);

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(Color.decode("#2B2B2B")); // Set the slide background

        lessonFrame.add(contentPanel);

        JTextArea contentArea = new JTextArea(lesson.getString("content"));
        contentArea.setLineWrap(true);
        contentArea.setWrapStyleWord(true);
        contentArea.setEditable(false);
        contentArea.setBackground(Color.decode("#2B2B2B")); // Set background of theory content
        contentArea.setForeground(Color.WHITE); // Set text color for readability
        contentPanel.add(new JScrollPane(contentArea));

        lessonFrame.setVisible(true);
    }

    private void displayQuizLesson(Document lesson, List<Document> slides, String collectionName) {
        currentSlide = 0;
        score = 0;

        JFrame lessonFrame = new JFrame("Lesson: " + lesson.getString("lessonName"));
        lessonFrame.setSize(900, 600);
        lessonFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        lessonFrame.setLocationRelativeTo(this);
        lessonFrame.setBackground(Color.decode("#2B2B2B"));
        

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(Color.decode("#2B2B2B"));
        lessonFrame.add(contentPanel);

        loadSlide(contentPanel, slides, collectionName, lessonFrame);

        lessonFrame.setVisible(true);
    }

    private void loadSlide(JPanel contentPanel, List<Document> slides, String collectionName, JFrame lessonFrame) {
        contentPanel.removeAll();
        Document currentSlideDoc = slides.get(currentSlide);

        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));

        JTextArea contentArea = new JTextArea(currentSlideDoc.getString("content"));
        contentArea.setLineWrap(true);
        contentArea.setWrapStyleWord(true);
        contentArea.setEditable(false);
        contentArea.setFont(new Font("Serif", Font.PLAIN, 18));
        contentArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        contentArea.setBackground(Color.decode("#2B2B2B")); // Set background color of quiz content
        contentArea.setForeground(Color.WHITE); // Set text color for readability
        contentPanel.add(contentArea);

        JPanel optionsPanel = new JPanel();
        optionsPanel.setLayout(new GridLayout(4, 1, 10, 10));
        optionsPanel.setBackground(Color.decode("#3B3B3B")); // Set lighter background for options pane
        contentPanel.add(optionsPanel);

        if (currentSlideDoc.containsKey("options")) {
            List<Object> options = (List<Object>) currentSlideDoc.get("options");
            int correctIndex = currentSlideDoc.getInteger("ansindex");

            ButtonGroup buttonGroup = new ButtonGroup();
            List<JRadioButton> optionButtons = new ArrayList<>();

            for (int i = 0; i < options.size(); i++) {
                int optionIndex = i;
                JRadioButton optionButton = new JRadioButton(options.get(i).toString());
                optionButton.setFont(new Font("Serif", Font.PLAIN, 16));
                optionButton.setBackground(Color.decode("#3B3B3B")); // Background for option buttons
                optionButton.setForeground(Color.WHITE); // Text color for option buttons
                optionButtons.add(optionButton);
                buttonGroup.add(optionButton);
                optionsPanel.add(optionButton);

                optionButton.addActionListener(e -> {
                    for (JRadioButton btn : optionButtons) {
                        btn.setBorder(BorderFactory.createEmptyBorder());
                    }

                    if (optionIndex == correctIndex) {
                        optionButton.setBorder(BorderFactory.createLineBorder(Color.GREEN, 3));
                    } else {
                        optionButton.setBorder(BorderFactory.createLineBorder(Color.RED, 3));
                        optionButtons.get(correctIndex).setBorder(BorderFactory.createLineBorder(Color.GREEN, 3));
                    }

                    for (JRadioButton btn : optionButtons) {
                        btn.setEnabled(false);
                    }
                });
            }
        }

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.decode("#2B2B2B")); // Match the background color of the slides
        contentPanel.add(buttonPanel);

        if (currentSlide == slides.size() - 1) {
            JButton submitButton = new JButton("Submit");
            buttonPanel.add(submitButton);
            submitButton.addActionListener(e -> submitLesson(collectionName, slides.size(), lessonFrame));
        } else {
            JButton nextButton = new JButton("Next");
            buttonPanel.add(nextButton);
            nextButton.addActionListener(e -> {
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
        
        JOptionPane.showMessageDialog(this, "Congratulations you have completed "+ collectionName + " Successfully");
        
        

        lessonFrame.dispose(); // Close the lesson window
        
        try {
        	Thread.sleep(5000);
        }catch(Exception e) {
        	
        }
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
