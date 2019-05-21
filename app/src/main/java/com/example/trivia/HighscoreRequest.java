package com.example.trivia;

import android.content.Context;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONObject;

import java.util.ArrayList;

public class HighscoreRequest implements Response.Listener<JSONObject>, Response.ErrorListener {

    private Context context;
    private Callback activity;

    public HighscoreRequest(Context context) {
        this.context = context;
    }

    public void putScores() {

    }

    public void getScores(Callback activity) {
        this.activity = activity;
    }

    public interface Callback {
        void gotScores(ArrayList<Trivia> trivias);
        void gotScoresError(String message);
    }

    @Override
    public void onErrorResponse(VolleyError error) {

    }

    @Override
    public void onResponse(JSONObject response) {

    }
}
