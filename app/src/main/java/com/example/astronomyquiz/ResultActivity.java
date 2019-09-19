package com.example.astronomyquiz;

import android.content.Intent;
import android.support.v4.content.IntentCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class ResultActivity extends AppCompatActivity {

    private RecyclerView resultRecyclerView;
    private RecyclerView.Adapter resultRecyclerAdapter;
    private RecyclerView.LayoutManager resultRecyclerLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        buildingRecyclerView();
        updateTextViewQuestionCorrectCount();
        setupDoneQuizSaveResultButton();
    }

    private void buildingRecyclerView(){
        resultRecyclerView = findViewById(R.id.result_grid_recycler_view);
        resultRecyclerView.setHasFixedSize(true);
        resultRecyclerLayoutManager = new GridLayoutManager(this,4);
        resultRecyclerView.setLayoutManager(resultRecyclerLayoutManager);

        resultRecyclerAdapter = new ResultRecyclerAdapter();
        resultRecyclerView.setAdapter(resultRecyclerAdapter);
    }

    private void updateTextViewQuestionCorrectCount(){

        Integer correctCount = 0;
        Integer wrongCount = 0;
        Integer unattemptedCount = 0;

        for (int x = 0 ; x < UserChoices.choices.size() ; x++){
            if (UserChoices.choices.get(x).answerCorrectness == 0){
                wrongCount++;
            }
            else if (UserChoices.choices.get(x).answerCorrectness == 1){
                correctCount++;
            }
            else{
                unattemptedCount++;
            }
        }

        TextView correctCountTextView = findViewById(R.id.correct_count_textview);
        correctCountTextView.setText(correctCount.toString());

        TextView wrongCountTextView = findViewById(R.id.wrong_count_textview);
        wrongCountTextView.setText(wrongCount.toString());

        TextView unattemptedCountTextView = findViewById(R.id.unattempted_count_textview);
        unattemptedCountTextView.setText(unattemptedCount.toString());
    }

    private void setupDoneQuizSaveResultButton(){
        ImageButton saveButton = findViewById(R.id.save_result_image_button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intents = new Intent(view.getContext(), MainActivity.class);
                intents.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                        | Intent.FLAG_ACTIVITY_CLEAR_TOP
                        | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intents);
                finish();
                Toast.makeText(view.getContext(), "Your results are saved!",
                        Toast.LENGTH_LONG).show();
            }
        });
    }
}
