package com.pluralsight;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class HomeScreen {
    private Scanner homeScanner = new Scanner(System.in);
    private LocalDateTime today = LocalDateTime.now();
    private DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd|HH:mm:ss");

    //region Deposit
    private static String depositInfo(Scanner homeScanner, LocalDateTime today, DateTimeFormatter format) {
        System.out.print("Enter Description: ");
        String description = homeScanner.nextLine();

        System.out.print("Enter Vendor: ");
        String vendor = homeScanner.nextLine();

        System.out.print("Enter Deposit Amount: ");
        String amount = homeScanner.nextLine();

        String dateTime = today.format(format);

        String deposit = dateTime + "|" + description + "|" + vendor + "|" + amount;

        System.out.println(deposit);
        return deposit;
    }

    public static void addDeposit(Scanner homeScanner, LocalDateTime today, DateTimeFormatter format) {


        try {
            BufferedWriter bufWriter = new BufferedWriter(new FileWriter("src/main/resources/test.csv", true));
            String confirmation = "";

            do {
                String deposit = depositInfo(homeScanner, today, format);

                System.out.print("Please confirm: Is the deposit information correct? (Yes/No): ");
                confirmation = homeScanner.nextLine().trim().toLowerCase();

                if (confirmation.equals("yes")) {
                    System.out.println("Deposit Complete!");
                    bufWriter.write("\n" + deposit);
                } else if (confirmation.equals("no")) {
                    System.out.println("Let's try again.");
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
        System.out.print("Enter Description: ");
        String description = homeScanner.nextLine();

        System.out.print("Enter Vendor: ");
        String vendor = homeScanner.nextLine();

        System.out.print("Enter Payment Amount: ");
        String amount = homeScanner.nextLine();

        String dateTime = today.format(format);

        String payment = dateTime + "|" + description + "|" + vendor + "|" + amount;

        System.out.println(payment);
        return payment;
    }

    public static void makePayment(Scanner homeScanner, LocalDateTime today, DateTimeFormatter format) {


        try {
            BufferedWriter bufWriter = new BufferedWriter(new FileWriter("src/main/resources/test.csv", true));
            String confirmation = "";

            do {
                String payment = paymentInfo(homeScanner, today, format);

                System.out.print("Please confirm: Is the payment information correct? (Yes/No): ");
                confirmation = homeScanner.nextLine().trim().toLowerCase();

                if (confirmation.equals("yes")) {
                    System.out.println("Deposit Complete!");
                    bufWriter.write("\n" + payment);
                } else if (confirmation.equals("no")) {
                    System.out.println("Let's try again.");
                }

            } while (confirmation.equals("no"));

            bufWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    //endregion
}