package com.example.astronomyquiz;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class ResultRecyclerAdapter extends RecyclerView.Adapter<ResultRecyclerAdapter.MyViewHolder>  {


    public static class MyViewHolder extends RecyclerView.ViewHolder{
        public View view;
        public MyViewHolder(View v){
            super(v);
            view = v;
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.activity_result_grid_cell,viewGroup, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {

        TextView questionNumTextView = myViewHolder.view.findViewById(R.id.textView_question_number);
        questionNumTextView.setText("Question "+(i+1));

        ImageView iconView = myViewHolder.view.findViewById(R.id.image_question_state_icon);
        Integer userAnswerCorrectness = UserChoices.choices.get(i).answerCorrectness;

        if (userAnswerCorrectness == 0){
            iconView.setImageResource(R.drawable.resultactivity_cross_icon);
        }

        else if (userAnswerCorrectness == 1){
            iconView.setImageResource(R.drawable.resultactivity_tickicon);
        }

        else{
            iconView.setImageResource(R.drawable.resultactivity_unknown_icon);
        }


    }

    @Override
    public int getItemCount() {
        return UserChoices.choices.size();
    }
}
