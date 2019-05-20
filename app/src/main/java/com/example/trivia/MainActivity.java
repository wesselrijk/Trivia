package com.example.trivia;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List<Integer> spinnerArray = new ArrayList<>();
        for (int i = 1; i <= 50; i++) {
            spinnerArray.add(i);
        }

        /* set first spinner using:
        * https://developer.android.com/guide/topics/ui/controls/spinner#java */
        Spinner spinnerQuestionNumber = findViewById(R.id.spinnerQuestionsNumber);
        ArrayAdapter<Integer> adapterNumbers = new ArrayAdapter<Integer>(
                this,
                android.R.layout.simple_spinner_item,
                spinnerArray
        );
        adapterNumbers.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerQuestionNumber.setAdapter(adapterNumbers);


        Spinner spinnerCategory = findViewById(R.id.spinnerCategory);
        ArrayAdapter<CharSequence> adapterCategories = ArrayAdapter.createFromResource(this,
                R.array.category_array, android.R.layout.simple_spinner_item);
        adapterCategories.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategory.setAdapter(adapterCategories);


        Spinner spinnerDifficulty = findViewById(R.id.spinnerDifficulty);
        ArrayAdapter<CharSequence> adapterDifficulty = ArrayAdapter.createFromResource(this,
                R.array.difficulty_array, android.R.layout.simple_spinner_item);
        adapterDifficulty.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDifficulty.setAdapter(adapterDifficulty);


        Spinner spinnerType = findViewById(R.id.spinnerType);
        ArrayAdapter<CharSequence> adapterType = ArrayAdapter.createFromResource(this,
                R.array.type_array, android.R.layout.simple_spinner_item);
        adapterType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerType.setAdapter(adapterType);
    }


    public void playClicked(View view) {

    }
}
