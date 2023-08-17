package org.example;

public class Chore {
    private final String name;
    private final String description;
    private final int points;

    public Chore(String name, String description, int points) {
        this.name = name;
        this.description = description;
        this.points = points;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getPoints() {
        return points;
    }
}





