package com.example.trivia;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class HighScoreAdapter extends ArrayAdapter<Score> {

    Context context;
    ArrayList<Score> highScoreItemList;

    public HighScoreAdapter(Context context, int layout, ArrayList<Score> objects) {
        super(context, layout, objects);
        this.context = context;
        this.highScoreItemList = objects;
    }

    // an override method used to fill in the items of the GridView of the activity_main layout
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // if the convertView is empty, the convertView will be set to the grid_item layout
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.scores_row, parent,
                    false);
        }

        TextView playerView = convertView.findViewById(R.id.playerView);
        TextView infoView = convertView.findViewById(R.id.infoView);
        TextView scoreView = convertView.findViewById(R.id.scoreView);

        Score current = highScoreItemList.get(position);

        playerView.setText(current.getPlayerName());
        scoreView.setText(current.getScore() + " / " + current.getQuestions());

        // Generate a string for the infotext
        String infoText = capitalizeString(current.getDifficulty()) + " Difficulty - ";

        String[] categories = context.getResources().getStringArray(R.array.category_array);
        infoText += categories[Integer.parseInt(current.getCategory()) - 8];
        switch(current.getType()) {
            case "any":
                infoText += " - Any Type";
                break;
            case "multiple":
                infoText += " - Multiple Choice";
                break;
            case "boolean":
                infoText += " - True / False";
                break;
        }

        infoView.setText(infoText);

        return convertView;
    }

    // Method for capitalizing string's first letter
    private String capitalizeString(String string) {
        String[] names = string.split("\\s+");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < names.length; i++) {
            if (i != 0) {
                sb.append(' ');
            }
            sb.append(Character.toUpperCase(names[i].charAt(0)));
            sb.append(names[i].substring(1).toLowerCase());
        }
        return sb.toString();
    }
}
