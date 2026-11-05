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
                    addDeposit(myScanner, today, format);
                    break;
                case "P":
                    System.out.println("\nMake Payment");
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
    private static String depositInfo(Scanner homeScanner, LocalDateTime today, DateTimeFormatter format) {

        double amount = 0;
        String answer = "";
        String amountString = "";
        String dateTime = today.format(format);
        String deposit = "";

        do {

            System.out.print("Enter Description: ");
            String description = homeScanner.nextLine();

            System.out.print("Enter Vendor: ");
            String vendor = homeScanner.nextLine();

            do {

                while (true) {
                    System.out.print("Enter Deposit Amount: ");
                    amountString = homeScanner.nextLine();
                    try {
                        amount = Double.parseDouble(amountString);
                        deposit = dateTime + "|" + description + "|" + vendor + "|" + amount;
                        return deposit;
                    } catch (NumberFormatException e) {
                        System.out.print("Quit try to break my code.\n");
                    }


                    if (amount == 0) {
                        System.out.println("Insufficient amount please try again.");
                    } else if (amount < 0) {
                        System.out.print("This is a negative amount, would you like to pay a payment (Yes/No)? ");
                        answer = homeScanner.nextLine().toUpperCase();


                        switch (answer) {
                            case "YES":
                                makePayment(homeScanner, today, format);
                                break;
                            case "NO":
                                System.out.println("Enter a sufficient amount.");
                                break;
                            default:
                                System.out.println("Please answer Yes/No");
                                break;
                        }

                    }
                }

            } while (answer.equals("YES"));

        } while (amount < 0);

    }

    public static void addDeposit(Scanner homeScanner, LocalDateTime today, DateTimeFormatter format) {

        try {
            BufferedWriter bufWriter = new BufferedWriter(new FileWriter("src/main/resources/test.csv", true));
            String confirmation = "";

            do {
                String deposit = depositInfo(homeScanner, today, format);
                System.out.println(deposit);
                System.out.print("Please confirm: Is the deposit information correct? (Yes/No): ");
                confirmation = homeScanner.nextLine().trim().toLowerCase();

                switch (confirmation) {
                    case "yes":
                        System.out.println("Deposit Complete!");
                        bufWriter.write("\n" + deposit);
                        break;
                    case "no":
                        System.out.println("Let's try again.");
                        break;
                    default:
                        while (true) {
                            System.out.print("Invalid answer, please answer with a Yes/No: ");
                            confirmation = homeScanner.nextLine().trim().toLowerCase();

                            if (confirmation.equals("yes")) {
                                System.out.println("Deposit Complete!");
                                bufWriter.write("\n" + deposit);
                                break;
                            } else if (confirmation.equals("no")) {
                                System.out.println("Let's try again.");
                                depositInfo(homeScanner, today, format);
                                break;
                            }

                        }

                }

            } while (confirmation.equals("no"));

            bufWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    //endregion

    //region Payment
    private static String paymentInfo(Scanner homeScanner, LocalDateTime today, DateTimeFormatter format) {
        double payment = 0.0;
        String answer = "";
        String amountString = "";
        String dateTime = today.format(format);
        String paymentStatues = "";

        do {

            System.out.print("Enter Description: ");
            String description = homeScanner.nextLine();

            System.out.print("Enter Vendor: ");
            String vendor = homeScanner.nextLine();

            do {

                while (true) {
                    System.out.print("Enter Payment Amount: ");
                    amountString = homeScanner.nextLine();
                    try {
                        payment = Double.parseDouble(amountString);
                        paymentStatues = dateTime + "|" + description + "|" + vendor + "|-" + payment;

                        if (payment == 0) {
                            System.out.println("Insufficient amount please try again.");
                        } else if (payment > 0) {
                            System.out.print("This is a positive amount, would you like to make a deposit (Yes/No)? ");
                            answer = homeScanner.nextLine().toUpperCase();

                            switch (answer) {
                                case "YES":
                                    addDeposit(homeScanner, today, format);
                                    break;
                                case "NO":
                                    while (true) {
                                        System.out.print("Enter a sufficient amount: ");
                                        amountString = homeScanner.nextLine();
                                        payment = Double.parseDouble(amountString);
                                        if (payment < 0) {
                                            break;
                                        }
                                    }

                                default:
                                    System.out.println("Please answer Yes/No");
                                    break;
                            }

                            return paymentStatues;
                        }

                    } catch (NumberFormatException e) {
                        System.out.print("Quit try to break my code.\n");
                    }
                }

            } while (answer.equals("YES"));

        } while (payment < 0);
    }

    public static void makePayment(Scanner homeScanner, LocalDateTime today, DateTimeFormatter format) {

        try {
            BufferedWriter bufWriter = new BufferedWriter(new FileWriter("src/main/resources/test.csv", true));
            String confirmation = "";

            do {
                String payment = paymentInfo(homeScanner, today, format);
                System.out.println(payment);
                System.out.print("Please confirm: Is the payment information correct? (Yes/No): ");
                confirmation = homeScanner.nextLine().trim().toLowerCase();

                switch (confirmation) {
                    case "yes":
                        System.out.println("Payment Complete!");
                        bufWriter.write("\n" + payment);
                        break;
                    case "no":
                        System.out.println("Let's try again.");
                        break;
                    default:
                        while (true) {
                            System.out.print("Invalid answer, please answer with a Yes/No: ");
                            confirmation = homeScanner.nextLine().trim().toLowerCase();

                            if (confirmation.equals("yes")) {
                                System.out.println("Payment Complete!");
                                bufWriter.write("\n" + payment);
                                break;
                            } else if (confirmation.equals("no")) {
                                System.out.println("Let's try again.");
                                depositInfo(homeScanner, today, format);
                                break;
                            }

                        }

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
                A) All - Display all entries
                D) Deposits - Display only the entries that are deposits into the account
                P) Payments - Display only the negative entries (or payments)
                R) Reports - A new screen that allows the user to run pre-defined reports or
                to run a custom search
                H) Home - go back to the home page
                Enter your choice:""";

        String ledgerInput;

        do {
            System.out.print("\n" + ledgerMenu + " ");
            ledgerInput = myScanner.nextLine().toUpperCase();

            switch (ledgerInput) {
                case "A":
                    System.out.println("All");
                    all();
                    break;
                case "D":
                    System.out.println("Deposits");
                    deposits();
                    break;
                case "P":
                    System.out.println("Payments");
                    payments();
                    break;
                case "R":
                    System.out.println("Reports");
                    report(myScanner, today, format);
                    break;
                case "H":
                    System.out.println("Home");
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

            Collections.sort(allTransactions, Collections.reverseOrder());

            for (int i = 0; i < allTransactions.size(); i++){
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
                    System.out.printf("Description: %s - Vendor: %s - Amount: $%.2f%n", t.getDescription(), t.getVendor(), t.getAmount());
                }
                line = bufReader.readLine();
            }

            Collections.sort(deposit, Collections.reverseOrder());

            for (int i = 0; i < deposit.size(); i++){
                System.out.println(deposit.get(i));
            }

            bufReader.close();


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
                    System.out.printf("Description: %s - Vendor: %s - Amount: $%.2f%n", t.getDescription(), t.getVendor(), t.getAmount());
                }
                line = bufReader.readLine();
            }

            Collections.sort(payment, Collections.reverseOrder());

            for (int i = 0; i < payment.size(); i++){
                System.out.println(payment.get(i));
            }

            bufReader.close();


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
                5) Search by Vendor - prompt the user for the vendor name and
                display all entries for that vendor
                0) Back - go back to the Ledger page
                Enter your choice:""";

        int reportInput;

        do {
            System.out.print("\n" + reportMenu + " ");
            reportInput = myScanner.nextInt();

            switch (reportInput) {
                case 1:
                    System.out.println("Month to Date");
                    monthToDate(today);
                    break;
                case 2:
                    System.out.println("Previous Month");
                    previousMonth(today);
                    break;
                case 3:
                    System.out.println("Year to Date");
                    yearToDate(today);
                    break;
                case 4:
                    System.out.println("Previous Year");
                    break;
                case 5:
                    System.out.println("Search by Vendor");
                case 0:
                    System.out.println("Back");
                    break;
                default:
                    System.out.print("\n" + reportMenu + " ");
                    reportInput = myScanner.nextInt();
            }

        } while (reportInput != '0');

    }

    private static void monthToDate(LocalDateTime today) {
        int thisMonth = today.getMonthValue();
        ArrayList<Transaction> thisMonthTransactions = new ArrayList<>();

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

                int yearInFile = date.getMonthValue();

                if (thisMonth == yearInFile) {
                    Transaction t = new Transaction(date, time, description, vendor, amount);
                    thisMonthTransactions.add(t);
                    System.out.printf("Description: %s - Vendor: %s - Amount: $%.2f%n", t.getDescription(), t.getVendor(), t.getAmount());
                }

                line = bufReader.readLine();
            }

            Collections.sort(thisMonthTransactions, Collections.reverseOrder());

            for (int i = 0; i < thisMonthTransactions.size(); i++){
                System.out.println(thisMonthTransactions.get(i));
            }

            bufReader.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void previousMonth(LocalDateTime today) {
        int prevMonth = today.minusMonths(1).getMonthValue();
        ArrayList<Transaction> prevMonthTransaction = new ArrayList<>();

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

                int prevMonthFile = date.getMonthValue();

                if (prevMonth == prevMonthFile) {
                    Transaction t = new Transaction(date, time, description, vendor, amount);
                    prevMonthTransaction.add(t);
                    System.out.printf("Description: %s - Vendor: %s - Amount: $%.2f%n", t.getDescription(), t.getVendor(), t.getAmount());
                }

                line = bufReader.readLine();
            }

            Collections.sort(prevMonthTransaction, Collections.reverseOrder());

            for (int i = 0; i < prevMonthTransaction.size(); i++){
                System.out.println(prevMonthTransaction.get(i));
            }

            bufReader.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void yearToDate(LocalDateTime today) {

        int thisYear = today.getYear();
        ArrayList<Transaction> thisYearTransaction = new ArrayList<>();

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

                int yearInFile = date.getYear();

                if (thisYear == yearInFile) {
                    Transaction t = new Transaction(date, time, description, vendor, amount);
                    thisYearTransaction.add(t);
                    System.out.printf("Description: %s - Vendor: %s - Amount: $%.2f%n", t.getDescription(), t.getVendor(), t.getAmount());
                }

                line = bufReader.readLine();
            }

            Collections.sort(thisYearTransaction, Collections.reverseOrder());

            for (int i = 0; i < thisYearTransaction.size(); i++){
                System.out.println(thisYearTransaction.get(i));
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