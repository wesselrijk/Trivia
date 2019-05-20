package com.example.trivia;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

public class GameActivity extends AppCompatActivity implements TriviaRequest.Callback {


    private Game game;
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

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
        Log.d("trivia", triviaItemsList.get(0).getQuestion());

    }

    @Override
    public void gotTriviaError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
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
}
