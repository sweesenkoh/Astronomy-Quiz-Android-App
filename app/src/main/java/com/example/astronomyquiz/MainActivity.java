package com.example.astronomyquiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupStartQuizButton();
    }


    private void setupStartQuizButton(){
        CardView startButton = findViewById(R.id.start_quiz_button_cardView);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(),ChapterSelectionPopActivity.class);
                startActivity(intent);

            }
        });
    }
}
