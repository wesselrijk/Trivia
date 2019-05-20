package com.example.trivia;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Game game;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        game  = new Game();

        List<Integer> spinnerArray = new ArrayList<>();
        for (int i = 1; i <= 50; i++) {
            spinnerArray.add(i);
        }

        /* set all spinners using:
        * https://developer.android.com/guide/topics/ui/controls/spinner#java */
        Spinner spinnerQuestionNumber = findViewById(R.id.spinnerQuestionsNumber);
        ArrayAdapter<Integer> adapterNumbers = new ArrayAdapter<Integer>(
                this,
                android.R.layout.simple_spinner_item,
                spinnerArray
        );
        adapterNumbers.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerQuestionNumber.setAdapter(adapterNumbers);
        spinnerQuestionNumber.setOnItemSelectedListener(this);
        spinnerQuestionNumber.setSelection(9);


        Spinner spinnerCategory = findViewById(R.id.spinnerCategory);
        ArrayAdapter<CharSequence> adapterCategories = ArrayAdapter.createFromResource(this,
                R.array.category_array, android.R.layout.simple_spinner_item);
        adapterCategories.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategory.setAdapter(adapterCategories);
        spinnerCategory.setOnItemSelectedListener(this);


        Spinner spinnerDifficulty = findViewById(R.id.spinnerDifficulty);
        ArrayAdapter<CharSequence> adapterDifficulty = ArrayAdapter.createFromResource(this,
                R.array.difficulty_array, android.R.layout.simple_spinner_item);
        adapterDifficulty.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDifficulty.setAdapter(adapterDifficulty);
        spinnerDifficulty.setOnItemSelectedListener(this);


        Spinner spinnerType = findViewById(R.id.spinnerType);
        ArrayAdapter<CharSequence> adapterType = ArrayAdapter.createFromResource(this,
                R.array.type_array, android.R.layout.simple_spinner_item);
        adapterType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerType.setAdapter(adapterType);
        spinnerType.setOnItemSelectedListener(this);
    }

    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        // An item was selected. You can retrieve the selected item using
        switch (parent.getId()) {
            case R.id.spinnerQuestionsNumber:
                game.setQuestionsNumber(pos - 1);
                Log.d("selected", String.valueOf(game.getQuestionsNumber()));
            case R.id.spinnerCategory:
                game.setCategory(pos + 8);
                Log.d("selected", String.valueOf(game.getCategory()));
            case R.id.spinnerDifficulty:
                String setDifficulty = parent.getItemAtPosition(pos).toString();
                if (setDifficulty == "Easy") {
                    game.setDifficulty("easy");
                } else if (setDifficulty == "Medium") {
                    game.setDifficulty("medium");
                } else if (setDifficulty == "Hard") {
                    game.setDifficulty("hard");
                }
                Log.d("selected", game.getDifficulty());
            case R.id.spinnerType:
                String setType = parent.getItemAtPosition(pos).toString();
                if (setType == "Multiple Choice") {
                    game.setType("multiple");
                } else if (setType == "True / False") {
                    game.setType("boolean");
                }
                Log.d("selected", game.getType());
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    public void playClicked(View view) {

    }
}
