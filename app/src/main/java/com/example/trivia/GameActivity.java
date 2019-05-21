package com.example.trivia;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class GameActivity extends AppCompatActivity implements TriviaRequest.Callback {


    private Game game;
    private String url;
    private TextView category;
    private TextView typeDifficulty;
    private TextView question;
    private Button button1;
    private Button button2;
    private Button button3;
    private Button button4;
    private ArrayList<Trivia> trivaItemsList;
    private int questionCounter = 0;
    private int score = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        // Get all views from the layout
        category = findViewById(R.id.categoryView);
        typeDifficulty = findViewById(R.id.typeDifficultyView);
        question = findViewById(R.id.questionView);
        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);
        button4 = findViewById(R.id.button4);

        // Get the game configuration from the intent
        Intent intent = getIntent();
        game = (Game) intent.getSerializableExtra("game_started");

        // Create the appropriate url using the game configuration
        url = urlBuilder();

        // Create a TriviaRequest using the created url
        TriviaRequest requestTrivia = new TriviaRequest(this);
        requestTrivia.getTrivia(this, url);
    }


    @Override
    public void gotTrivia(ArrayList<Trivia> triviaItemsList) {
        this.trivaItemsList = triviaItemsList;
        if (game.getCategory() == 8) {
            category.setText("Any Category");
        } else {
            category.setText(triviaItemsList.get(0).getCategory());
        }

        String text = "";
        if (game.getDifficulty() == null){
            text += "Any Difficulty - ";
        } else {
            text += capitalizeString(game.getDifficulty() + " Difficulty - ");
        }
        if (game.getType() == null) {
            text += "Any Type";
        } else {
            text += capitalizeString(game.getType());
        }
        typeDifficulty.setText(text);

        nextTrivia();
    }

    @Override
    public void gotTriviaError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    // Method for capitalizing string's first letter
    private String capitalizeString(String string) {
        String[] names = string.split("\\s+");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < names.length; i++) {
            if (i != 0) {
                sb.append(' ');
            }
            sb.append(Character.toUpperCase(names[i].charAt(0)));
            sb.append(names[i].substring(1).toLowerCase());
        }
        return sb.toString();
    }

    // Method for getting the next trivia
    private void nextTrivia() {
        Log.d("questioncounter", String.valueOf(questionCounter));
        Trivia trivia = trivaItemsList.get(questionCounter);
        int answersNumber = trivia.getIncorrectAnswers().size() + 1;
        question.setText(trivia.getQuestion());

        /* Shuffle buttons randomly, from:
         * https://www.geeksforgeeks.org/collections-shuffle-java-examples/
         */
        ArrayList<Button> buttonList = new ArrayList<>();
        buttonList.add(button1);
        buttonList.add(button2);
        if (answersNumber == 2) {
            button3.setVisibility(View.INVISIBLE);
            button4.setVisibility(View.INVISIBLE);
        } else {
            button3.setVisibility(View.VISIBLE);
            button4.setVisibility(View.VISIBLE);
            buttonList.add(button3);
            buttonList.add(button4);
        }
        Collections.shuffle(buttonList);
        Log.d("button", String.valueOf(buttonList.get(0).getId()));

        buttonList.get(0).setText(trivia.getCorrectAnswer());
        buttonList.get(1).setText(trivia.getIncorrectAnswers().get(0));
        if (answersNumber == 4) {
            buttonList.get(2).setText(trivia.getIncorrectAnswers().get(1));
            buttonList.get(3).setText(trivia.getIncorrectAnswers().get(2));
        }
        questionCounter += 1;
    }

    // Method that builds the url String for the JSONRequest
    public String urlBuilder() {
        StringBuilder sb = new StringBuilder();
        sb.append("https://opentdb.com/api.php?amount="); //start of the url
        sb.append(game.getQuestionsNumber()); // number of questions added
        if (game.getCategory() != 8) {
            sb.append("&category="+game.getCategory());
        }
        if (game.getDifficulty() != null) {
            sb.append("&difficullty="+game.getDifficulty());
        }
        if (game.getType() != null) {
            sb.append("&type="+game.getType());
        }
        Log.d("url", sb.toString()); //TODO remove later
        return sb.toString();
    }

    public void buttonClicked(View view) {
        Button buttonClicked = (Button) view;

        if (buttonClicked.getText().equals(trivaItemsList.get(questionCounter).getCorrectAnswer())) {
            score += 1;
        }
        Log.d("score", String.valueOf(score) + " " + buttonClicked.getText() + " - " + trivaItemsList.get(questionCounter).getCorrectAnswer());
        nextTrivia();
    }
}
