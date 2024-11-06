package main;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Path2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import java.util.List;

public class Profile extends JFrame {

    private MongoClient mongoClient;
    private MongoDatabase db;
    MongoCollection<Document> user;
    private String un;
    private int points, lessonscompleted;
    private String picPath;
    private List<String> badges;

    public Profile() {

        mongoClient = MongoClients.create("mongodb://localhost:27017");
        db = mongoClient.getDatabase("ipllDb");

        user = db.getCollection("User");
        Document userdoc = user.find().first();
        un = userdoc.getString("username");
        points = userdoc.getInteger("points");
        lessonscompleted = userdoc.getInteger("lessonsCompleted");
        picPath = userdoc.getString("avatar");
        
        
        
        checkaCchivements(db);
        checkaJavachivements(db);
        
        
        badges = userdoc.getList("badges", String.class);
        int badgesSize = badges.size();

        setTitle("User Profile");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null); // Using absolute layout for precise control
        setLocationRelativeTo(null);
        getContentPane().setBackground(Color.decode("#2B2B2B"));
        
        // Back Button
        JButton backButton = new JButton("<-");
        backButton.setBounds(10, 10, 70, 30); // Position top-left
        backButton.setBackground(Color.DARK_GRAY);
        backButton.setForeground(Color.white);
        add(backButton);

        // Profile Image (Circular)
        JPanel profilePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                Image profileImage = new ImageIcon(picPath).getImage();
                Shape circle = new Ellipse2D.Double(0, 0, 150, 150); // Increase circle size
                g2.setClip(circle);  // Clip to circular shape
                g2.drawImage(profileImage, 0, 0, 150, 150, null);  // Draw image within the circle
            }
        };
        profilePanel.setBounds(50, 50, 150, 150); // Adjusted position and size
        profilePanel.setBackground(Color.decode("#2B2B2B"));
        add(profilePanel);

        // User Info Panel
        JPanel userInfoPanel = new JPanel();
        userInfoPanel.setBounds(220, 50, 600, 150); // Adjusted position and size
        userInfoPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        userInfoPanel.setLayout(new GridLayout(3, 1));

        JLabel usernameLabel = new JLabel(un, SwingConstants.CENTER);
        usernameLabel.setBackground(Color.white);
        usernameLabel.setForeground(Color.white);
        usernameLabel.setFont(new Font("Arial",Font.BOLD,15));
        JLabel pointsLabel = new JLabel("Points : " + points, SwingConstants.CENTER);
        pointsLabel.setBackground(Color.white);
        pointsLabel.setForeground(Color.white);
        pointsLabel.setFont(new Font("Arial",Font.BOLD,15));
        JLabel lessonsLabel = new JLabel("Lessons Completed : " + lessonscompleted, SwingConstants.CENTER);
        lessonsLabel.setBackground(Color.white);
        lessonsLabel.setForeground(Color.white);
        lessonsLabel.setFont(new Font("Arial",Font.BOLD,15));

        userInfoPanel.add(usernameLabel);
        userInfoPanel.add(pointsLabel);
        userInfoPanel.add(lessonsLabel);
        userInfoPanel.setBackground(Color.decode("#636363"));
        add(userInfoPanel);

        // Hexagon Shapes Panel (Scrollable)
        JPanel hexagonPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Draw hexagons in a grid format
                int hexagonSize = 90; // Size of hexagon
                int spacing = 40; // Space between hexagons
                int hexagonsPerRow = 10; // Number of hexagons per row
                int xOffset = 90; // Adjusted starting x position for better alignment

                for (int i = 0; i < badgesSize; i++) {
                    int x = xOffset + i * (hexagonSize * 2 + spacing); // Updated calculation for x
                    int y = 110; // Keep y constant for a single row
                    Shape hexagon = createHexagon(x, y, hexagonSize);

                    // Set color to yellow before filling hexagon
                    g2.setColor(Color.YELLOW);
                    g2.fill(hexagon); // Fill hexagon with yellow color

                    // Set color back to black for hexagon outline
                    g2.setColor(Color.BLACK);
                    g2.draw(hexagon); // Draw hexagon outline

                    // Center the text inside the hexagon
                    String badgeText = badges.get(i);
                    Font originalFont = g2.getFont();
                    int fontSize = 12; // Adjust font size for fitting text inside hexagon
                    g2.setFont(new Font("Arial", Font.PLAIN, fontSize));

                    // Measure the width of the text
                    FontMetrics metrics = g2.getFontMetrics();
                    int textWidth = metrics.stringWidth(badgeText);

                    // Calculate the center position for the text inside the hexagon
                    int textX = x - textWidth / 2;
                    int textY = y + metrics.getHeight() / 4;

                    // Check if text overflows and handle it (truncating long text)
                    if (textWidth > hexagonSize * 2) {
                        badgeText = badgeText.substring(0, 5) + "..."; // Shorten the text
                        textWidth = metrics.stringWidth(badgeText); // Recalculate text width
                        textX = x - textWidth / 2; // Recalculate position
                    }

                    // Draw text in black to contrast with yellow hexagon
                    g2.setColor(Color.BLACK);
                    g2.drawString(badgeText, textX, textY);
                    g2.setFont(originalFont); // Restore original font
                }
            }

            private Shape createHexagon(int x, int y, int size) {
                Path2D hexagon = new Path2D.Double();
                for (int i = 0; i < 6; i++) {
                    double angle = Math.toRadians((60 * i) + 30); // Starting angle
                    int xPoint = (int) (x + size * Math.cos(angle));
                    int yPoint = (int) (y + size * Math.sin(angle));
                    if (i == 0) {
                        hexagon.moveTo(xPoint, yPoint);
                    } else {
                        hexagon.lineTo(xPoint, yPoint);
                    }
                }
                hexagon.closePath();
                return hexagon;
            }
        };

        hexagonPanel.setBackground(Color.decode("#636363"));
        hexagonPanel.setPreferredSize(new Dimension(1200, 200)); // Increased width for scrolling

        JScrollPane hexagonScrollPane = new JScrollPane(hexagonPanel);
        hexagonScrollPane.setBounds(50, 250, 800, 250); // Adjusted position and size
        hexagonScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        hexagonScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER); // Disable vertical scroll
        add(hexagonScrollPane);


        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle back button action
                Profile.this.setVisible(false);
                MainPage mp = new MainPage();
                mp.setVisible(true);
                Profile.this.dispose();
            }
        });
    }
    
    
    private void checkaCchivements(MongoDatabase db2) {
    	MongoCollection<Document>collection =  db2.getCollection("User") ;
    	 Document userdoc = collection.find().first();
    	if(db2.getCollection("Clesson3").find().first().getBoolean("lessonCompleted")) {
    		Document filter = new Document("_id", userdoc.get("_id"));
    		Document update = new Document("$push",new Document("badges","C QUICKIE"));
    		collection.updateOne(filter,update);
    	}
		
	}


	private void checkaJavachivements(MongoDatabase db2) {
		// TODO Auto-generated method stub
		
	}


	public void checkCachivements() {
    	
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Profile().setVisible(true);
        });
    }
}
