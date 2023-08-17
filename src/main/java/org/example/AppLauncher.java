package org.example;

import java.util.Scanner;

public class AppLauncher {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("-----WELCOME TO THE CHORES APP-----\n");
        System.out.println("Chores is the ultimate app for making chores fun and rewarding. Whether you need to clean your room, do the laundry, or take out the trash, Chores will help you get it done with ease and enjoyment.\n");
        System.out.println("Let's get you started!\n");

        System.out.print("Enter parent's email: ");
        String parentEmail = scanner.nextLine();

        System.out.print("Enter parent's password: ");
        String parentPassword = scanner.nextLine();

        ParentAccount parentAccount = new ParentAccount(parentEmail, parentPassword);

        if (parentAccount.createAccount()) {
            System.out.println("Parent account created successfully!");

            System.out.print("Enter child's nickname/username: ");
            String childUsername = scanner.nextLine();

            System.out.print("Enter child's password: ");
            String childPassword = scanner.nextLine();

            UserAccountManager userAccountManager = new UserAccountManager(parentEmail, parentPassword);
            int parentId = userAccountManager.getParentId();

            if (parentId != -1) {
                ChildAccount childAccount = userAccountManager.addChildAccount(childUsername, childPassword, parentId);
                if (childAccount != null) {
                    System.out.println("Child account created successfully!");
                } else {
                    System.out.println("Failed to create child account.");
                    scanner.close();
                    return;
                }
            } else {
                System.out.println("Failed to create parent account. It might already exist.");
                scanner.close();
                return;
            }

            // Create ChoreManager and add predefined chores
            ChoreManager choreManager = new ChoreManager();
            choreManager.addChore("Bed", 10);
            choreManager.addChore("Dishes", 15);
            choreManager.addChore("Trash", 10);
            choreManager.addChore("Homework", 15);
            choreManager.addChore("Tidy", 15);
            choreManager.addChore("Table", 5);

            // Add custom chore
            System.out.print("Enter custom chore name: ");
            String customChoreName = scanner.nextLine();
            System.out.print("Enter custom chore points: ");
            int customChorePoints = Integer.parseInt(scanner.nextLine());

            choreManager.addCustomChore(customChoreName, customChorePoints);

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
                        System.out.print("Enter custom chore name: ");
                        customChoreName = scanner.nextLine();
                        System.out.print("Enter custom chore points: ");
                        customChorePoints = scanner.nextInt();
                        scanner.nextLine(); // Consume the newline character
                        choreManager.addCustomChore(customChoreName, customChorePoints);
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
        } else {
            System.out.println("Parent account not found or login failed.");
            scanner.close();
        }
    }

    private static void sendWeeklyReportToParent(String parentEmail, WeeklyReport weeklyReport) {
        EmailManager emailManager = new EmailManager("testParent@yahoo.com", "Pass1");
        String reportContent = weeklyReport.generateReportContent();
        emailManager.sendWeeklyReport(parentEmail, reportContent);
    }
}


