package com.example.trivia;
/**
 * The GameActivity activity for the app.
 * In this activity the user will have to click on a button that contains the correct answer.
 * The activity starts by collecting trivia data from the online database if there is no saved
 * instance state. The trivia data will be according to the Game object that has been made with the
 * settings specified in the MainActivity. Questions will then be displayed on screen and buttons
 * will display the answers. The buttons are set randomly in order to make sure that the correct
 * answer is not set to the same button constantly. After a button is clicked, the activity will
 * check if it was the correct answer and increment the score in the Game class, and load the next
 * question and answers. If the question was the final question, the game will be over and the
 * HighScoreActivity will start.
 */

// List of imports.
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

public class GameActivity extends AppCompatActivity implements TriviaRequest.Callback {

    private Game game;
    private String url;
    private TextView category;
    private TextView typeDifficulty;
    private TextView question;
    private TextView outOf;
    private Button button1;
    private Button button2;
    private Button button3;
    private Button button4;
    private ArrayList<Trivia> triviaItemsList;
    private String correctAnswer;
    private int questionCounter = 0;

    /* The onCreate for the GameActivity checks if there is a saved game state and will set the
     * played game accordingly.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        // Get all views from the layout.
        category = findViewById(R.id.difficultyView);
        typeDifficulty = findViewById(R.id.typeDifficultyView);
        question = findViewById(R.id.questionView);
        outOf = findViewById(R.id.outOfView);
        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);
        button4 = findViewById(R.id.button4);

        // Check if there is a game saved in the savedInstanceState.
        if (savedInstanceState != null) {

            // Set game state to saved variables.
            game = (Game) savedInstanceState.getSerializable("game");
            triviaItemsList = (ArrayList<Trivia>) savedInstanceState.getSerializable("trivias");
            questionCounter = savedInstanceState.getInt("question_counter") - 1;
            button1.setVisibility(savedInstanceState.getInt("button1_visibility"));
            button2.setVisibility(savedInstanceState.getInt("button2_visibility"));

            nextTrivia();
        } else {

            // Get the game configuration from the intent
            Intent intent = getIntent();
            game = (Game) intent.getSerializableExtra("game_started");

            // Create the appropriate url using the game configuration
            url = urlBuilder();

            // Create a TriviaRequest using the created url
            TriviaRequest requestTrivia = new TriviaRequest(this);
            requestTrivia.getTrivia(this, url);
        }

        // Set text of category according to data in R.array.category_array.
        if (game.getCategory() == 8) {
            category.setText("Any Category");
        } else {
            String[] categories = getResources().getStringArray(R.array.category_array);
            category.setText(categories[game.getCategory() - 8]);
        }

        // Set text of difficulty and type accordingly.
        String text = "";
        if (game.getDifficulty() == null){
            text += "Any Difficulty - ";
        } else {
            text += capitalizeString(game.getDifficulty()) + " Difficulty - ";
        }
        if (game.getType() == null) {
            text += "Any Type";
        } else {
            text += capitalizeString(game.getType());
        }

        // Set the text to be displayed.
        typeDifficulty.setText(text);
    }


    // Method that is called when the TriviaRequest was done successfully.
    @Override
    public void gotTrivia(ArrayList<Trivia> triviaItemsList) {

        // Sets trivialist and makes the top buttons visible.
        this.triviaItemsList = triviaItemsList;
        button1.setVisibility(View.VISIBLE);
        button2.setVisibility(View.VISIBLE);

        nextTrivia();
    }

    // Method that is called when the TriviaRequest encountered an error.
    @Override
    public void gotTriviaError(String message) {

        // Display error in a message on-screen.
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }


    // Method for getting the next trivia.
    private void nextTrivia() {

        // Get the next trivia object from the list.
        Trivia trivia = triviaItemsList.get(questionCounter);

        // Set views and variables.
        outOf.setText(questionCounter + 1 + " / " + triviaItemsList.size());
        question.setText(android.text.Html.fromHtml(trivia.getQuestion()));
        int answersNumber = trivia.getIncorrectAnswers().size() + 1;
        correctAnswer = trivia.getCorrectAnswer();

        /* Shuffle buttons randomly, source:
         * https://www.geeksforgeeks.org/collections-shuffle-java-examples/
         */
        ArrayList<Button> buttonList = new ArrayList<>();
        buttonList.add(button1);
        buttonList.add(button2);
        if (answersNumber == 2) {
            button3.setVisibility(View.INVISIBLE);
            button4.setVisibility(View.INVISIBLE);
        } else {
            button3.setVisibility(View.VISIBLE);
            button4.setVisibility(View.VISIBLE);
            buttonList.add(button3);
            buttonList.add(button4);
        }
        Collections.shuffle(buttonList);

        /* Reform answers from HTML and set them as text to buttons, set normal answers as tag.
        * Set the correct answer to the first button in the button list, set incorrect answers
        * to the other buttons.
        * */
        buttonList.get(0).setText(android.text.Html.fromHtml(trivia.getCorrectAnswer()));
        buttonList.get(0).setTag(trivia.getCorrectAnswer());
        buttonList.get(1).setText(android.text.Html.fromHtml(trivia.getIncorrectAnswers().get(0)));
        buttonList.get(1).setTag(trivia.getIncorrectAnswers().get(0));
        if (answersNumber == 4) {
            buttonList.get(2).setText(android.text.Html.fromHtml(
                    trivia.getIncorrectAnswers().get(1)));
            buttonList.get(2).setTag(trivia.getIncorrectAnswers().get(1));
            buttonList.get(3).setText(android.text.Html.fromHtml(
                    trivia.getIncorrectAnswers().get(2)));
            buttonList.get(3).setTag(trivia.getIncorrectAnswers().get(2));
        }

        // Add to the question counter.
        questionCounter += 1;
    }

    // Method that builds the url String for the JSONRequest.
    public String urlBuilder() {
        StringBuilder sb = new StringBuilder();
        sb.append("https://opentdb.com/api.php?amount="); //start of the url
        sb.append(game.getQuestionsNumber()); // number of questions added

        // Adds correct string values for category, difficulty and type.
        if (game.getCategory() != 8) {
            sb.append("&category="+game.getCategory());
        }
        if (game.getDifficulty() != null) {
            sb.append("&difficullty="+game.getDifficulty());
        }
        if (game.getType() != null) {
            sb.append("&type="+game.getType());
        }

        return sb.toString();
    }

    // Method that activates when an answer button has been clicked.
    public void buttonClicked(View view) {
        Button buttonClicked = (Button) view;

        // Count to the score if the clicked button has the correct answer.
        if (buttonClicked.getTag().equals(correctAnswer)) {
            game.setScore(game.getScore()+1);
        }

        checkRemaining();
    }

    // Checks the remaining number of questions, goes to HighScoreActivity if game is over.
    private void checkRemaining() {
        if (questionCounter == game.getQuestionsNumber()) {
            Intent intent = new Intent(GameActivity.this, HighScoreActivity.class);
            intent.putExtra("game_finished", game);
            startActivity(intent);
            finish();
        } else {
            nextTrivia();
        }
    }

    // Method for capitalizing string's first letter.
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

    // Saves current game state, trivia list and button visibility.
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("game", game);
        outState.putSerializable("trivias", triviaItemsList);
        outState.putSerializable("question_counter", questionCounter);
        outState.putSerializable("button1_visibility", button1.getVisibility());
        outState.putSerializable("button2_visibility", button2.getVisibility());
    }
}
