package com.pluralsight;

import java.util.Scanner;

public class Main {

    static void main(String[] args) {
        Scanner myScanner = new Scanner(System.in);

        welcome();


        homeScreen(myScanner);
    }


    private static void welcome() {
        try {
            System.out.println("Welcome to Java Bank, were your satisfaction is our top priority.");
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private static void homeScreen(Scanner myScanner) {
        String homeMenu = """
                D) Add Deposit - prompt the user for the deposit information and
                save it to the CSV file
                P) Make Payment (Debit) - prompt the user for the debit information
                and save it to the CSV file
                L) Ledger - display the ledger screen
                X) Exit - exit the application
                Enter your choice:""";

        char homeInput;
        do {
            System.out.print("\n" + homeMenu + " ");
            homeInput = myScanner.next().toUpperCase().charAt(0);

            switch (homeInput) {
                case 'D':
                    System.out.println("Add Deposit");
                    break;
                case 'P':
                    System.out.println("Make Payment");
                    break;
                case 'L':
                    System.out.println("Ledger");
                    ledger(myScanner);
                    break;
                case 'X':
                    System.out.println("Exit");
                    break;
                default:
                    System.out.print("\n" + homeMenu + " ");
                    homeInput = myScanner.next().toUpperCase().charAt(0);
            }

        } while (homeInput != 'X');

    }

    private static void ledger(Scanner myScanner) {
        String ledgerMenu = """
                A) All - Display all entries
                D) Deposits - Display only the entries that are deposits into the account
                P) Payments - Display only the negative entries (or payments)
                R) Reports - A new screen that allows the user to run pre-defined reports or
                to run a custom search
                H) Home - go back to the home page
                Enter your choice:""";

        char ledgerInput;

        do {
            System.out.print("\n" + ledgerMenu + " ");
            ledgerInput = myScanner.next().toUpperCase().charAt(0);

            switch (ledgerInput) {
                case 'A':
                    System.out.println("All");
                    break;
                case 'D':
                    System.out.println("Deposits");
                    break;
                case 'P':
                    System.out.println("Payments");
                    break;
                case 'R':
                    System.out.println("Reports");
                    report(myScanner);
                    break;
                case 'H':
                    System.out.println("Home");
                    homeScreen(myScanner);
                default:
                    System.out.print("\n" + ledgerMenu + " ");
                    ledgerInput = myScanner.next().toUpperCase().charAt(0);
            }

        }while (ledgerInput != 'H');
    }

    private static void report(Scanner myScanner) {

        String reportMenu = """
                1) Month To Date
                2) Previous Month
                3) Year To Date
                4) Previous Year
                5) Search by Vendor - prompt the user for the vendor name and
                display all entries for that vendor
                0) Back - go back to the Ledger page""";

        int reportInput;

        do {
            System.out.print("\n" + reportMenu + " ");
            reportInput = myScanner.nextInt();

            switch (reportInput) {
                case 1:
                    System.out.println("All");
                    break;
                case 2:
                    System.out.println("Deposits");
                    break;
                case 3:
                    System.out.println("Payments");
                    break;
                case 4:
                    System.out.println("Reports");
                    report(myScanner);
                    break;
                case 5:
                    System.out.println("Home");
                    homeScreen(myScanner);
                case 0:
                    System.out.println("Back");
                    ledger(myScanner);
                default:
                    System.out.print("\n" + reportMenu + " ");
                    reportInput = myScanner.nextInt();
            }

        }while (reportInput != '0');

    }

}
