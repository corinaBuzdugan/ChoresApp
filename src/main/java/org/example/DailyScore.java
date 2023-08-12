package org.example;
import java.util.ArrayList;
import java.util.List;

public class DailyScore {
    private List<Chore> completedChores;
    private int totalScore;

    public DailyScore() {
        completedChores = new ArrayList<>();
        totalScore = 0;
    }

    public void addCompletedChore(Chore chore) {
        completedChores.add(chore);
        totalScore += chore.getPoints();
    }

    public List<Chore> getCompletedChores() {
        return completedChores;
    }

    public int getTotalScore() {
        return totalScore;
    }

    public void resetScore() {
        completedChores.clear();
        totalScore = 0;
    }
}