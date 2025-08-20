package com.pluralsight;

import java.util.List;
import java.util.Scanner;

public class UserInterface {

    private static boolean running = true;

    public static void main(String[] args) {

        List<Transaction> transactions = TransactionFileManager.readFile();
        Scanner scanner = new Scanner(System.in);

        while (running) {
            System.out.println("\n=== Financial Ledger Home ===");
            System.out.println("1) Add Deposit");
            System.out.println("2) Make Payment");
            System.out.println("3) Ledger Options");
            System.out.println("4) Exit");
            System.out.print("Choose an option: ");

            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    Ledger.addDeposit(transactions, scanner);
                    break;
                case "2":
                    Ledger.makePayment(transactions, scanner);
                    break;
                case "3":
                    showLedgerMenu(transactions, scanner);
                    break;
                case "4":
                    System.out.println("Exiting ledger. Goodbye!");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid selection, try again.");
            }
        }

        scanner.close();
    }

    private static void showLedgerMenu(List<Transaction> transactions, Scanner scanner) {
        boolean inMenu = true;

        while (inMenu) {
            System.out.println("\n--- Ledger Menu ---");
            System.out.println("1) Show All Transactions");
            System.out.println("2) Show Only Deposits");
            System.out.println("3) Show Only Payments");
            System.out.println("4) Reports");
            System.out.println("5) Back to Home");
            System.out.print("Choose an option: ");

            String option = scanner.nextLine().trim();

            switch (option) {
                case "1":
                    Ledger.viewAllLedger(transactions);
                    break;
                case "2":
                    Ledger.depositsFilter(transactions);
                    break;
                case "3":
                    Ledger.paymentsFilter(transactions);
                    break;
                case "4":
                    showReportsMenu(transactions, scanner);
                    break;
                case "5":
                    inMenu = false;
                    break;
                default:
                    System.out.println("Invalid selection, try again.");
            }
        }
    }

    private static void showReportsMenu(List<Transaction> transactions, Scanner scanner) {
        boolean inReports = true;

        while (inReports) {
            System.out.println("\n=== Reports Menu ===");
            System.out.println("1) Month-to-Date Transactions");
            System.out.println("2) Previous Month Transactions");
            System.out.println("3) Year-to-Date Transactions");
            System.out.println("4) Previous Year Transactions");
            System.out.println("5) Search by Vendor");
            System.out.println("6) Back to Ledger Menu");
            System.out.print("Select an option: ");

            String option = scanner.nextLine().trim();

            switch (option) {
                case "1":
                    Ledger.monthToDate(transactions);
                    break;
                case "2":
                    Ledger.previousMonth(transactions);
                    break;
                case "3":
                    Ledger.yearToDate(transactions);
                    break;
                case "4":
                    Ledger.previousYear(transactions);
                    break;
                case "5":
                    Ledger.searchByVendor(transactions, scanner);
                    break;
                case "6":
                    inReports = false;
                    break;
                default:
                    System.out.println("Invalid selection, try again.");
            }
        }
    }
}

