package com.pluralsight;

import java.util.Scanner;

public class Main {

    static void main(String[] args) {
        Scanner myScanner = new Scanner(System.in);

        welcome();



    }


    private static void welcome() {
        try {
            System.out.println("Welcome to Java Bank, were your satisfaction is our top priority.");
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


}
