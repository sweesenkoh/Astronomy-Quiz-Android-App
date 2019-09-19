package com.example.astronomyquiz;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;

import java.util.ArrayList;
import java.util.List;

public class ChapterSelectionPopActivity extends Activity implements AdapterView.OnItemSelectedListener {


    private AstroData astroData;
    private List<String> chaptersString = new ArrayList<String>();
    private Integer selectedChapterIndex = 0;
    private boolean shouldShuffle = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter_selection_pop);

        astroData = new AstroData().loadAstronomyData(this);
        for (int x = 0 ; x < astroData.data.size() ; x++){
            chaptersString.add((x+1) + ". " + astroData.data.get(x).ChapterName);
        }

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*0.8),(int)(height*0.65));

        setupChapterSelectionSpinner();
        setupNextButton();
        setupRandomShuffleCheckbox();
    }

    @Override
    protected void onStart() {
        super.onStart();
//        setAnimationViewInvisible();
    }

    private void setupChapterSelectionSpinner(){
        Spinner spinner = findViewById(R.id.chapter_selection_spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,chaptersString);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_row);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
         ((TextView) adapterView.getChildAt(0)).setTextColor(getResources().getColor(R.color.white));;
         selectedChapterIndex = i;
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    private void setupNextButton(){
        Button button = findViewById(R.id.next_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                LottieAnimationView animationView = findViewById(R.id.animation_view_loading_lottie);
//                animationView.setVisibility(View.VISIBLE);

                Intent intent = new Intent(view.getContext(),QuizActivity.class);
                Integer chapterValue = selectedChapterIndex;
                intent.putExtra("ChapterValue",chapterValue);
                intent.putExtra("ShouldShuffle",shouldShuffle);
                startActivity(intent);
            }
        });
    }

    private void setupRandomShuffleCheckbox(){
        CheckBox mycheckbox = findViewById(R.id.checkbox_random_mode);
        mycheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                shouldShuffle = b;
            }
        });
    }


}
