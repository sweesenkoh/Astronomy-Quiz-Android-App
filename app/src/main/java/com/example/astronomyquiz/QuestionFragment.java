package com.example.astronomyquiz;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;


public class QuestionFragment extends Fragment {


    private AstroData.Question question;
    private Integer questionIndex;
    private Integer correctAnswer;


    public QuestionFragment(AstroData.Question question,Integer questionIndex) {
        this.question = question;
        this.questionIndex = questionIndex;
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_question, container, false);

        correctAnswer = convertStringAnswerToInt(question.Answer);

        setupQuestionAndOptionTextView(rootView);
        configureButtonOnclickListener(rootView);
        setButtonColorBasedOnPreviousAnswer(rootView);

        return rootView;
    }


    private onButtonClickedListener mlistener;

    public interface onButtonClickedListener{
        void buttonClicked(int position);
    }

    public void setOnButtonClickedListener(onButtonClickedListener listener){
        mlistener = listener;
    }






    private void setupQuestionAndOptionTextView(View rootView){
        TextView question_textView = rootView.findViewById(R.id.textView_question);
        TextView option_textView = rootView.findViewById(R.id.options_textView);
        question_textView.setText(question.QuestionStatement);

        String optionString = "";

        for (int x = 0; x < question.Options.size(); x++) {
            optionString += question.Options.get(x) + "\n\n";
        }
        option_textView.setText(optionString);
        option_textView.setMovementMethod(new ScrollingMovementMethod());
    }


    private void configureButtonOnclickListener(final View rootView){

        final Button optionAButton = rootView.findViewById(R.id.button_optionA);
        Button optionBButton = rootView.findViewById(R.id.button_optionB);
        Button optionCButton = rootView.findViewById(R.id.button_optionC);
        Button optionDButton = rootView.findViewById(R.id.button_optionD);
        Button optionEButton = rootView.findViewById(R.id.button_optionE);

        List<Button> allButtons = new ArrayList<Button>();
        allButtons.add(optionAButton);
        allButtons.add(optionBButton);
        allButtons.add(optionCButton);
        allButtons.add(optionDButton);
        allButtons.add(optionEButton);



        optionAButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addToUserChoices(0);

            }
        });

        optionBButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addToUserChoices(1);
            }
        });

        optionCButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addToUserChoices(2);
            }
        });

        optionDButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addToUserChoices(3);
            }
        });

        optionEButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addToUserChoices(4);
            }
        });

    }



    private void addToUserChoices(Integer userChoice){

        Integer correctness = (userChoice == correctAnswer) ? 1 : 0;

        if (questionIndex == (UserChoices.choices.size())){

            UserChoices.choices.add(new Choice(userChoice,correctness));
            setButtonColorBasedOnPreviousAnswer(this.getView());
        }
        else if (questionIndex < (UserChoices.choices.size())){

            if (UserChoices.choices.get(questionIndex).userAnswer == -1){
                UserChoices.choices.set(questionIndex,new Choice(userChoice,correctness));
                setButtonColorBasedOnPreviousAnswer(this.getView());
            }

        }
        else{
            while (questionIndex > (UserChoices.choices.size())){
                UserChoices.choices.add(new Choice(-1,-1));
            }
            UserChoices.choices.add(new Choice(userChoice,correctness));
            setButtonColorBasedOnPreviousAnswer(this.getView());
        }

        mlistener.buttonClicked(questionIndex);

    }



    private void setButtonColorBasedOnPreviousAnswer(View rootView){

        Button optionAButton = rootView.findViewById(R.id.button_optionA);
        Button optionBButton = rootView.findViewById(R.id.button_optionB);
        Button optionCButton = rootView.findViewById(R.id.button_optionC);
        Button optionDButton = rootView.findViewById(R.id.button_optionD);
        Button optionEButton = rootView.findViewById(R.id.button_optionE);

        final List<Button> buttons= new ArrayList<Button>();
        buttons.add(optionAButton);
        buttons.add(optionBButton);
        buttons.add(optionCButton);
        buttons.add(optionDButton);
        buttons.add(optionEButton);


        if (questionIndex < UserChoices.choices.size()){

            final int userChoice = UserChoices.choices.get(questionIndex).userAnswer;

            if (userChoice != -1){

                if (correctAnswer == userChoice){

                    animateButton(buttons.get(userChoice),true);

                }

                else{
                    animateButton(buttons.get(userChoice),false);
                    animateButton(buttons.get(correctAnswer),true);
                    //buttons.get(userChoice).setBackgroundTintList(getResources().getColorStateList(R.color.red));
                    //buttons.get(correctAnswer).setBackgroundTintList(getResources().getColorStateList(R.color.green));
                }

            }
        }

    }

    private void animateButton(final Button button, boolean correct){

        if (correct){
            final ObjectAnimator animator = ObjectAnimator.ofInt(button, "backgroundTint", Color.rgb(100,100,100), Color.rgb(0,180,0));
            animator.setDuration(2000L);
            animator.setEvaluator(new ArgbEvaluator());
            animator.setInterpolator(new DecelerateInterpolator(2));
            animator.addUpdateListener(new ObjectAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    int animatedValue = (int) animation.getAnimatedValue();
                    button.setBackgroundTintList(ColorStateList.valueOf(animatedValue));
                }
            });
            animator.start();
        }

        else{
            final ObjectAnimator animator = ObjectAnimator.ofInt(button, "backgroundTint", Color.rgb(100,100,100), Color.rgb(255,50,50));
            animator.setDuration(2000L);
            animator.setEvaluator(new ArgbEvaluator());
            animator.setInterpolator(new DecelerateInterpolator(2));
            animator.addUpdateListener(new ObjectAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    int animatedValue = (int) animation.getAnimatedValue();
                    button.setBackgroundTintList(ColorStateList.valueOf(animatedValue));
                }
            });
            animator.start();
        }


    }

    private Integer convertStringAnswerToInt(String answer){
        switch(answer) {
            case "A":
                return 0;
            case "B":
                return 1;
            case "C":
                return 2;
            case "D":
                return 3;
            case "E":
                return 4;
            default:
                return -1;
        }
    }


}
