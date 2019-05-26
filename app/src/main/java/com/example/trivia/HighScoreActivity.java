package com.example.trivia;
/**
 * The HighScoreActivity activity for the app.
 * In this activity the user will receive a prompt to fill in their highscore, once this has been
 * done and the user clicks the enterButton, the highscore list will be displayed.
 * The activity starts by showing a prompt that displays the score of the user and an input to
 * fill in their name. When the user clicks on the enterButton, the score will be registered to an
 * online database by implementing the HighScorePostRequest. The get method for this request is
 * also handled in this activity, with correct response to a successful post and an unsuccessful
 * post.
 */

// List of imports.
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
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

public class HighScoreActivity extends AppCompatActivity implements HighScoreGetRequest.Callback,
        Response.ErrorListener, Response.Listener<String> {

    private Game game;
    private boolean highScoreFilled = false;
    private ListView listView;
    private ArrayList<Score> scoreList;

    /*
    * In oncreate the layout and listView variable are set. The game is set from the intent.
    * Next it checks if there is a savedinstance. If not, call for a prompt. Resets the saved
    * instance otherwise.
    * */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scores);

        listView = findViewById(R.id.listView);

        Intent intent = getIntent();
        game = (Game) intent.getSerializableExtra("game_finished");

        if (savedInstanceState != null) {
            highScoreFilled = savedInstanceState.getBoolean("highscore_filled");
            scoreList = (ArrayList<Score>) savedInstanceState.getSerializable(
                    "highscore_list");
        }

        // Checks if the highscoreprompt has been filled by the user.
        if (highScoreFilled) {
            HighScoreAdapter adapter = new HighScoreAdapter(this, R.layout.scores_row,
                    scoreList);
            listView.setAdapter(adapter);
        } else {
            highScorePrompt();
        }
    }

    // Method that is called when the HighScoreRequest was done successfully.
    @Override
    public void gotHighScores(ArrayList<Score> highScoreItemList) {
        scoreList = highScoreItemList;
        HighScoreAdapter adapter = new HighScoreAdapter(this, R.layout.scores_row,
                highScoreItemList);
        listView.setAdapter(adapter);
    }

    // Method that is called when the HighScoreRequest encountered an error.
    @Override
    public void gotHighScoresError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    // Method called when there is an unsuccessful response from the server.
    @Override
    public void onErrorResponse(VolleyError error) {

        // Returns error to the activity.
        HighScoreActivity.this.gotHighScoresError(error.toString());
    }

    // Method called when there is a successful response from the server.
    @Override
    public void onResponse(String response) {
    }


    /* Create a prompt for the player to input their name, using code from:
     * https://stackoverflow.com/questions/18799216/how-to-make-a-edittext-box-in-a-dialog
    * */
    private void highScorePrompt() {
        final EditText input = new EditText(HighScoreActivity.this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);
        final AlertDialog alertDialog = new AlertDialog.Builder(HighScoreActivity.this)
                .create();
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
                        highScoreFilled = true;
                        postRequest();
                    }
                });
        alertDialog.show();
    }

    // Method called to execute a POST request.
    private void postRequest() {

        /* The url is generated from executing a flask in python, from rester.py, source:
         * https://github.com/stgm/rester
        */
        String url = "http://ec2-3-17-162-147.us-east-2.compute.amazonaws.com:8080/list";

        // Create a post request to the database.
        RequestQueue queue = Volley.newRequestQueue(this);
        HighScorePostRequest requestPostHighScores = new HighScorePostRequest(Request.Method.POST,
                url, this, this, game);
        queue.add(requestPostHighScores);

        // Create a TriviaRequest using the created url.
        HighScoreGetRequest requestGetHighScores = new HighScoreGetRequest(this);
        requestGetHighScores.getScores(this, url);
    }

    // Saves the current state.
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("highscore_filled", highScoreFilled);
        outState.putSerializable("highscore_list", scoreList);
    }
}
