package com.example.trivia;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class TriviaRequest implements Response.Listener<JSONObject>, Response.ErrorListener {

    private Context context;
    private Callback activity;

    public TriviaRequest(Context context) {
        this.context = context;
    }

    public interface Callback {
        void gotTrivia(ArrayList<Trivia> trivias);
        void gotTriviaError(String message);
    }

    // A method that gets the Trivia objects from a JSON request
    public void getTrivia(Callback activity, String url) {
        this.activity = activity;
        RequestQueue queue = Volley.newRequestQueue(context);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                url, null, this,
                this);
        queue.add(jsonObjectRequest);
    }

    // Handle a VolleyError response
    @Override
    public void onErrorResponse(VolleyError error) {
        activity.gotTriviaError(error.toString());
        Log.d("Volley error message.",error.toString());
    }

    // Handle a successful response
    @Override
    public void onResponse(JSONObject response) {

        // Handle the response code: '0' is success
        int responseCode = 0;
        try {
            responseCode = response.getInt("response_code");
        } catch (JSONException e) {
            e.printStackTrace();
            activity.gotTriviaError(e.getMessage());
        }

        // Checks if the ResponseCode is success before getting all trivia
        if (responseCode == 0) {
            JSONArray triviaItems = null;

            // Get the results from the response
            try {
                triviaItems = response.getJSONArray("results");
            } catch (JSONException e) {
                e.printStackTrace();
                activity.gotTriviaError(e.getMessage());
            }

            // Create an ArrayList to store all the Trivia objects
            ArrayList<Trivia> triviaItemsList = new ArrayList<>();

            // Iterate over the results of the response
            for (int i = 0; i < triviaItems.length(); i++) {
                try {

                    // Get all necessary variables from the results
                    JSONObject triviaItemJSON = triviaItems.getJSONObject(i);
                    String category = triviaItemJSON.getString("category");
                    String type = triviaItemJSON.getString("type");
                    String difficulty = triviaItemJSON.getString("difficulty");
                    String question = triviaItemJSON.getString("question");
                    String correctAnswer = triviaItemJSON.getString("correct_answer");
                    JSONArray incorrectAnswersJSON = triviaItemJSON.getJSONArray(
                            "incorrect_answers");

                    // Create a new ArrayList and add the incorrect answers
                    ArrayList<String> incorrectAnswers = new ArrayList<>();
                    for (int j = 0; j < incorrectAnswersJSON.length(); j++) {
                        incorrectAnswers.add(incorrectAnswersJSON.getString(j));
                    }

                    // Add everything to a Trivia object
                    triviaItemsList.add(new Trivia(category, type, difficulty, question,
                            correctAnswer, incorrectAnswers));

                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.d("onResponse error message.", e.getMessage());
                    activity.gotTriviaError(e.getMessage());
                }
            }

            // Return the ArrayList of Trivia objects to the gotTrivia function
            activity.gotTrivia(triviaItemsList);
        } else {
            activity.gotTriviaError("Unsuccesful Response!");
        }
    }
}
