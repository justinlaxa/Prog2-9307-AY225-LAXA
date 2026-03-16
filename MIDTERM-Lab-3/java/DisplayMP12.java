import java.io.*;
import java.util.*;

public class DisplayMP12 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Ask user for CSV file path
        System.out.print("Enter CSV file path: ");
        String filePath = scanner.nextLine();

        List<String[]> rows = new ArrayList<>();
        String[] headers = null;

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean firstLine = true;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (firstLine) {
                    headers = values; // store headers
                    firstLine = false;
                } else {
                    rows.add(values);
                }
            }

            // Print table header
            for (String header : headers) {
                System.out.printf("%-15s", header);
            }
            System.out.println();
            System.out.println("-".repeat(headers.length * 15));

            // Print rows
            for (String[] row : rows) {
                for (String value : row) {
                    System.out.printf("%-15s", value);
                }
                System.out.println();
            }

        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + filePath);
        } catch (IOException e) {
            System.out.println("Error reading file.");
        }
    }
}
