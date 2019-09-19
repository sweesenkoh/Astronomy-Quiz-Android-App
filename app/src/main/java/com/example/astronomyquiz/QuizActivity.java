package com.example.astronomyquiz;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class QuizActivity extends AppCompatActivity {

    private TabLayout tablayout;
    private AppBarLayout appBarLayout;
    private ViewPager viewPager;
    private AstroData astroData;
    private List<Integer> shuffleList;
    private Integer currentChapterIndex = 10;
    private boolean shouldShuffle = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        astroData = new AstroData().loadAstronomyData(this);
        currentChapterIndex = getIntent().getIntExtra("ChapterValue",0);
        shouldShuffle = getIntent().getBooleanExtra("ShouldShuffle",false);

        setupShuffleList();
        populateUserChoicesDataWithNull();
        setupViewPagerAdapter();
        setupProgressIndicatorView();
        refreshProgressIndicatorViewState();
        setupQuizDoneOnClick();


    }

    @Override
    public void onBackPressed() {


        AlertDialog myDiaglog = new AlertDialog.Builder(this)
                .setMessage("Are you sure you want to exit? Your progress will be lost")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        QuizActivity.super.onBackPressed();
                    }
                })
                .setNegativeButton("No", null)
                .create();

        myDiaglog.show();
        myDiaglog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(getResources().getColor(R.color.black));
        myDiaglog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.red));


    }

    private void setupQuizDoneOnClick(){
        ImageButton quizdonebutton = findViewById(R.id.quiz_done_button);
        quizdonebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (new UserChoices().userDoneAllQuestions()){
                    Intent myIntent = new Intent(view.getContext(),ResultActivity.class);
                    startActivity(myIntent);
                }
                else{
                    displayPromptToMakeSureUserEnd();
                }
            }
        });
    }

    private void displayPromptToMakeSureUserEnd(){

        AlertDialog myDiaglog = new AlertDialog.Builder(this)
                .setMessage("You haven't finished all the questions. Are you sure you want to proceed to end the quiz?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent myIntent = new Intent(getApplicationContext(),ResultActivity.class);
                        startActivity(myIntent);
                    }
                })
                .setNegativeButton("No", null)
                .create();

        myDiaglog.show();
        myDiaglog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(getResources().getColor(R.color.black));
        myDiaglog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.black));
    }

    private void setupShuffleList(){
        shuffleList = new ArrayList<Integer>();

        for (int x = 0 ; x < astroData.data.get(currentChapterIndex).Questions.size() ; x++){
            shuffleList.add(x);
        }

        if (shouldShuffle){
            Collections.shuffle(shuffleList);
        }
    }


    private void populateUserChoicesDataWithNull(){
        UserChoices.choices.clear();
        for (int x  = 0 ; x < astroData.data.get(currentChapterIndex).Questions.size() ; x++){
            UserChoices.choices.add(new Choice(-1,-1));
        }
    }

    private void setupViewPagerAdapter(){

        tablayout = findViewById(R.id.tab_layout);
        appBarLayout = findViewById(R.id.app_bar);
        viewPager = findViewById(R.id.view_pager);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());


        for (int x = 0 ; x < astroData.data.get(currentChapterIndex).Questions.size() ; x++){

            QuestionFragment questionFragment = new QuestionFragment(astroData.data.get(currentChapterIndex).Questions.get(shuffleList.get(x)),x);
            questionFragment.setOnButtonClickedListener(new QuestionFragment.onButtonClickedListener() {
                @Override
                public void buttonClicked(int position) {
                    refreshProgressIndicatorViewState();
                }
            });
            adapter.addFragment(questionFragment,"Qn " + (x+1));
        }


        viewPager.setAdapter(adapter);
        tablayout.setupWithViewPager(viewPager);

        TextView titleTextView = findViewById(R.id.Textview_navigation_bar_title);
        titleTextView.setText("Chapter " + (currentChapterIndex + 1));
        //titleTextView.setText("Chapter " + (currentChapterIndex + 1) + ": " + astroData.data.get(currentChapterIndex).ChapterName);
    }


    private void setupProgressIndicatorView(){

        LinearLayout questionProgressLayout1 = findViewById(R.id.navigation_linear_layout_question_progress_1);
        LinearLayout questionProgressLayout2 = findViewById(R.id.navigation_linear_layout_question_progress_2);
        LinearLayout questionProgressLayout3 = findViewById(R.id.navigation_linear_layout_question_progress_3);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;

        for (int x = 0 ; x < astroData.data.get(currentChapterIndex).Questions.size() ; x++){

            View newButton  = new Button(this);
            newButton.setId(x);

            if (x >= 40){
                questionProgressLayout3.addView(newButton);
            }

            else if (x >= 20){
                questionProgressLayout2.addView(newButton);
            }

            else{
                questionProgressLayout1.addView(newButton);
            }

            newButton = findViewById(x);
            newButton.getLayoutParams().width = width / 20 - 2;
            newButton.getLayoutParams().height = width / 20 - 2;
            newButton.setBackgroundTintList(this.getResources().getColorStateList(R.color.gray));
            newButton.requestLayout();
        }

        questionProgressLayout1.getLayoutParams().height = (width / 20);
        questionProgressLayout2.getLayoutParams().height = (width / 20);
        questionProgressLayout3.getLayoutParams().height = (width / 20);
    }

    private void refreshProgressIndicatorViewState(){

        Button button = new Button(this);

        for (int x = 0 ; x < UserChoices.choices.size() ; x++){
            button = findViewById(x);
            Integer correctness = UserChoices.choices.get(x).answerCorrectness;

            if (correctness == 1){
                button.setBackgroundTintList(this.getResources().getColorStateList(R.color.green));
            }
            else if (correctness == 0){
                button.setBackgroundTintList(this.getResources().getColorStateList(R.color.red));
            }
            else{
                button.setBackgroundTintList(this.getResources().getColorStateList(R.color.gray));
            }
        }

    }



}

