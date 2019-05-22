package com.example.trivia;

import java.io.Serializable;

public class Score implements Serializable {

    private String difficulty;
    private String score;
    private String questions;
    private String category;
    private String type;
    private String playerName;

    public Score(String difficulty, String score, String questions, String category, String type,
                 String playerName) {
        this.difficulty = difficulty;
        this.score = score;
        this.questions = questions;
        this.category = category;
        this.type = type;
        this.playerName = playerName;
    }

    public String getPlayerName() {
        return playerName;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public String getScore() {
        return score;
    }

    public String getQuestions() {
        return questions;
    }

    public String getCategory() {
        return category;
    }

    public String getType() {
        return type;
    }
}
