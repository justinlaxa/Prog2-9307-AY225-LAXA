import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.*;

class DataRecord {
    private String title;
    private double totalSales;

    // Stores product title and accumulated sales
    public DataRecord(String title, double sales) {
        this.title = title;
        this.totalSales = sales;
    }

    // Adds sales to existing total
    public void addSales(double sales) {
        this.totalSales += sales;
    }

    public String getTitle() {
        return title;
    }

    public double getTotalSales() {
        return totalSales;
    }
}

public class LowPerformingProducts extends JFrame {

    private JTextField pathField;
    private JButton browseButton, analyzeButton, zoomInButton, zoomOutButton, helpButton;
    private JTable table;
    private DefaultTableModel model;
    private JLabel averageLabel, summaryLabel;
    private int fontSize = 14;

    public LowPerformingProducts() {

        setTitle("Low Performing Product Analyzer");
        setSize(1100, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // Apply system look and feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) { }

        // ===== TOP PANEL =====
        JPanel topPanel = new JPanel(new BorderLayout(10, 10));
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        pathField = new JTextField();
        pathField.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        browseButton = new JButton("Browse CSV");
        analyzeButton = new JButton("Analyze");
        zoomInButton = new JButton("Zoom +");
        zoomOutButton = new JButton("Zoom -");
        helpButton = new JButton("Help");

        JPanel rightButtons = new JPanel();
        rightButtons.add(browseButton);
        rightButtons.add(analyzeButton);
        rightButtons.add(zoomInButton);
        rightButtons.add(zoomOutButton);
        rightButtons.add(helpButton);

        topPanel.add(new JLabel("Select Dataset: "), BorderLayout.WEST);
        topPanel.add(pathField, BorderLayout.CENTER);
        topPanel.add(rightButtons, BorderLayout.EAST);

        add(topPanel, BorderLayout.NORTH);

        // ===== TABLE =====
        model = new DefaultTableModel(
                new String[]{"Product Title", "Total Sales (Million Units)", "Status"}, 0) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(model);
        table.setFont(new Font("Segoe UI", Font.PLAIN, fontSize));
        table.setRowHeight(28);
        table.setFillsViewportHeight(true);

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // ===== BOTTOM PANEL =====
        JPanel bottomPanel = new JPanel(new GridLayout(2, 1));
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        averageLabel = new JLabel("Average Sales: ");
        averageLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        averageLabel.setHorizontalAlignment(SwingConstants.CENTER);

        summaryLabel = new JLabel(" ");
        summaryLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        summaryLabel.setHorizontalAlignment(SwingConstants.CENTER);

        bottomPanel.add(averageLabel);
        bottomPanel.add(summaryLabel);

        add(bottomPanel, BorderLayout.SOUTH);

        // ===== BUTTON ACTIONS =====
        browseButton.addActionListener(e -> openFileChooser());
        analyzeButton.addActionListener(e -> validateAndProcess());
        zoomInButton.addActionListener(e -> zoomText(2));
        zoomOutButton.addActionListener(e -> zoomText(-2));
        helpButton.addActionListener(e -> showHelpDialog());
    }

    // Adjusts table font size
    private void zoomText(int change) {
        fontSize += change;
        if (fontSize < 10) fontSize = 10;
        if (fontSize > 30) fontSize = 30;
        table.setFont(new Font("Segoe UI", Font.PLAIN, fontSize));
        table.setRowHeight(fontSize + 14);
    }

    // Opens file chooser for selecting CSV
    private void openFileChooser() {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileFilter(new FileNameExtensionFilter("CSV Files", "csv"));
        int result = chooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            pathField.setText(chooser.getSelectedFile().getAbsolutePath());
        }
    }

    // Validates file before processing
    private void validateAndProcess() {
        String filePath = pathField.getText().trim();
        File file = new File(filePath);

        if (!file.exists()) {
            JOptionPane.showMessageDialog(this, "File does not exist.");
            return;
        }

        if (!file.canRead()) {
            JOptionPane.showMessageDialog(this, "File is not readable.");
            return;
        }

        if (!filePath.toLowerCase().endsWith(".csv")) {
            JOptionPane.showMessageDialog(this, "Please select a CSV file.");
            return;
        }

        processFile(file);
    }

