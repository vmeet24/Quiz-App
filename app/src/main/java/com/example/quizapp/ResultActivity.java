package com.example.quizapp;

import android.content.Intent;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class ResultActivity extends AppCompatActivity {

    TextView attempted , correct , incorrect;
    Button againPlay;
    private ConstraintLayout constraintLayout;
    private ConstraintSet c1 = new ConstraintSet();
    private ConstraintSet c2 = new ConstraintSet();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_result_screen);
        constraintLayout = findViewById(R.id.constraintLayout);
        correct = findViewById(R.id.correct);
        incorrect = findViewById(R.id.incorrect);
        attempted = findViewById(R.id.attempted);
        againPlay = findViewById(R.id.againPlay);
        int[] result =getIntent().getIntArrayExtra("result");

        HashMap<Integer, Integer> data = new HashMap<Integer, Integer>();
        for (int i : result) {
            if (!data.containsKey(i))
                data.put(i, 1);
            else
                data.put(i, data.get(i) + 1);
        }

        int value = (data.get(0)==null)?0:data.get(0);
        attempted.setText("Attempted : "+(result.length- value));

        value=(data.get(2)==null)?0:data.get(2);
        correct.setText("Correct : "+ value);

        value=(data.get(1)==null)?0:data.get(1);
        incorrect.setText("Wrong : "+ value );

        c1.clone(constraintLayout);
        c2.clone(this, R.layout.activity_result);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                AutoTransition t = new AutoTransition();
                t.setDuration(3000);
                TransitionManager.beginDelayedTransition(constraintLayout,t);
                c2.applyTo(constraintLayout);
            }
        },500);

        againPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ResultActivity.this,MainActivity.class);
                startActivity(i);
            }
        });

    }
}
