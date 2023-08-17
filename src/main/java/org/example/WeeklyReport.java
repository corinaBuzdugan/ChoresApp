package org.example;

import java.util.ArrayList;
import java.util.List;

public class WeeklyReport {
    private final List<DailyScore> dailyScores;
    private int totalWeeklyScore;

    public WeeklyReport() {
        dailyScores = new ArrayList<>();
        totalWeeklyScore = 0;
    }

    public void addDailyScore(DailyScore dailyScore) {
        dailyScores.add(dailyScore);
        totalWeeklyScore += dailyScore.getTotalScore();
    }

    public List<DailyScore> getDailyScores() {
        return dailyScores;
    }

    public int getTotalWeeklyScore() {
        return totalWeeklyScore;
    }

    public void resetReport() {
        dailyScores.clear();
        totalWeeklyScore = 0;
    }

    public boolean isWeeklyReportComplete() {
        // Assuming a complete week has 5 days (Monday to Friday)
        return dailyScores.size() == 5;
    }

    public String generateReportContent() {
        StringBuilder reportContent = new StringBuilder();
        reportContent.append("Weekly Report for Your Child's Chores\n\n");

        for (int i = 0; i < dailyScores.size(); i++) {
            DailyScore dailyScore = dailyScores.get(i);
            reportContent.append("Day ").append(i + 1).append(": ");
            reportContent.append("Total score: ").append(dailyScore.getTotalScore()).append("\n");

            List<Chore> completedChores = dailyScore.getCompletedChores();
            for (Chore chore : completedChores) {
                reportContent.append("- ").append(chore.getName()).append(": ").append(chore.getPoints()).append(" points\n");
            }

            reportContent.append("\n");
        }

        reportContent.append("Total weekly score: ").append(totalWeeklyScore).append("\n\n");
        reportContent.append("Thank you for using our Chores App!\n");
        reportContent.append("Best regards,\n");
        reportContent.append("The Chores App Team");

        return reportContent.toString();
    }
}
