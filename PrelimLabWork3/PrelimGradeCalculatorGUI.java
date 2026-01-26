import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class PrelimGradeCalculatorGUI extends JFrame {

    private RoundedTextField attendanceField, lab1Field, lab2Field, lab3Field;

    private JLabel passExamResult, excellentExamResult;
    private JLabel attendanceResult, labAvgResult, classStandingResult, remarksResult;

    public PrelimGradeCalculatorGUI() {

        setTitle("Prelim Grade Calculator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 520);
        setLocationRelativeTo(null);

        JPanel root = new JPanel(new GridBagLayout());
        root.setBackground(new Color(245, 239, 233));
        root.setBorder(new EmptyBorder(20, 20, 20, 20));
        add(root);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10,10,10,10);
        gbc.fill = GridBagConstraints.BOTH;

        /* ================= INPUT CARD ================= */
        JPanel inputCard = new RoundedPanel();
        inputCard.setLayout(new GridBagLayout());
        inputCard.setBackground(Color.WHITE);
        inputCard.setBorder(new EmptyBorder(25,25,25,25));

        GridBagConstraints in = new GridBagConstraints();
        in.gridx = 0; in.gridy = 0;
        in.insets = new Insets(8,0,8,0);
        in.fill = GridBagConstraints.HORIZONTAL;

        JLabel inputTitle = new JLabel("Student Inputs");
        inputTitle.setFont(new Font("Segoe UI", Font.BOLD, 26));
        inputTitle.setHorizontalAlignment(SwingConstants.CENTER);
        inputCard.add(inputTitle, in);

        attendanceField = new RoundedTextField(10);
        lab1Field = new RoundedTextField(10);
        lab2Field = new RoundedTextField(10);
        lab3Field = new RoundedTextField(10);

        setupEnterKeys();

        in.gridy++;
        inputCard.add(inputRow("No. of Absences", attendanceField), in);
        in.gridy++;
        inputCard.add(inputRow("Lab Work 1", lab1Field), in);
        in.gridy++;
        inputCard.add(inputRow("Lab Work 2", lab2Field), in);
        in.gridy++;
        inputCard.add(inputRow("Lab Work 3", lab3Field), in);

        JButton compute = new GradientButton("Compute Grades",
                new Color(52,152,219), new Color(41,128,185));
        JButton clear = new GradientButton("Clear",
                new Color(180,180,180), new Color(140,140,140));

        JPanel btns = new JPanel(new FlowLayout());
        btns.setOpaque(false);
        btns.add(compute);
        btns.add(clear);

        in.gridy++;
        inputCard.add(btns, in);

        gbc.gridx = 0; gbc.weightx = 0.4;
        root.add(inputCard, gbc);

        /* ================= RESULT CARD ================= */
        JPanel resultCard = new RoundedPanel();
        resultCard.setLayout(new GridBagLayout());
        resultCard.setBackground(Color.WHITE);
        resultCard.setBorder(new EmptyBorder(25,25,25,25));

        GridBagConstraints r = new GridBagConstraints();
        r.gridx = 0; r.gridy = 0;
        r.insets = new Insets(6,0,6,0);
        r.fill = GridBagConstraints.HORIZONTAL;

        JLabel resTitle = new JLabel("Results");
        resTitle.setFont(new Font("Segoe UI", Font.BOLD, 26));
        resTitle.setHorizontalAlignment(SwingConstants.CENTER);
        resultCard.add(resTitle, r);

        r.gridy++;
        JLabel reqTitle = new JLabel("Required Exam Scores");
        reqTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        resultCard.add(reqTitle, r);

        r.gridy++;
        passExamResult = resultLabel("To Pass (75): —");
        resultCard.add(passExamResult, r);

        r.gridy++;
        excellentExamResult = resultLabel("To Excellent (100): —");
        resultCard.add(excellentExamResult, r);

        r.gridy++;
        resultCard.add(new JSeparator(), r);

        r.gridy++;
        attendanceResult = resultLabel("Attendance: —");
        resultCard.add(attendanceResult, r);

        r.gridy++;
        labAvgResult = resultLabel("Lab Average: —");
        resultCard.add(labAvgResult, r);

        r.gridy++;
        classStandingResult = resultLabel("Class Standing: —");
        resultCard.add(classStandingResult, r);

        r.gridy++;
        remarksResult = resultLabel("Remarks: —");
        remarksResult.setFont(new Font("Segoe UI", Font.BOLD, 18));
        resultCard.add(remarksResult, r);

        gbc.gridx = 1; gbc.weightx = 0.6;
        root.add(resultCard, gbc);

        compute.addActionListener(e -> computeGrades());
        clear.addActionListener(e -> clear());
    }

    /* ================= LOGIC ================= */
    private void computeGrades() {
        try {
            int absences = Integer.parseInt(attendanceField.getText());
            int l1 = Integer.parseInt(lab1Field.getText());
            int l2 = Integer.parseInt(lab2Field.getText());
            int l3 = Integer.parseInt(lab3Field.getText());

            // Validate labs
            if (absences < 0 || l1 < 0 || l1 > 100 || l2 < 0 || l2 > 100 || l3 < 0 || l3 > 100) {
                throw new NumberFormatException();
            }

            // Auto-fail at 4 absences
            if (absences >= 4) {
                attendanceResult.setText("Attendance: Failed");
                remarksResult.setText("Remarks: Failure due to absences ");
                remarksResult.setForeground(Color.RED);
                passExamResult.setText("To Pass (75): —");
                excellentExamResult.setText("To Excellent (100): —");
                labAvgResult.setText("Lab Average: —");
                classStandingResult.setText("Class Standing: —");
                return;
            }

            // Attendance formula: 100 - (absences*10)
            int attendanceGrade = 100 - (absences * 10);
            double labAvg = (l1 + l2 + l3) / 3.0;
            double classStanding = (attendanceGrade * 0.40) + (labAvg * 0.60);

            int pass = (int)Math.ceil((75 - classStanding*0.30)/0.70);
            int excel = (int)Math.ceil((100 - classStanding*0.30)/0.70);

            // Clamp values 0-100
            pass = Math.max(0, Math.min(100, pass));
            excel = Math.max(0, Math.min(100, excel));

            attendanceResult.setText("Attendance: " + attendanceGrade);
            labAvgResult.setText(String.format("Lab Average: %.2f", labAvg));
            classStandingResult.setText(String.format("Class Standing: %.2f", classStanding));
            passExamResult.setText("To Pass (75): " + pass);
            excellentExamResult.setText("To Excellent (100): " + excel);

            remarksResult.setText(classStanding >= 75
                    ? "Remarks: Good standing "
                    : "Remarks: Below passing ");
            remarksResult.setForeground(classStanding >= 75 ? new Color(46,204,113) : Color.RED);

        } catch (Exception e) {
            remarksResult.setText("Remarks: Invalid input ❌");
            remarksResult.setForeground(Color.RED);
        }
    }

    /* ================= HELPERS ================= */
    private void setupEnterKeys() {
        attendanceField.addActionListener(e -> lab1Field.requestFocus());
        lab1Field.addActionListener(e -> lab2Field.requestFocus());
        lab2Field.addActionListener(e -> lab3Field.requestFocus());
        lab3Field.addActionListener(e -> computeGrades());
    }

    private JPanel inputRow(String label, JTextField field) {
        JPanel p = new JPanel(new BorderLayout());
        p.setOpaque(false);
        JLabel l = new JLabel(label + ":");
        l.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        p.add(l, BorderLayout.NORTH);
        p.add(field, BorderLayout.CENTER);
        return p;
    }

    private JLabel resultLabel(String text) {
        JLabel l = new JLabel(text);
        l.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        return l;
    }

    private void clear() {
        attendanceField.setText("");
        lab1Field.setText("");
        lab2Field.setText("");
        lab3Field.setText("");
        passExamResult.setText("To Pass (75): —");
        excellentExamResult.setText("To Excellent (100): —");
        attendanceResult.setText("Attendance: —");
        labAvgResult.setText("Lab Average: —");
        classStandingResult.setText("Class Standing: —");
        remarksResult.setText("Remarks: —");
        remarksResult.setForeground(Color.BLACK);
    }

    /* ================= UI COMPONENTS ================= */
    static class RoundedPanel extends JPanel {
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(getBackground());
            g2.fillRoundRect(0,0,getWidth(),getHeight(),25,25);
            super.paintComponent(g);
        }
    }

    static class RoundedTextField extends JTextField {
    private int radius = 15;

    RoundedTextField(int columns) {
        super(columns);
        setOpaque(false);
        setFont(new Font("Segoe UI", Font.PLAIN, 16));
        setBorder(BorderFactory.createEmptyBorder(5,10,5,10)); // padding inside
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Fill background solid white (like JS inputs)
        g2.setColor(Color.WHITE);
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);

        // Draw subtle gray border
        g2.setColor(new Color(180,180,180));
        g2.setStroke(new BasicStroke(1));
        g2.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, radius, radius);

        super.paintComponent(g);
    }
}


    static class GradientButton extends JButton {
        Color c1, c2;
        GradientButton(String t, Color a, Color b) {
            super(t); c1=a; c2=b;
            setContentAreaFilled(false);
            setForeground(Color.WHITE);
            setFont(new Font("Segoe UI", Font.BOLD, 15));
        }
        protected void paintComponent(Graphics g) {
            Graphics2D g2=(Graphics2D)g;
            g2.setPaint(new GradientPaint(0,0,c1,0,getHeight(),c2));
            g2.fillRoundRect(0,0,getWidth(),getHeight(),18,18);
            super.paintComponent(g);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new PrelimGradeCalculatorGUI().setVisible(true));
    }
}
