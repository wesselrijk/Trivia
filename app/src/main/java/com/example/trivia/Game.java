package com.example.trivia;

import java.io.Serializable;

public class Game implements Serializable {

    private int questionsNumber;
    private String category;
    private String difficulty;
    private String type;
    private String encoding;
    private Boolean gameOver;

    public Game() {

    }
}
