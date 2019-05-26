package com.example.trivia;
/**
 * <h1>Trivia</h1>
 * The MainActivity for the Trivia app.
 * The Trivia app is an app where the user can play a game that asks the user for some settings
 * then collects a list of trivia questions and answers from the Open Trivia Database:
 * https://opentdb.com/api_config.php .
 * In this activity, spinners are used to ask the user for input. When the user clicks on the
 * playButton, the GameActivity will start, trivia data will be collected from the server and the
 * game will play. After the game is over, the user can post his score to the Highscore server and
 * the highscore list will be displayed.
 */

// List of imports.
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Game game;

    // In the onCreate, a new game will be set, as well as the spinners used in the activity_main.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        game  = new Game();

        // Spinnerarray for the number of questions that can be picked by the user.
        List<Integer> spinnerArray = new ArrayList<>();
        for (int i = 1; i <= 50; i++) {
            spinnerArray.add(i);
        }

        /* Set all spinners using:
         * https://developer.android.com/guide/topics/ui/controls/spinner#java
         * the first spinner is for the number of questions*/
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

        // Configure a spinner for the category.
        Spinner spinnerCategory = findViewById(R.id.spinnerCategory);
        ArrayAdapter<CharSequence> adapterCategories = ArrayAdapter.createFromResource(this,
                R.array.category_array, android.R.layout.simple_spinner_item);
        adapterCategories.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategory.setAdapter(adapterCategories);
        spinnerCategory.setOnItemSelectedListener(this);

        // Configure a spinner for the difficulty.
        Spinner spinnerDifficulty = findViewById(R.id.spinnerDifficulty);
        ArrayAdapter<CharSequence> adapterDifficulty = ArrayAdapter.createFromResource(this,
                R.array.difficulty_array, android.R.layout.simple_spinner_item);
        adapterDifficulty.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDifficulty.setAdapter(adapterDifficulty);
        spinnerDifficulty.setOnItemSelectedListener(this);

        // Configure a spinner for the game type.
        Spinner spinnerType = findViewById(R.id.spinnerType);
        ArrayAdapter<CharSequence> adapterType = ArrayAdapter.createFromResource(this,
                R.array.type_array, android.R.layout.simple_spinner_item);
        adapterType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerType.setAdapter(adapterType);
        spinnerType.setOnItemSelectedListener(this);
    }

    // Method activated when a spinner setting is selected, from same source as mentioned above.
    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {

        // Retrieve selected item from a spinner.
        switch (parent.getId()) {
            case R.id.spinnerQuestionsNumber:
                game.setQuestionsNumber(pos + 1);
                break;
            case R.id.spinnerCategory:
                game.setCategory(pos + 8);
                break;
            case R.id.spinnerDifficulty:
                String[] difficulties = {null, "easy", "medium", "hard"};
                game.setDifficulty(difficulties[pos]);
                break;
            case R.id.spinnerType:
                String[] types = {null, "multiple", "boolean"};
                game.setType(types[pos]);
                break;
        }
    }

    // Method that activates if nothing is selected by the spinner.
    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        Toast.makeText(this,"Nothing Selected", Toast.LENGTH_SHORT).show();

    }

    // Method that starts the GameActivity intent when the playButton is clicked.
    public void playClicked(View view) {
        Intent intent = new Intent(MainActivity.this, GameActivity.class);
        intent.putExtra("game_started", game);
        startActivity(intent);
    }
}
