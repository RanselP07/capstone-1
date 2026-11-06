package com.pluralsight;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Main {

    static void main(String[] args) {
        Scanner myScanner = new Scanner(System.in);
        LocalDateTime today = LocalDateTime.now();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd|HH:mm:ss");

        welcome();

        homeScreenMenu(myScanner, today, format);
    }

    // Welcome
    private static void welcome() {
        try {
            System.out.println("Welcome to Java Bank, were your satisfaction is our top priority.");
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    //region Menu & Sub-menu

    //region Home Screen
    private static void homeScreenMenu(Scanner myScanner, LocalDateTime today, DateTimeFormatter format) {
        String homeMenu = """
                D) Add Deposit
                P) Make Payment (Debit)
                L) Ledger
                X) Exit
                Enter your choice:""";

        String homeInput = "";

        do {
            System.out.print("\n" + homeMenu + " ");
            homeInput = myScanner.nextLine().toUpperCase();

            switch (homeInput) {
                case "D":
                    System.out.println("\nAdd Deposit");
                    System.out.print("\n📢 Type \"home\" anywhere in to return to the home screen.\n");
                    addDeposit(myScanner, today, format);
                    break;
                case "P":
                    System.out.println("\nMake Payment");
                    System.out.print("\n📢 Type \"home\" anywhere in to return to the home screen.\n");
                    makePayment(myScanner, today, format);
                    break;
                case "L":
                    System.out.println("\nLedger");
                    ledger(myScanner, today, format);
                    break;
                case "X":
                    System.out.println("\nExit");
                    break;
                default:
                    System.out.println("Invalid choice. Please enter D, P, L, or X.");
            }

        } while (!homeInput.equals("X"));

    }

    //region Deposit
    private static String depositInfo(Scanner myScanner, LocalDateTime today, DateTimeFormatter format) {

        String dateTime = today.format(format);

        System.out.print("Enter Description: ");
        String description = myScanner.nextLine();

        if (description.trim().equalsIgnoreCase("home")){
            homeScreenMenu(myScanner, today, format);
        }

        System.out.print("Enter Vendor: ");
        String vendor = myScanner.nextLine();

        if(vendor.trim().equalsIgnoreCase("home")){
            homeScreenMenu(myScanner, today, format);
        }

        double amount = 0;

        System.out.print("Enter Deposit Amount: $");

        try {
            do {
                String amountString = myScanner.nextLine().trim().toLowerCase();

                if(amountString.trim().equalsIgnoreCase("home")){
                    homeScreenMenu(myScanner, today, format);
                }

                amount = Double.parseDouble(amountString);

                if (amount <= 0) {
                    System.out.print("Deposit amount must be greater than $0, Try again: ");
                } else {
                    continue;
                }
            } while (amount <= 0);


        } catch (NumberFormatException e) {
            System.out.println("Nice try!! Let's do this again.");
            depositInfo(myScanner, today, format);
        }
        String stringFormat = "%s|%s|%s|$%.2f";
        return String.format(stringFormat, dateTime, description, vendor, amount);
    }

    public static void addDeposit(Scanner myScanner, LocalDateTime today, DateTimeFormatter format) {

        try {
            BufferedWriter bufWriter = new BufferedWriter(new FileWriter("src/main/resources/test.csv", true));
            String confirmation = "";


            String deposit = depositInfo(myScanner, today, format);
            System.out.print("\n" + deposit);
            System.out.print("\nPlease confirm: Is the deposit information correct? (Yes/No): ");


            do {
                confirmation = myScanner.nextLine().trim().toLowerCase();

                if (confirmation.trim().equalsIgnoreCase("home")){
                    homeScreenMenu(myScanner, today, format);
                }
                switch (confirmation) {
                    case "yes":
                        System.out.println("Deposit Complete!");
                        bufWriter.write("\n" + deposit);
                        break;
                    case "no":
                        System.out.println("Let's try again.");
                        depositInfo(myScanner, today, format);
                        break;
                    default:
                        System.out.print("Invalid answer, please answer with a Yes/No: ");

                }
            } while (!confirmation.equals("no"));


            bufWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    //endregion

    //region Payment
    private static String paymentInfo(Scanner myScanner, LocalDateTime today, DateTimeFormatter format) {

        String dateTime = today.format(format);

        System.out.print("Enter Description: ");
        String description = myScanner.nextLine();

        if (description.trim().equalsIgnoreCase("home")){
            homeScreenMenu(myScanner, today, format);
        }

        System.out.print("Enter Vendor: ");
        String vendor = myScanner.nextLine();

        if (vendor.trim().equalsIgnoreCase("home")){
            homeScreenMenu(myScanner, today, format);
        }

        double amount = 0;

        System.out.print("Enter Payment Amount: $-");

        try {
            do {
                String amountString = myScanner.nextLine().trim();

                if (amountString.trim().equalsIgnoreCase("home")){
                    homeScreenMenu(myScanner, today, format);
                }

                amount = Double.parseDouble(amountString);

                if (amount <= 0) {
                    System.out.print("Payment amount must be greater than $0 (and it already includes negative), Try again: ");
                } else {
                    continue;
                }
            } while (amount <= 0);

        } catch (NumberFormatException e) {
            System.out.println("Nice try!! Let's do this again.");
            paymentInfo(myScanner, today, format);
        }
        String stringFormat = "%s|%s|%s|$-%.2f";
        return String.format(stringFormat, dateTime, description, vendor, amount);
    }

    public static void makePayment(Scanner myScanner, LocalDateTime today, DateTimeFormatter format) {

        try {
            BufferedWriter bufWriter = new BufferedWriter(new FileWriter("src/main/resources/test.csv", true));
            String confirmation = "";


            String payment = paymentInfo(myScanner, today, format);
            System.out.print("\n" + payment);
            System.out.print("\nPlease confirm: Is the payment information correct? (Yes/No): ");

            do {
                confirmation = myScanner.nextLine().trim().toLowerCase();

                if (confirmation.trim().equalsIgnoreCase("home")){
                    homeScreenMenu(myScanner, today, format);
                }

                switch (confirmation) {
                    case "yes":
                        System.out.println("Payment Complete!");
                        bufWriter.write("\n" + payment);
                        break;
                    case "no":
                        System.out.println("Let's try again.");
                        paymentInfo(myScanner, today, format);
                        break;
                    default:
                        System.out.print("Invalid answer, please answer with a Yes/No: ");

                }

            } while (confirmation.equals("no"));

            bufWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    //endregion
    //endregion

    //region Ledger Screen
    private static void ledger(Scanner myScanner, LocalDateTime today, DateTimeFormatter format) {
        String ledgerMenu = """
                A) All
                D) Deposits
                P) Payments
                R) Reports
                H) Home
                Enter your choice:""";

        String ledgerInput;

        do {
            System.out.print("\n" + ledgerMenu + " ");
            ledgerInput = myScanner.nextLine().toUpperCase();

            switch (ledgerInput) {
                case "A":
                    System.out.println("\nAll\n");
                    all();
                    break;
                case "D":
                    System.out.println("\nDeposits\n");
                    deposits();
                    break;
                case "P":
                    System.out.println("\nPayments\n");
                    payments();
                    break;
                case "R":
                    System.out.println("\nReports");
                    report(myScanner, today, format);
                    break;
                case "H":
                    System.out.println("\nHome");
                    break;
                default:
                    System.out.println("Invalid choice. Please enter A, D, P, R, or H.");
            }

        } while (!ledgerInput.equals("H"));
    }

    public static void all() {
        try {
            BufferedReader buffReader = new BufferedReader(new FileReader("src/main/resources/test.csv"));
            ArrayList<String> allTransactions = new ArrayList<>();
            String line = buffReader.readLine();

            line = buffReader.readLine();

            while (line != null) {

                allTransactions.add(line);

                line = buffReader.readLine();
            }

            Collections.reverse(allTransactions);

            for (int i = 0; i < allTransactions.size(); i++) {
                System.out.println(allTransactions.get(i));
            }

            buffReader.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void deposits() {
        try {
            BufferedReader bufReader = new BufferedReader(new FileReader("src/main/resources/test.csv"));
            String line = bufReader.readLine();
            ArrayList<Transaction> deposit = new ArrayList<>();


            line = bufReader.readLine();

            while (line != null) {
                String[] statementInfo = line.split("\\|");
                LocalDate date = LocalDate.parse(statementInfo[0]);
                LocalTime time = LocalTime.parse(statementInfo[1]);
                String description = statementInfo[2];
                String vendor = statementInfo[3];
                double amount = Double.parseDouble(statementInfo[4]);

                if (amount > 0) {
                    Transaction t = new Transaction(date, time, description, vendor, amount);
                    deposit.add(t);
                }
                line = bufReader.readLine();
            }

            bufReader.close();

            Collections.reverse(deposit);

            for (int i = 0; i < deposit.size(); i++) {
                System.out.println(deposit.get(i));
            }

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void payments() {
        try {
            BufferedReader bufReader = new BufferedReader(new FileReader("src/main/resources/test.csv"));
            String line = bufReader.readLine();
            ArrayList<Transaction> payment = new ArrayList<>();


            line = bufReader.readLine();

            while (line != null) {
                String[] statementInfo = line.split("\\|");
                LocalDate date = LocalDate.parse(statementInfo[0]);
                LocalTime time = LocalTime.parse(statementInfo[1]);
                String description = statementInfo[2];
                String vendor = statementInfo[3];
                double amount = Double.parseDouble(statementInfo[4]);

                if (amount < 0) {
                    Transaction t = new Transaction(date, time, description, vendor, amount);
                    payment.add(t);
                }
                line = bufReader.readLine();
            }

            bufReader.close();

            Collections.reverse(payment);

            for (int i = 0; i < payment.size(); i++) {
                System.out.println(payment.get(i));
            }

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    //endregion

    //region Report Screen
    private static void report(Scanner myScanner, LocalDateTime today, DateTimeFormatter format) {

        String reportMenu = """
                1) Month To Date
                2) Previous Month
                3) Year To Date
                4) Previous Year
                5) Search by Vendor
                0) Back
                Enter your choice:""";

        String reportInput;

        do {
            System.out.print("\n" + reportMenu + " ");
            reportInput = myScanner.nextLine();

            switch (reportInput) {
                case "1":
                    System.out.println("\nMonth to Date\n");
                    monthToDate(today);
                    break;
                case "2":
                    System.out.println("\nPrevious Month\n");
                    previousMonth(today);
                    break;
                case "3":
                    System.out.println("\nYear to Date\n");
                    yearToDate(today);
                    break;
                case "4":
                    System.out.println("\nPrevious Year\n");
                    previousYear(today);
                    break;
                case "5":
                    System.out.println("\nSearch by Vendor\n");
                    System.out.print("\n📢 Type \"home\", \"ledger\", or \"report\" to return to the home screen.\n");
                    searchByVendor(myScanner, today, format);
                    break;
                case "0":
                    System.out.println("Back");
                    break;
                default:
                    System.out.print("\n" + reportMenu + " ");
                    reportInput = myScanner.nextLine();
            }

        } while (reportInput != "0");

    }

    // Month to date
    private static void monthToDate(LocalDateTime today) {
        int thisMonth = today.getMonthValue();
        int thisYear = today.getYear();
        ArrayList<Transaction> thisMonthTransactions = new ArrayList<>();

        try {
            BufferedReader bufReader = new BufferedReader(new FileReader("src/main/resources/test.csv"));
            String line = bufReader.readLine();
            line = bufReader.readLine();

            while (line != null) {
                String[] statementInfo = line.split("\\|");
                LocalDate date = LocalDate.parse(statementInfo[0]);
                int fileYear = date.getYear();
                LocalTime time = LocalTime.parse(statementInfo[1]);
                String description = statementInfo[2];
                String vendor = statementInfo[3];
                double amount = Double.parseDouble(statementInfo[4]);

                int monthInFile = date.getMonthValue();

                if (thisMonth == monthInFile && thisYear == fileYear) {
                    Transaction t = new Transaction(date, time, description, vendor, amount);
                    thisMonthTransactions.add(t);
                }

                line = bufReader.readLine();
            }

            Collections.reverse(thisMonthTransactions);

            for (int i = 0; i < thisMonthTransactions.size(); i++) {
                System.out.println(thisMonthTransactions.get(i));
            }

            bufReader.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // Previous Month
    private static void previousMonth(LocalDateTime today) {
        int prevMonth = today.minusMonths(1).getMonthValue();
        int thisYear = today.getYear();
        ArrayList<Transaction> prevMonthTransaction = new ArrayList<>();

        try {
            BufferedReader bufReader = new BufferedReader(new FileReader("src/main/resources/test.csv"));
            String line = bufReader.readLine();
            line = bufReader.readLine();

            while (line != null) {
                String[] statementInfo = line.split("\\|");
                LocalDate date = LocalDate.parse(statementInfo[0]);
                int fileYear = date.getYear();
                LocalTime time = LocalTime.parse(statementInfo[1]);
                String description = statementInfo[2];
                String vendor = statementInfo[3];
                double amount = Double.parseDouble(statementInfo[4]);

                int prevMonthFile = date.getMonthValue();

                if (prevMonth == prevMonthFile && thisYear == fileYear) {
                    Transaction t = new Transaction(date, time, description, vendor, amount);
                    prevMonthTransaction.add(t);
                }
                line = bufReader.readLine();
            }
            bufReader.close();

            Collections.reverse(prevMonthTransaction);

            for (int i = 0; i < prevMonthTransaction.size(); i++) {
                System.out.println(prevMonthTransaction.get(i));
            }

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // Year to date
    private static void yearToDate(LocalDateTime today) {

        int thisYear = today.getYear();
        int thisMonth = today.getMonthValue();
        ArrayList<Transaction> thisYearTransaction = new ArrayList<>();

        try {
            BufferedReader bufReader = new BufferedReader(new FileReader("src/main/resources/test.csv"));
            String line = bufReader.readLine();
            line = bufReader.readLine();

            while (line != null) {
                String[] statementInfo = line.split("\\|");
                LocalDate date = LocalDate.parse(statementInfo[0]);
                int fileMonth = date.getMonthValue();
                LocalTime time = LocalTime.parse(statementInfo[1]);
                String description = statementInfo[2];
                String vendor = statementInfo[3];
                double amount = Double.parseDouble(statementInfo[4]);

                int yearInFile = date.getYear();

                if (thisYear == yearInFile && thisMonth == fileMonth) {
                    Transaction t = new Transaction(date, time, description, vendor, amount);
                    thisYearTransaction.add(t);
                }

                line = bufReader.readLine();
            }

            Collections.reverse(thisYearTransaction);

            for (int i = 0; i < thisYearTransaction.size(); i++) {
                System.out.println(thisYearTransaction.get(i));
            }

            bufReader.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    private static void previousYear(LocalDateTime today) {

        int thisYear = today.minusYears(1).getYear();
        int thisMonth = today.getMonthValue();
        ArrayList<Transaction> prevYearTransaction = new ArrayList<>();

        try {
            BufferedReader bufReader = new BufferedReader(new FileReader("src/main/resources/test.csv"));
            String line = bufReader.readLine();
            line = bufReader.readLine();

            while (line != null) {
                String[] statementInfo = line.split("\\|");
                LocalDate date = LocalDate.parse(statementInfo[0]);
                int fileMonth = date.getMonthValue();
                LocalTime time = LocalTime.parse(statementInfo[1]);
                String description = statementInfo[2];
                String vendor = statementInfo[3];
                double amount = Double.parseDouble(statementInfo[4]);

                int yearInFile = date.getYear();

                if (thisYear == yearInFile && thisMonth == fileMonth) {
                    Transaction t = new Transaction(date, time, description, vendor, amount);
                    prevYearTransaction.add(t);
                }

                line = bufReader.readLine();
            }

            Collections.reverse(prevYearTransaction);

            for (int i = 0; i < prevYearTransaction.size(); i++) {
                System.out.println(prevYearTransaction.get(i));
            }

            bufReader.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private static void searchByVendor(Scanner myScanner, LocalDateTime today, DateTimeFormatter format) {

        ArrayList<Transaction> vendors = new ArrayList<>();
        System.out.print("Enter the vendor: ");
        String input = myScanner.nextLine();

        if (input.trim().equalsIgnoreCase("home")){
            homeScreenMenu(myScanner, today, format);
        }else if (input.trim().equalsIgnoreCase("ledger")){
            ledger(myScanner, today, format);
        }else if (input.trim().equalsIgnoreCase("report")){
            report(myScanner, today, format);
        }


        try {
            BufferedReader bufReader = new BufferedReader(new FileReader("src/main/resources/test.csv"));
            String line = bufReader.readLine();
            line = bufReader.readLine();

            while (line != null) {
                String[] statementInfo = line.split("\\|");
                LocalDate date = LocalDate.parse(statementInfo[0]);
                LocalTime time = LocalTime.parse(statementInfo[1]);
                String description = statementInfo[2];
                String vendor = statementInfo[3];
                double amount = Double.parseDouble(statementInfo[4]);


                if (vendor.equals(input)) {
                    Transaction t = new Transaction(date, time, description, vendor, amount);
                    vendors.add(t);
                }

                line = bufReader.readLine();
            }

            Collections.reverse(vendors);

            for (int i = 0; i < vendors.size(); i++) {
                System.out.println(vendors.get(i));
            }

            bufReader.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    //endregion
    //endregion
}