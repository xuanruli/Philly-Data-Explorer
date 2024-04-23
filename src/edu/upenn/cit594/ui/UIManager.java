package edu.upenn.cit594.ui;

import edu.upenn.cit594.processor.CovidDataProcessor;

import java.io.File;
import java.util.*;

public class UIManager {

    public static void initCovidProcessor(String covidFilename){
        File covidFile = new File(covidFilename);

        // terminate if stateFile doesn't exist or cannot be read
        if (!covidFile.canRead() || !covidFile.exists()) {
            System.err.println("Failure to read covid file.");
            return;
        }

        CovidDataProcessor covidProcessor = CovidDataProcessor.getInstance();
        covidProcessor.setFile(covidFile);
    }

    public static void displayMenu() {
        Scanner scanner = new Scanner(System.in);
        int choice = 0;

        do {
            System.out.println("0. Exit the program.");
            System.out.println("1. Show the available actions");
            System.out.println("2. Show the total population for all ZIP Codes");
            System.out.println("3. Show the total vaccinations per capita for each ZIP Code for the specified date");
            System.out.println("4. Show the average market value for properties in a specified ZIP Code");
            System.out.println("5. Show the average total livable area for properties in a specified ZIP Code");
            System.out.println("6. Show the total market value of properties, per capita, for a specified ZIP Code");
            System.out.println("7. Show the results of your custom feature");
            System.out.print("> ");
            System.out.flush();

            try {
                choice = scanner.nextInt();
            } catch (Exception e) {
                System.out.println("Invalid input. Only try number from 0 to 7." );
                scanner.nextLine();
                continue;
            }

            switch (choice) {
                case 0:
                    System.out.println("Exiting the program.");
                    break;

                case 1:

                    break;
                case 3:

                    break;
                case 4:

                    break;

                case 5:

                    break;

                case 6:

                    break;

                case 7:

                    break;

                default:
                    System.out.println("Invalid input. Only try number from 0 to 7.");
            }


        } while (choice != 0);

        scanner.close();
    }
}
