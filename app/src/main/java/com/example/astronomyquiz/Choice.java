package com.example.astronomyquiz;

public class Choice {

    public Choice(Integer userAnswer, Integer answerCorrectness) {
        this.userAnswer = userAnswer;
        this.answerCorrectness = answerCorrectness;
    }

    public Integer userAnswer;
    public Integer answerCorrectness;
}
