package edu.upenn.cit594.ui;

import edu.upenn.cit594.processor.Analysis;
import edu.upenn.cit594.processor.DataProcessor;

import java.util.*;
import java.util.function.Function;

public class UIManager {

    private final DataProcessor processor;
    private final List<Map.Entry<String, Analysis>> analysisList = new ArrayList<>();

    public UIManager(DataProcessor processor) {
        this.processor = processor;
    }

    public void addAnalysis(String desc, Analysis analysis) {
        analysisList.add(new AbstractMap.SimpleEntry<>(desc, analysis));
    }

    public void displayPrompt() {
        System.out.print("> ");
    }

    public void displayContent(String s) {
        System.out.println(s);
    }

    public void showAvailableActions() {
        List<String> actionList = new ArrayList<>(List.of(
            "Exit the program",
            "Show the available actions"
        ));

        for (var entry : analysisList) {
            actionList.add(entry.getKey());
        }

        for (int i = 0; i < actionList.size(); i++) {
            displayContent(i + ". " + actionList.get(i));
        }
    }

    public String promptAndValidateParameter(String prompt, Function<String, Boolean> validate, Scanner scanner) {
        displayContent(prompt);
        displayPrompt();

        String input = scanner.nextLine();
        while (!validate.apply(input)) {
            displayContent("Invalid input. Please try again.");
            displayContent(prompt);
            displayPrompt();
            input = scanner.nextLine();
        }

        return input;
    }

    public void run() throws Exception {
        Scanner scanner = new Scanner(System.in);
        showAvailableActions();

        int choice;
        while (true) {
            displayPrompt();

            String input = scanner.nextLine();

            try {
                choice = Integer.parseInt(input);
            } catch (Exception e) {
                displayContent("Invalid input, should be a number." );
                continue;
            }

            if (choice < 0 || choice > analysisList.size() + 1) {
                displayContent("Invalid input. Only try number from 0 to " + (analysisList.size() + 1));
                continue;
            }

            // Exit the program
            if (choice == 0) break;

            // Show the available actions
            if (choice == 1) {
                showAvailableActions();
                continue;
            }

            // Run the selected analysis
            Analysis analysis = analysisList.get(choice - 2).getValue();
            List<String> params = analysis.getExtraParamsPrompts().entrySet().stream()
                .map(entry -> promptAndValidateParameter(entry.getKey(), entry.getValue(), scanner))
                .toList();

            String result = processor.process(analysis, params);
            displayContent("BEGIN OUTPUT\n" + result + "\nEND OUTPUT");
        }

        scanner.close();
    }
}
