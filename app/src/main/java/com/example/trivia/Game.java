package com.example.trivia;

import java.io.Serializable;

public class Game implements Serializable {

    private int questionsNumber;
    private int category;
    private String difficulty;
    private String type;
    private String encoding;
    private Boolean gameOver;

    public Game() {
        this.questionsNumber = 10;
        this.category = 8;
        this.difficulty = null;
        this.type = null;
        this.encoding = null;
        this.gameOver = false;
    }

    // list of getters
    public int getQuestionsNumber() { return this.questionsNumber; }
    public int getCategory() {return this.category; }
    public String getDifficulty() {return this.difficulty; }
    public String getType() {return this.type; }
    public String getEncoding() {return this.encoding; } // TODO remove later
    public Boolean getGameOver() {return this.gameOver; }

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
    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }
    public void setGameOver(Boolean gameOver) {
        this.gameOver = gameOver;
    }
}
