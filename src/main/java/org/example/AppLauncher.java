package org.example;
import java.util.Scanner;

public class AppLauncher {
    public static <ChildAccount> void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Get parent's email and password to create the UserAccountManager
        System.out.println("-----WELCOME TO THE CHORES APP-----\n");
        System.out.println("Chores is the ultimate app for making chores fun and rewarding. Whether you need to clean your room, do the laundry, or take out the trash, Chores will help you get it done with ease and enjoyment.\n");
        System.out.println("Let`s get you started!\n");
        System.out.print("Enter parent's email: ");
        String parentEmail = scanner.nextLine();
        System.out.print("Enter parent's password: ");
        String parentPassword = scanner.nextLine();

        UserAccountManager userAccountManager = new UserAccountManager(parentEmail, parentPassword);

        // Get child's nickname/username and password to create the ChildAccount
        System.out.print("Enter child's nickname/username: ");
        String childUsername = scanner.nextLine();
        System.out.print("Enter child's password: ");
        String childPassword = scanner.nextLine();

        ChildAccount childAccount = userAccountManager.addChildAccount(childUsername, childPassword);

        // Create ChoreManager and add predefined chores
        ChoreManager choreManager = new ChoreManager();
        choreManager.addChore(new Chore("Bed", "Make your bed in the morning", 10));
        choreManager.addChore(new Chore("Dishes", "Wash your dishes after lunch and dinner", 15));
        choreManager.addChore(new Chore("Trash", "Take out the trash bags", 10));
        choreManager.addChore(new Chore("Homework", "Do the homework for the following day", 15));
        choreManager.addChore(new Chore("Tidy", "Tidy your rooms, put clothes and toys away", 15));
        choreManager.addChore(new Chore("Table", "Help set the table for dinner", 5));

        // Create DailyScore and WeeklyReport for the child
        DailyScore dailyScore = new DailyScore();
        WeeklyReport weeklyReport = new WeeklyReport();

        // Starting the Chores App
        System.out.println("\nWelcome to the Chores App!");
        boolean isAppRunning = true;

        while (isAppRunning) {
            System.out.println("\nSelect an option:");
            System.out.println("1. Available chores");
            System.out.println("2. Custom chore");
            System.out.println("3. Mark chore as complete");
            System.out.println("4. Show daily score");
            System.out.println("5. Show weekly report");
            System.out.println("0. Exit");

            int option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1:
                    choreManager.displayAvailableChores();
                    break;
                case 2:
                    choreManager.addCustomChore(childAccount);
                    break;
                case 3:
                    Chore completedChore = choreManager.selectChoreToComplete();
                    if (completedChore != null) {
                        dailyScore.addCompletedChore(completedChore);
                        System.out.println("Chore marked as complete.");
                    } else {
                        System.out.println("Invalid chore selection.");
                    }
                    break;
                case 4:
                    System.out.println("Your total daily score so far is: " + dailyScore.getTotalScore());
                    break;
                case 5:
                    if (weeklyReport.isWeeklyReportComplete()) {
                        weeklyReport.addDailyScore(dailyScore);
                        sendWeeklyReportToParent(parentEmail, weeklyReport);
                        weeklyReport.resetReport();
                        System.out.println("Weekly report sent to parent's email.");
                    } else {
                        System.out.println("The weekly report is not complete yet. Please complete the week first.");
                    }
                    break;
                case 0:
                    isAppRunning = false;
                    break;
                default:
                    System.out.println("Invalid option. Please select a valid option.");
            }
        }

        System.out.println("\nThank you for using the Chores App. Goodbye!");
        scanner.close();
    }

    private static void sendWeeklyReportToParent(String parentEmail, WeeklyReport weeklyReport) {

        EmailManager emailManager = new EmailManager("your_sender_email@example.com", "your_sender_password");
        String reportContent = weeklyReport.generateReportContent();
        emailManager.sendWeeklyReport(parentEmail, Integer.parseInt(reportContent));
    }
}