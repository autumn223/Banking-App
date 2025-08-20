package com.pluralsight;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Scanner;

public class Ledger {

    public static void addDeposit(List<Transaction> transactions, Scanner scanner) {
        System.out.println("\n>>> Adding a Deposit <<<");
        try {
            System.out.print("Enter Name: ");
            String vendor = scanner.nextLine().trim();

            System.out.print("Enter Description: ");
            String description = scanner.nextLine().trim();

            System.out.print("Enter Amount: ");
            BigDecimal amount = new BigDecimal(scanner.nextLine().trim());

            if (amount.compareTo(BigDecimal.ZERO) <= 0) {
                System.out.println("Amount must be greater than zero.");
                return;
            }

            LocalDate date = LocalDate.now();
            LocalTime time = LocalTime.now();

            Transaction t = new Transaction(date, time, description, vendor, amount);
            TransactionFileManager.appendTransaction(t);
            transactions.add(t);

            System.out.println("Deposit recorded successfully.");

        } catch (NumberFormatException e) {
            System.out.println("Invalid number entered.");
        } catch (Exception e) {
            System.out.println("An error occurred while recording the deposit.");
        }
    }

    public static void makePayment(List<Transaction> transactions, Scanner scanner) {
        System.out.println("\n>>> Making a Payment <<<");
        try {
            System.out.print("Enter Vendor: ");
            String vendor = scanner.nextLine().trim();

            System.out.print("Enter Description: ");
            String description = scanner.nextLine().trim();

            System.out.print("Enter Payment Amount: ");
            BigDecimal amount = new BigDecimal(scanner.nextLine().trim());
            if (amount.compareTo(BigDecimal.ZERO) <= 0) {
                System.out.println("Amount must be greater than zero.");
                return;
            }

            amount = amount.negate();
            LocalDate date = LocalDate.now();
            LocalTime time = LocalTime.now();

            Transaction t = new Transaction(date, time, description, vendor, amount);
            TransactionFileManager.appendTransaction(t);
            transactions.add(t);

            System.out.println("Payment recorded successfully.");

        } catch (NumberFormatException e) {
            System.out.println("Invalid number entered.");
        } catch (Exception e) {
            System.out.println("An error occurred while making the payment.");
        }
    }

    public static void viewAllLedger(List<Transaction> transactions) {
        System.out.println("\n--- Full Ledger ---");
        System.out.println("Date | Time | Description | Vendor | Amount");
        for (Transaction t : transactions) System.out.println(t.toString());
    }

    public static void depositsFilter(List<Transaction> transactions) {
        System.out.println("\n--- Deposits ---");
        System.out.println("Date | Time | Description | Vendor | Amount");
        for (Transaction t : transactions) {
            if (t.getAmount().compareTo(BigDecimal.ZERO) > 0) System.out.println(t.toString());
        }
    }

    public static void paymentsFilter(List<Transaction> transactions) {
        System.out.println("\n--- Payments ---");
        System.out.println("Date | Time | Description | Vendor | Amount");
        for (Transaction t : transactions) {
            if (t.getAmount().compareTo(BigDecimal.ZERO) < 0) System.out.println(t.toString());
        }
    }

    public static void monthToDate(List<Transaction> transactions) {
        LocalDate today = LocalDate.now();
        System.out.println("\n--- Month-to-Date Transactions ---");
        System.out.println("Date | Time | Description | Vendor | Amount");

        for (Transaction t : transactions) {
            if (t.getDate().getMonth() == today.getMonth() && t.getDate().getYear() == today.getYear()) {
                System.out.println(t.toString());
            }
        }
    }

    public static void previousMonth(List<Transaction> transactions) {
        LocalDate lastMonth = LocalDate.now().minusMonths(1);
        System.out.println("\n--- Previous Month Transactions ---");
        System.out.println("Date | Time | Description | Vendor | Amount");

        for (Transaction t : transactions) {
            if (t.getDate().getMonth() == lastMonth.getMonth() && t.getDate().getYear() == lastMonth.getYear()) {
                System.out.println(t.toString());
            }
        }
    }

    public static void yearToDate(List<Transaction> transactions) {
        int year = LocalDate.now().getYear();
        System.out.println("\n--- Year-to-Date Transactions ---");
        System.out.println("Date | Time | Description | Vendor | Amount");

        for (Transaction t : transactions) {
            if (t.getDate().getYear() == year) System.out.println(t.toString());
        }
    }

    public static void previousYear(List<Transaction> transactions) {
        int lastYear = LocalDate.now().minusYears(1).getYear();
        System.out.println("\n--- Previous Year Transactions ---");
        System.out.println("Date | Time | Description | Vendor | Amount");

        for (Transaction t : transactions) {
            if (t.getDate().getYear() == lastYear) System.out.println(t.toString());
        }
    }

    public static void searchByVendor(List<Transaction> transactions, Scanner scanner) {
        System.out.print("Enter Vendor to search: ");
        String term = scanner.nextLine().trim().toLowerCase();
        System.out.println("\n--- Search Results ---");
        System.out.println("Date | Time | Description | Vendor | Amount");

        for (Transaction t : transactions) {
            if (t.getVendor().toLowerCase().contains(term)) System.out.println(t.toString());
        }
    }
}

