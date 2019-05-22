package com.example.trivia;
/*
* Layout is completely taken from https://apps.mprog.nl/android-reference/volley,
* adjusted slightly for parameters used in this app. */

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class HighScorePostRequest extends StringRequest {


    private Game game;

    public HighScorePostRequest(int method, String url, Response.Listener<String> listener, Response.ErrorListener errorListener, Game game) {
        super(method, url, listener, errorListener);
        this.game = game;
    }

    // Method to supply parameters to the request
    @Override
    protected Map<String, String> getParams() {

        Map<String, String> params = new HashMap<>();
        params.put("questions", String.valueOf(game.getQuestionsNumber()));
        params.put("category", String.valueOf(game.getCategory()));
        if (game.getDifficulty() == null){
            params.put("difficulty", "any");
        } else {
            params.put("difficulty", game.getDifficulty());
        }
        if (game.getType() == null) {
            params.put("type", "any");
        } else {
            params.put("type", game.getType());
        }
        params.put("score", String.valueOf(game.getScore()));
        params.put("player", game.getPlayerName());

        return params;
    }
}
