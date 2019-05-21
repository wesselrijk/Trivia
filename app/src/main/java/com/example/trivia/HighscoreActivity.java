package com.example.trivia;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.util.ArrayList;

public class HighscoreActivity  extends AppCompatActivity implements HighscoreRequest.Callback {

    private Game game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scores);
        Intent intent = getIntent();
        game = (Game) intent.getSerializableExtra("game_finished");
        highScorePrompt();



        // Create a TriviaRequest using the created url
        //HighscoreRequest requestHighscores = new HighscoreRequest(this);
        //requestHighscores.getScores(this);
    }

    @Override
    public void gotScores(ArrayList<Trivia> trivias) {

    }

    @Override
    public void gotScoresError(String message) {

    }

    private void highScorePrompt() {
        final EditText input = new EditText(HighscoreActivity.this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);
        final AlertDialog alertDialog = new AlertDialog.Builder(HighscoreActivity.this).create();
        alertDialog.setTitle("Your score is: " + String.valueOf(game.getScore()) + "!");
        alertDialog.setMessage("Fill in your name to add it to the Highscore list:");
        alertDialog.setView(input);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Enter",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        String playerName = String.valueOf(input.getText());
                        Log.d("name", playerName);
                        game.setPlayerName(playerName);
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }
}
