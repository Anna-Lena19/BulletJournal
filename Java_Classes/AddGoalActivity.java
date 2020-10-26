package com.example.bulletjournal;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddGoalActivity extends AppCompatActivity {

    EditText goalYearly,goalMonthly;
    Button addGoal;
    String goalYearlyString, goalMonthlyString;
    GoalManager goalManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_goal);


        goalManager = new GoalManager(this);
        goalYearly = findViewById(R.id.goalYearly);
        goalMonthly = findViewById(R.id.goalMonthly);
        addGoal = findViewById(R.id.addGoal);
        addGoal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goalYearlyString = goalYearly.getText().toString();
                goalMonthlyString = goalMonthly.getText().toString();
                Goal goal = new Goal();
                goal.setYearlyGoal(goalYearlyString);
                goal.setMonthlyGoal(goalMonthlyString);
                goalManager.addGoal(goal);
            }
        });


    }
}