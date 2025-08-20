package com.pluralsight;

import java.io.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class TransactionFileManager {

    private static final String FILE_PATH = "src/main/resources/transactions.csv";

    public static List<Transaction> readFile() {
        List<Transaction> transactions = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            reader.readLine(); // skip header
            String line;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                LocalDate date = LocalDate.parse(parts[0].trim());
                LocalTime time = LocalTime.parse(parts[1].trim());
                String description = parts[2].trim();
                String vendor = parts[3].trim();
                BigDecimal amount = new BigDecimal(parts[4].trim());
                transactions.add(new Transaction(date, time, description, vendor, amount));
            }

        } catch (IOException e) {
            System.out.println("Unable to read transactions file.");
        }

        return transactions;
    }

    public static void appendTransaction(Transaction transaction) {
        File file = new File(FILE_PATH);

        try {
            if (!file.getParentFile().exists()) file.getParentFile().mkdirs();
            boolean isEmpty = !file.exists() || file.length() == 0;

            try (FileWriter writer = new FileWriter(file, true)) {
                if (isEmpty) writer.write("date|time|description|vendor|amount\n");
                writer.write(transaction.toString() + "\n");
            }

        } catch (IOException e) {
            System.out.println("Error saving transaction.");
            e.printStackTrace();
        }
    }
}
