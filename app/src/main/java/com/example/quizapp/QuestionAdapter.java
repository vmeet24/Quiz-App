package com.example.quizapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.ViewHolder>{

    Context mContext;
    ArrayList<QaModel> arrayList;

    public QuestionAdapter(Context mContext, ArrayList<QaModel> arrayList) {
        this.mContext = mContext;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.question_list,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView questionTitlePage;
        TextView questionDataPage;
        RadioGroup radioGroup;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            questionDataPage = itemView.findViewById(R.id.questionDataPage);
            questionTitlePage = itemView.findViewById(R.id.questionTitlePage);
            radioGroup = itemView.findViewById(R.id.radioGroup);
        }
    }
}
