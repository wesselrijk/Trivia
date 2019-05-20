package com.example.trivia;

import java.io.Serializable;
import java.util.ArrayList;

public class Trivia implements Serializable {

    private String category;
    private String type;
    private String difficulty;
    private String question;
    private String correctAnswer;
    private ArrayList<String> incorrectAnswers;

    public Trivia(String category, String type, String difficulty, String question,
                  String correctAnswer, ArrayList<String> incorrectAnswers) {
        this.category = category;
        this.type = type;
        this.difficulty = difficulty;
        this.question = question;
        this.correctAnswer = correctAnswer;
        this.incorrectAnswers = incorrectAnswers;
    }

    public String getCategory() { return category; }
    public String getType() { return type; }
    public String getDifficulty() { return difficulty; }
    public String getQuestion() { return question; }
    public String getCorrectAnswer() { return correctAnswer; }
    public ArrayList<String> getIncorrectAnswers() {
        return incorrectAnswers;
    }
}
