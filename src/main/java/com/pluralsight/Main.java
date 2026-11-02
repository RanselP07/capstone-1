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
}
