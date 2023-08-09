package org.example;
import java.util.HashMap;

public class ChoreManager {
    private HashMap<String, Integer> predefinedChores;

    public ChoreManager() {
        predefinedChores = new HashMap<>();
        // Add the predefined chores and their descriptions to the HashMap
        predefinedChores.put("Bed", 10);
        predefinedChores.put("Dishes", 15);
        predefinedChores.put("Trash", 10);
        predefinedChores.put("Homework", 15);
        predefinedChores.put("Tidy", 15);
        predefinedChores.put("Table", 5);
    }

    public void addCustomChore(String choreName, int points) {
        // Add the custom chore and its points to the HashMap
        predefinedChores.put(choreName, points);
    }

    public HashMap<String, Integer> getAvailableChores() {
        // Return the HashMap of predefined chores and their points
        return predefinedChores;
    }

    public String getChoreDescription(String choreName) {
        // Get the description of a specific chore

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

    public void displayAvailableChores() {
    }

    public <ChildAccount> void addCustomChore(ChildAccount childAccount) {
    }

    public Chore selectChoreToComplete() {
        return null;
    }

    public void addChore(Chore bed) {
    }
}
