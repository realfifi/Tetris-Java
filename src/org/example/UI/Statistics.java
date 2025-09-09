package org.example.UI;

public class Statistics {
    private int score;
    private int highscore;
    private int level;
    private int lines;
    private long timeElapsed;

    public Statistics() {
        score = 0;
        highscore = 0;
        level = 1;
        lines = 0;
        timeElapsed = 0;
    }


    // Score
    public void addScore(int count) {
        score += count;
    }

    public void setScore(int count) {
        score = count;
    }

    public int getScore() {
        return score;
    }

    // Level
    public void incrementLevel() {
        level++;
    }

    public void setLevel(int count) {
        level = count;
    }

    public int getLevel() {
        return level;
    }

    // Lines
    public void incrementLines() {
        lines++;
    }

    public int getLines() {
        return lines;
    }

    // Time
    public void incremenetTime() {
        timeElapsed++;
    }

    public long getTimeElapsed() {
        return timeElapsed;
    }
}
