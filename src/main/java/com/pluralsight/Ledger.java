package com.pluralsight;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;


public class Ledger {


    public static void all() {
        try {
            BufferedReader buffReader = new BufferedReader(new FileReader("src/main/resources/test.csv"));
            String line = buffReader.readLine();

            while (line != null) {
                System.out.println(line);

                line = buffReader.readLine();
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
            int counter = -1;


            line = bufReader.readLine();

            while (line != null) {
                String[] statementInfo = line.split("\\|");
                LocalDate date = LocalDate.parse(statementInfo[0]);
                LocalTime time = LocalTime.parse(statementInfo[1]);
                String description = statementInfo[2];
                String vendor = statementInfo[3];
                double amount = Double.parseDouble(statementInfo[4]);

                if (amount >= 0) {
                    Transaction t = new Transaction(date, time, description, vendor, amount);
                    deposit.add(t);
                    counter++;
                    System.out.printf("Description: %s - Vendor: %s - Price: $%.2f%n", t.getDescription(), t.getVendor(), t.getAmount());
                }
                line = bufReader.readLine();
            }

            bufReader.close();


        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
