import java.io.*;
import java.util.*;

public class DisplayMP11 {
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
                    headers = values; // first line is header
                    firstLine = false;
                } else {
                    rows.add(values);
                }
            }

            // Ask user which column to count
            System.out.println("\nColumns:");
            for (int i = 0; i < headers.length; i++) {
                System.out.println(i + ": " + headers[i]);
            }
            System.out.print("Enter column index to count frequency: ");
            int colIndex = scanner.nextInt();

            Map<String, Integer> frequency = new HashMap<>();
            for (String[] row : rows) {
                if (colIndex < row.length) {
                    String key = row[colIndex];
                    frequency.put(key, frequency.getOrDefault(key, 0) + 1);
                }
            }

            System.out.println("\nFrequency Count for column '" + headers[colIndex] + "':");
            for (Map.Entry<String, Integer> entry : frequency.entrySet()) {
                System.out.println(entry.getKey() + " : " + entry.getValue());
            }

        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + filePath);
        } catch (IOException e) {
            System.out.println("Error reading file.");
        } catch (InputMismatchException e) {
            System.out.println("Invalid input for column index.");
        }
    }
}
