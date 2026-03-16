import java.io.*;
import java.util.*;

public class DisplayMP10 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Ask user for CSV file path
        System.out.print("Enter CSV file path: ");
        String filePath = scanner.nextLine();

        List<String> rows = new ArrayList<>();
        Set<String> duplicates = new LinkedHashSet<>(); // store duplicates
        Set<String> seen = new HashSet<>(); // track seen rows

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (!seen.add(line)) { // if line already exists, it's a duplicate
                    duplicates.add(line);
                }
                rows.add(line);
            }

            if (duplicates.isEmpty()) {
                System.out.println("\nNo duplicate records found.");
            } else {
                System.out.println("\nDuplicate Records Found:");
                for (String dup : duplicates) {
                    System.out.println(dup);
                }
            }

        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + filePath);
        } catch (IOException e) {
            System.out.println("Error reading file.");
        }
    }
}