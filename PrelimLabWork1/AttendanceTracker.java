import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.swing.*;

public class AttendanceTracker {

    // Generate plate-style E-Signature
    public static String generatePlateStyleSignature() {
        String letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuilder sb = new StringBuilder();

        // 3 random letters
        for (int i = 0; i < 3; i++) {
            int index = (int)(Math.random() * letters.length());
            sb.append(letters.charAt(index));
        }

        sb.append(" "); // add space

        // 4 random digits
        for (int i = 0; i < 4; i++) {
            int digit = (int)(Math.random() * 10);
            sb.append(digit);
        }

        return sb.toString();
    }

    public static void main(String[] args) {
        // Main window
        JFrame frame = new JFrame("Attendance Tracker");
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setMinimumSize(new Dimension(800, 600));
        frame.setSize(1200, 800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridBagLayout());

        // Background color
        frame.getContentPane().setBackground(new Color(245, 241, 236)); 

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Labels
        JLabel nameLabel = new JLabel("Attendance Name:");
        JLabel courseLabel = new JLabel("Course / Year:");
        JLabel timeInLabel = new JLabel("Time In:");
        JLabel signatureLabel = new JLabel("E-Signature:");

        // Text fields
        JTextField nameField = new JTextField();
        JTextField timeInField = new JTextField();
        JTextField signatureField = new JTextField();

        // Courses / Year options
        String[] courses = {
            "BSCS-DS 1st year", "BSCS-DS 2nd year", "BSCS-DS 3rd year", "BSCS-DS 4th year",
            "BSIT-GD 1st year", "BSIT-GD 2nd year", "BSIT-GD 3rd year", "BSIT-GD 4th year"
        };
        JComboBox<String> courseBox = new JComboBox<>(courses);

        // Buttons
        JButton submitButton = new JButton("Submit Attendance");
        JButton generateButton = new JButton("Generate Signature");

        // Base fonts
        Font labelFontSmall = new Font("Segoe UI", Font.BOLD, 18);
        Font fieldFontSmall = new Font("Segoe UI", Font.PLAIN, 18);
        Font buttonFontSmall = new Font("Segoe UI", Font.BOLD, 20);

        Font labelFontBig = new Font("Segoe UI", Font.BOLD, 28);
        Font fieldFontBig = new Font("Segoe UI", Font.PLAIN, 28);
        Font buttonFontBig = new Font("Segoe UI", Font.BOLD, 26);

        // Apply initial small fonts
        nameLabel.setFont(labelFontSmall);
        courseLabel.setFont(labelFontSmall);
        timeInLabel.setFont(labelFontSmall);
        signatureLabel.setFont(labelFontSmall);

        nameField.setFont(fieldFontSmall);
        timeInField.setFont(fieldFontSmall);
        signatureField.setFont(fieldFontSmall);
        courseBox.setFont(fieldFontSmall);

        submitButton.setFont(buttonFontSmall);
        generateButton.setFont(buttonFontSmall);

        // Text field colors
        Color fieldBg = new Color(255, 250, 240); 
        nameField.setBackground(fieldBg);
        timeInField.setBackground(fieldBg);
        signatureField.setBackground(fieldBg);
        courseBox.setBackground(fieldBg);

        // Button colors
        Color btnColor = new Color(255, 183, 77); 
        submitButton.setBackground(btnColor);
        submitButton.setForeground(Color.WHITE);
        submitButton.setFocusPainted(false);
        submitButton.setBorder(BorderFactory.createLineBorder(btnColor, 2));
        submitButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        generateButton.setBackground(btnColor);
        generateButton.setForeground(Color.WHITE);
        generateButton.setFocusPainted(false);
        generateButton.setBorder(BorderFactory.createLineBorder(btnColor, 2));
        generateButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Time In field
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        timeInField.setText(LocalDateTime.now().format(formatter));
        timeInField.setEditable(false);

        // Initial E-Signature
        signatureField.setText(generatePlateStyleSignature());
        signatureField.setEditable(false);

        gbc.anchor = GridBagConstraints.WEST;

        // Layout components
        gbc.gridx = 0; gbc.gridy = 0;
        frame.add(nameLabel, gbc);
        gbc.gridx = 1;
        frame.add(nameField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        frame.add(courseLabel, gbc);
        gbc.gridx = 1;
        frame.add(courseBox, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        frame.add(timeInLabel, gbc);
        gbc.gridx = 1;
        frame.add(timeInField, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        frame.add(signatureLabel, gbc);
        gbc.gridx = 1;
        frame.add(signatureField, gbc);

        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        frame.add(submitButton, gbc);

        gbc.gridx = 0; gbc.gridy = 5; gbc.gridwidth = 2;
        frame.add(generateButton, gbc);

        // Submit button action
        submitButton.addActionListener(e -> {
            String name = nameField.getText().trim();
            String course = (String) courseBox.getSelectedItem();
            String timeIn = timeInField.getText();

            if (name.isEmpty() || course.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Please fill in all fields!", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                String newSignature = generatePlateStyleSignature();
                signatureField.setText(newSignature);

                JOptionPane.showMessageDialog(frame, "Attendance Recorded!\nName: " + name +
                        "\nCourse: " + course + "\nTime In: " + timeIn + "\nE-Signature: " + newSignature);

                System.out.println("Attendance Recorded:");
                System.out.println("Name: " + name);
                System.out.println("Course: " + course);
                System.out.println("Time In: " + timeIn);
                System.out.println("E-Signature: " + newSignature);

                nameField.setText("");
                courseBox.setSelectedIndex(0);
                timeInField.setText(LocalDateTime.now().format(formatter));
            }
        });

        // New E-Signature button action
        generateButton.addActionListener(e -> {
            String newSig = generatePlateStyleSignature();
            signatureField.setText(newSig);
        });

        // Responsive font scaling
        frame.addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentResized(java.awt.event.ComponentEvent e) {
                int width = frame.getWidth();
                int height = frame.getHeight();

                Font labelFont, fieldFont, buttonFont;
                if (width > 1200 || height > 800) { 
                    labelFont = labelFontBig;
                    fieldFont = fieldFontBig;
                    buttonFont = buttonFontBig;
                } else { // smaller window
                    labelFont = labelFontSmall;
                    fieldFont = fieldFontSmall;
                    buttonFont = buttonFontSmall;
                }

                // Labels
                nameLabel.setFont(labelFont);
                courseLabel.setFont(labelFont);
                timeInLabel.setFont(labelFont);
                signatureLabel.setFont(labelFont);

                // Text fields and combo box
                nameField.setFont(fieldFont);
                courseBox.setFont(fieldFont);
                timeInField.setFont(fieldFont);
                signatureField.setFont(fieldFont);

                // Buttons
                submitButton.setFont(buttonFont);
                generateButton.setFont(buttonFont);

                // Resize text fields dynamically
                Dimension newSize = new Dimension(20 * fieldFont.getSize(), fieldFont.getSize() + 10);
                nameField.setPreferredSize(newSize);
                courseBox.setPreferredSize(newSize);
                timeInField.setPreferredSize(newSize);
                signatureField.setPreferredSize(newSize);

                frame.revalidate();
                frame.repaint();
            }
        });

        // Show window
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
