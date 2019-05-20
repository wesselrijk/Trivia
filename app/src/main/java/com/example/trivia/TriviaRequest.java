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
        void gotTrivia(ArrayList<String> trivias);
        void gotTriviaError(String message);
    }

    public void getTrivia(Callback activity, String url) {
        this.activity = activity;
        RequestQueue queue = Volley.newRequestQueue(context);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                url, null, this,
                this);
        queue.add(jsonObjectRequest);
    }


    @Override
    public void onErrorResponse(VolleyError error) {
        activity.gotTriviaError(error.toString());
        Log.d("Volley error message.",error.toString());
    }

    @Override
    public void onResponse(JSONObject response) {
        int responseCode = 0;
        try {
            responseCode = response.getInt("response_code");
        } catch (JSONException e) {
            e.printStackTrace();
            activity.gotTriviaError(e.getMessage());
        }

        // Checks if the responsecode is succes before getting all trivia
        if (responseCode == 0) {
            JSONArray triviaItems = null;
            try {
                triviaItems = response.getJSONArray("results");
            } catch (JSONException e) {
                e.printStackTrace();
                activity.gotTriviaError(e.getMessage());
            }

            ArrayList<String> triviaItemsList = new ArrayList<>();
            activity.gotTrivia(triviaItemsList);
        } else {
            activity.gotTriviaError("Unsuccesful Response!");
        }
    }
}
