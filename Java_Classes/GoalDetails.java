package com.example.bulletjournal;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class GoalDetails extends AppCompatActivity {

    String weeklyGoal, yearlyGoal;
    TextView textView;
    ImageButton editGoal;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goal_details);

        if (getIntent().hasExtra("yearlyGoal") && getIntent().hasExtra("monthlyGoal")) {
            weeklyGoal = getIntent().getStringExtra("yearlyGoal");
            yearlyGoal = getIntent().getStringExtra("monthlyGoal");

        }

        textView = findViewById(R.id.setMonthlyGoal);
        textView.setText(weeklyGoal);
        textView.setTextColor(Color.parseColor("#f00064"));

        editGoal = findViewById(R.id.edditGoal);
        editGoal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // todo open editpage
            }
        });
    }
}