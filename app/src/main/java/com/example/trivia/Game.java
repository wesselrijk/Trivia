package com.example.trivia;
/**
 * The Game class for the app.
 * This class stores the current ongoing game parameters. This inculdes the number of questions that
 * will be asked, the category, the difficulty, the type of game, the current score for the game and
 * the player's name. The questionsNumber is initially set to 10 since this is the default value for
 * the trivia game in this app. The category is initially set to 8 since this corresponds to Any
 * Category, which is the default category in this app. The score is set to 0 for any new game.
 * And finally, the player's name will initially be set to AAA, as this resembles high score input
 * for old arcades.
 */

import java.io.Serializable;

public class Game implements Serializable {

    private int questionsNumber;
    private int category;
    private String difficulty;
    private String type;
    private int score;
    private String playerName;


    // Constructor sets the variables.
    public Game() {
        this.questionsNumber = 10;
        this.category = 8;
        this.difficulty = null;
        this.type = null;
        this.score = 0;
        this.playerName = "AAA"; // Default value for name (as in the arcades!).
    }

    // List of getters.
    public int getQuestionsNumber() { return this.questionsNumber; }
    public int getCategory() {return this.category; }
    public String getDifficulty() {return this.difficulty; }
    public String getType() {return this.type; }
    public int getScore() { return  this.score; }
    public String getPlayerName() { return this.playerName; }

    // List of setters.
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
    public void setScore(int score) { this.score = score; }
    public void setPlayerName(String playerName) {this.playerName = playerName; }
}
