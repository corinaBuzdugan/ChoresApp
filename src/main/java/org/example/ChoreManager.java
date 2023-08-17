package org.example;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ChoreManager {
    private final HashMap<String, Integer> predefinedChores;

    public ChoreManager() {
        predefinedChores = new HashMap<>();
        predefinedChores.put("Bed", 10);
        predefinedChores.put("Dishes", 15);
        predefinedChores.put("Trash", 10);
        predefinedChores.put("Homework", 15);
        predefinedChores.put("Tidy", 15);
        predefinedChores.put("Table", 5);
    }

    public void addChore(String choreName, int points) {
        predefinedChores.put(choreName, points);
    }

    public String getChoreDescription(String choreName) {
        switch (choreName) {
            case "Bed":
                return "Make your bed in the morning";
            case "Dishes":
                return "Wash your dishes after lunch and dinner";
            case "Trash":
                return "Take out the trash bags";
            case "Homework":
                return "Do the homework for the following day";
            case "Tidy":
                return "Tidy your rooms, put clothes and toys away";
            case "Table":
                return "Help set the table for dinner";
            default:
                return "No description available";
        }
    }

    public HashMap<String, Integer> getAvailableChores() {
        return new HashMap<>(predefinedChores);
    }

    public void displayAvailableChores() {
        System.out.println("Available Chores:");
        for (Map.Entry<String, Integer> entry : predefinedChores.entrySet()) {
            String choreName = entry.getKey();
            int points = entry.getValue();
            String description = getChoreDescription(choreName);
            System.out.println(choreName + " - " + description + " (" + points + " points)");
        }
    }

    public Chore selectChoreToComplete() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Select a chore to mark as complete:");
        int index = 1;
        for (String chore : predefinedChores.keySet()) {
            System.out.println(index + ". " + chore);
            index++;
        }
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character
        if (choice >= 1 && choice <= predefinedChores.size()) {
            String selectedChore = (String) predefinedChores.keySet().toArray()[choice - 1];
            return new Chore(selectedChore, getChoreDescription(selectedChore), predefinedChores.get(selectedChore));
        } else {
            return null;
        }
    }

    public void addCustomChore(String choreName, int points) {
        predefinedChores.put(choreName, points);
    }
}
