package com.example.trivia;
/**
 * The Trivia class for the app.
 * This is the class that holds the information for the trivia's. This includes the category, type,
 * difficulty, number of questions, the correct answer and a list of incorrect answers.
 */

// List of imports.
import java.io.Serializable;
import java.util.ArrayList;

public class Trivia implements Serializable {

    private String category;
    private String type;
    private String difficulty;
    private String question;
    private String correctAnswer;
    private ArrayList<String> incorrectAnswers;

    // Constructor sets variables.
    public Trivia(String category, String type, String difficulty, String question,
                  String correctAnswer, ArrayList<String> incorrectAnswers) {
        this.category = category;
        this.type = type;
        this.difficulty = difficulty;
        this.question = question;
        this.correctAnswer = correctAnswer;
        this.incorrectAnswers = incorrectAnswers;
    }

    // List of getters.
    public String getCategory() { return category; }
    public String getType() { return type; }
    public String getDifficulty() { return difficulty; }
    public String getQuestion() { return question; }
    public String getCorrectAnswer() { return correctAnswer; }
    public ArrayList<String> getIncorrectAnswers() {
        return incorrectAnswers;
    }
}
