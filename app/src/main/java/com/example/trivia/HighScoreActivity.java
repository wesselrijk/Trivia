package com.example.trivia;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;

public class HighScoreActivity extends AppCompatActivity implements HighScoreGetRequest.Callback, Response.ErrorListener, Response.Listener<String> {

    private Game game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scores);
        Intent intent = getIntent();
        game = (Game) intent.getSerializableExtra("game_finished");
        highScorePrompt();

    }

    @Override
    public void gotHighScores(ArrayList<Score> highScoreItemList) {
        ListView listView = findViewById(R.id.listView);

        HighScoreAdapter adapter = new HighScoreAdapter(this, R.layout.scores_row, highScoreItemList);
        listView.setAdapter(adapter);
    }

    @Override
    public void gotHighScoresError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        HighScoreActivity.this.gotHighScoresError(error.toString());
        Log.d("Volley error message.",error.toString());
    }

    @Override
    public void onResponse(String response) {
    }


    /* Create a prompt for the player to input their name, using code from:
    * https://stackoverflow.com/questions/18799216/how-to-make-a-edittext-box-in-a-dialog */
    private void highScorePrompt() {
        final EditText input = new EditText(HighScoreActivity.this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);
        final AlertDialog alertDialog = new AlertDialog.Builder(HighScoreActivity.this).create();
        alertDialog.setTitle("Your score is: " + String.valueOf(game.getScore()) + "!");
        alertDialog.setMessage("Fill in your name to add it to the High Score list:");
        alertDialog.setView(input);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Enter",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        String playerName = String.valueOf(input.getText());
                        if (!playerName.equals("")) {
                            game.setPlayerName(playerName);
                        }
                        dialog.dismiss();
                        postRequest();
                    }
                });
        alertDialog.show();
    }

    private void postRequest() {
        // String url = "https://127.0.0.1:5000/list";
        String url = "http://ec2-18-222-174-66.us-east-2.compute.amazonaws.com:8080/list";
        RequestQueue queue = Volley.newRequestQueue(this);
        HighScorePostRequest requestPostHighScores = new HighScorePostRequest(Request.Method.POST, url, this, this, game);
        queue.add(requestPostHighScores);

        // Create a TriviaRequest using the created url
        HighScoreGetRequest requestGetHighScores = new HighScoreGetRequest(this);
        requestGetHighScores.getScores(this, url);
    }

}
