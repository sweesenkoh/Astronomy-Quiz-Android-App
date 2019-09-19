package com.example.astronomyquiz;

import java.util.ArrayList;
import java.util.List;


public class UserChoices {

    public static List<Choice> choices = new ArrayList<>();

    public boolean userDoneAllQuestions(){
        for (int x = 0 ; x < choices.size() ; x++){
            if (choices.get(x).userAnswer == -1){
                return false;
            }
        }
        return true;
    }

}

