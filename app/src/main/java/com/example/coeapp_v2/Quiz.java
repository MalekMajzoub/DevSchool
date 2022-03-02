package com.example.coeapp_v2;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Quiz extends AppCompatActivity {

    private static final int REQUEST_CODE_QUIZ = 1;

    private Button backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        backBtn = findViewById(R.id.backBtn);

        Button buttonStartQuiz1 = findViewById(R.id.button_quiz_one);
        buttonStartQuiz1.setOnClickListener(v-> startQuiz1());

        Button buttonStartQuiz2 = findViewById(R.id.button_quiz_two);
        buttonStartQuiz2.setOnClickListener(v-> startQuiz2());

        Button buttonStartQuiz3 = findViewById(R.id.button_quiz_three);
        buttonStartQuiz3.setOnClickListener(v-> startQuiz3());

        Button buttonStartQuiz4 = findViewById(R.id.button_quiz_four);
        buttonStartQuiz4.setOnClickListener(v-> startQuiz4());

        Button buttonStartQuiz5 = findViewById(R.id.button_quiz_five);
        buttonStartQuiz5.setOnClickListener(v-> startQuiz5());


        backBtn.setOnClickListener(v->{
            Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
            startActivity(intent);
        });

    }

    private void startQuiz1() {
        Intent intent = new Intent( Quiz.this, Quiz1.class);
        startActivityForResult(intent, REQUEST_CODE_QUIZ);

    }

    private void startQuiz2() {
        Intent intent = new Intent( Quiz.this, Quiz2.class);
        startActivityForResult(intent, REQUEST_CODE_QUIZ);

    }

    private void startQuiz3() {
        Intent intent = new Intent( Quiz.this, Quiz3.class);
        startActivityForResult(intent, REQUEST_CODE_QUIZ);

    }

    private void startQuiz4() {
        Intent intent = new Intent( Quiz.this, Quiz4.class);
        startActivityForResult(intent, REQUEST_CODE_QUIZ);

    }

    private void startQuiz5() {
        Intent intent = new Intent( Quiz.this, Quiz5.class);
        startActivityForResult(intent, REQUEST_CODE_QUIZ);

    }


}