package com.example.trivia;

import java.io.Serializable;

public class Game implements Serializable {

    private int questionsNumber;
    private int category;
    private String difficulty;
    private String type;
    private Boolean gameOver;
    private int score;
    private String playerName;

    public Game() {
        this.questionsNumber = 10;
        this.category = 8;
        this.difficulty = null;
        this.type = null;
        this.gameOver = false;
        this.score = 0;
        this.playerName = "AAA";
    }

    // list of getters
    public int getQuestionsNumber() { return this.questionsNumber; }
    public int getCategory() {return this.category; }
    public String getDifficulty() {return this.difficulty; }
    public String getType() {return this.type; }
    public Boolean getGameOver() {return this.gameOver; }
    public int getScore() { return  this.score; }
    public String getPlayerName() { return this.playerName; }

    // list of setters
    public void setQuestionsNumber(int questionsNumber) {
        this.questionsNumber = questionsNumber;
    }
    public void setCategory(int category) { this.category = category; }
    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }
    public void setType(String type) {
        this.type = type;
    }
    public void setGameOver(Boolean gameOver) {
        this.gameOver = gameOver;
    }
    public void setScore(int score) { this.score = score; }
    public void setPlayerName(String playerName) {this.playerName = playerName; }
}
