package com.example.trivia;
/**
 * The HighScoreGetRequest class for the app.
 * This is the class that handles a JsonObjectRequest coming from the HighScoreActivity. This
 * request asks for a list of highscores and returns it to the HighScoreActivity if successful. If
 * not successful, error messages will be returned to the MainActivity.
 */

// List of imports.
import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class HighScoreGetRequest implements Response.Listener<JSONArray>, Response.ErrorListener {

    private Context context;
    private Callback activity;

    // Constructor sets context.
    public HighScoreGetRequest(Context context) {
        this.context = context;
    }

    // Callback method for the MainActivity.
    public interface Callback {
        void gotHighScores(ArrayList<Score> pastGameStates);
        void gotHighScoresError(String message);
    }

    // Method called to execute a GET request from the server, also sets activity.
    public void getScores(Callback activity, String url) {
        this.activity = activity;

        // Create a request queue and sets a request for highscores to the queue.
        RequestQueue queue = Volley.newRequestQueue(context);
        JsonArrayRequest jsonObjectRequest = new JsonArrayRequest(Request.Method.GET,
                url, null, this, this);
        queue.add(jsonObjectRequest);
    }

    // Method called when there is an unsuccessful response from the server.
    @Override
    public void onErrorResponse(VolleyError error) {

        // Returns error to the activity.
        activity.gotHighScoresError(error.toString());
    }

    // Method called when there is a successful response from the server.
    @Override
    public void onResponse(JSONArray response) {

        // Create an ArrayList to store all past Game objects.
        ArrayList<Score> highScoreItemList = new ArrayList<>();

        // Iterate over the results of the response.
        for (int i = 0; i < response.length(); i++) {
            try {

                // Get all necessary variables from the results.
                JSONObject highScoreJSON = response.getJSONObject(i);
                String difficulty = highScoreJSON.getString("difficulty");
                String score = highScoreJSON.getString("score");
                String questions = highScoreJSON.getString("questions");
                String category = highScoreJSON.getString("category");
                String type = highScoreJSON.getString("type");
                String player = highScoreJSON.getString("player");

                highScoreItemList.add(new Score(difficulty, score, questions, category, type,
                        player));
            } catch (JSONException e) {
                e.printStackTrace();

                // Returns error to the activity.
                activity.gotHighScoresError(e.getMessage());
            }
        }

        activity.gotHighScores(highScoreItemList);
    }
}