    // Displays help instructions
    private void showHelpDialog() {
        String message = """
        HOW TO USE THE PROGRAM:

        1. Click 'Browse CSV' to select your dataset file.
        2. Make sure the CSV contains:
           - 'title' column
           - 'total_sales' column
        3. Click 'Analyze' to process the file.
        4. Products below the average sales will be marked as LOW.
        5. Use Zoom + or Zoom - to adjust text size.

        The table will display:
        - Product Title
        - Total Sales
        - Status (LOW or OK)

        LOW products are highlighted in light red.
        """;

        JOptionPane.showMessageDialog(this, message, "Program Help",
                JOptionPane.INFORMATION_MESSAGE);
    }

    // Parses CSV line safely (handles quoted commas)
    private String[] parseCSVLine(String line) {
        List<String> result = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        boolean inQuotes = false;

        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);
            if (c == '"') {
                inQuotes = !inQuotes;
            } else if (c == ',' && !inQuotes) {
                result.add(sb.toString().trim());
                sb.setLength(0);
            } else {
                sb.append(c);
            }
        }
        result.add(sb.toString().trim());
        return result.toArray(new String[0]);
    }

    // Reads file and processes data
    private void processFile(File file) {
        Map<String, DataRecord> productMap = new HashMap<>();
        int totalRows = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {

            String header = br.readLine();
            if (header == null) {
                JOptionPane.showMessageDialog(this, "CSV file is empty.");
                return;
            }

            String[] columns = parseCSVLine(header);
            int titleIndex = -1;
            int salesIndex = -1;

            for (int i = 0; i < columns.length; i++) {
                if (columns[i].equalsIgnoreCase("title"))
                    titleIndex = i;
                if (columns[i].equalsIgnoreCase("total_sales"))
                    salesIndex = i;
            }

            if (titleIndex == -1 || salesIndex == -1) {
                JOptionPane.showMessageDialog(this, "Columns not found.");
                return;
            }

            String line;
            while ((line = br.readLine()) != null) {
                totalRows++;
                String[] parts = parseCSVLine(line);

                if (parts.length <= salesIndex)
                    continue;

                String title = parts[titleIndex].replace("\"", "");
                double sales;

                try {
                    sales = Double.parseDouble(parts[salesIndex]
                            .replace(",", "")
                            .replace("\"", ""));
                } catch (Exception e) {
                    continue;
                }

                if (productMap.containsKey(title)) {
                    productMap.get(title).addSales(sales);
                } else {
                    productMap.put(title, new DataRecord(title, sales));
                }
            }

            displayResults(productMap, totalRows);

        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error reading file.");
        }
    }

    // Displays results in table
    private void displayResults(Map<String, DataRecord> productMap, int totalRows) {

        model.setRowCount(0);

        if (productMap.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No valid data found.");
            return;
        }

        double total = 0;
        for (DataRecord r : productMap.values()) {
            total += r.getTotalSales();
        }

        double average = total / productMap.size();

        averageLabel.setText("Average Sales: " +
                String.format("%.2f", average) + " Million Units");

        List<DataRecord> list = new ArrayList<>(productMap.values());
        list.sort(Comparator.comparingDouble(DataRecord::getTotalSales));

        int lowCount = 0;
        for (DataRecord r : list) {
            String status = r.getTotalSales() < average ? "LOW" : "OK";
            if (status.equals("LOW"))
                lowCount++;

            model.addRow(new Object[]{
                    r.getTitle(),
                    String.format("%.2f", r.getTotalSales()),
                    status
            });
        }

        summaryLabel.setText("Total Rows: " + totalRows +
                "   |   Low Performing: " + lowCount);

        highlightRows(average);
    }

    // Highlights low-performing rows
    private void highlightRows(double average) {

        table.setDefaultRenderer(Object.class,
                new DefaultTableCellRenderer() {

            public Component getTableCellRendererComponent(
                    JTable table, Object value,
                    boolean isSelected, boolean hasFocus,
                    int row, int column) {

                Component c = super.getTableCellRendererComponent(
                        table, value, isSelected,
                        hasFocus, row, column);

                double sales = Double.parseDouble(
                        table.getValueAt(row, 1).toString());

                if (sales < average) {
                    c.setBackground(new Color(255, 220, 220));
                } else {
                    c.setBackground(Color.WHITE);
                }

                return c;
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() ->
                new LowPerformingProducts().setVisible(true));
    }
}