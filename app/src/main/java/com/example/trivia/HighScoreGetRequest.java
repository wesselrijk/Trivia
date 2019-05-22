package com.example.trivia;

import android.content.Context;
import android.util.Log;

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

    public HighScoreGetRequest(Context context) {
        this.context = context;
    }

    public interface Callback {
        void gotHighScores(ArrayList<Score> pastGameStates);
        void gotHighScoresError(String message);
    }

    public void getScores(Callback activity, String url) {
        this.activity = activity;
        RequestQueue queue = Volley.newRequestQueue(context);
        JsonArrayRequest jsonObjectRequest = new JsonArrayRequest(Request.Method.GET,
                url, null, this, this);
        queue.add(jsonObjectRequest);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        activity.gotHighScoresError(error.toString());
        Log.d("Volley error message.",error.toString());
    }

    @Override
    public void onResponse(JSONArray response) {

//        JSONObject value = null;
//        try {
//            value = response.getJSONObject(0);
//        } catch (JSONException e) {
//            e.printStackTrace();
//            activity.gotHighScoresError(e.getMessage());
//        }
//
//        JSONArray highScoreItems = null;
//
//        try {
//            highScoreItems = value.getJSONArray("label");
//        } catch (JSONException e) {
//            e.printStackTrace();
//            activity.gotHighScoresError(e.getMessage());
//        }
//
//        try {
//            Log.d("tag", value.toString(4));
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
        // Create an ArrayList to store all past Game objects
        ArrayList<Score> highScoreItemList = new ArrayList<>();

        // Iterate over the results of the response
        for (int i = 0; i < response.length(); i++) {
            try {

                // Get all necessary variables from the results
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
                Log.d("onResponse error message.", e.getMessage());
                activity.gotHighScoresError(e.getMessage());
            }
        }
        activity.gotHighScores(highScoreItemList);
    }
}
