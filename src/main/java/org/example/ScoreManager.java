package org.example;
import java.util.HashMap;

public class ScoreManager {
    private HashMap<String, Integer> dailyScore;
    private int weeklyScore;

    public ScoreManager() {
        dailyScore = new HashMap<>();
        weeklyScore = 0;
    }

    public void addCompletedChore(String choreName, int points) {
        // Add the completed chore and its points to the dailyScore HashMap
        dailyScore.put(choreName, points);
        // Update the total weekly score
        weeklyScore += points;
    }

    public int getTotalDailyScore() {
        // Calculate the total daily score from the dailyScore HashMap
        int totalDailyScore = 0;
        for (int points : dailyScore.values()) {
            totalDailyScore += points;
        }
        return totalDailyScore;
    }

    public int getTotalWeeklyScore() {
        // Return the total weekly score
        return weeklyScore;
    }

    public void resetWeeklyScore() {
        // Reset the weekly score to zero at the beginning of each week (on Monday)
        weeklyScore = 0;
        dailyScore.clear();
    }
}
