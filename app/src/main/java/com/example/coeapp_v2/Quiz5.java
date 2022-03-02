package com.example.coeapp_v2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.Locale;

public class Quiz5 extends AppCompatActivity {
    public static final String EXTRA_SCORE5 = "extraScore";
    private static final long COUNTDOWN_IN_MILLIS = 90000;

    private TextView textViewScore;
    private TextView textViewCountDown;
    private TextView textViewQuestion1;
    private RadioGroup rbGroup1;
    private RadioButton rb11;
    private RadioButton rb12;
    private RadioButton rb13;

    private TextView textViewQuestion2;
    private RadioGroup rbGroup2;
    private RadioButton rb21;
    private RadioButton rb22;
    private RadioButton rb23;

    private TextView textViewQuestion3;
    private RadioGroup rbGroup3;
    private RadioButton rb31;
    private RadioButton rb32;
    private RadioButton rb33;
    private Button buttonConfirm;

    private ColorStateList textColorDefaultCD;

    private CountDownTimer countDownTimer;
    private long timeLeftInMillis;

    private List<Question> questionList;
    private Question currentQuestion1;
    private Question currentQuestion2;
    private Question currentQuestion3;


    private int questionCounter;
    private int score;
    private boolean answered;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz5);

        textViewScore = findViewById(R.id.quiz5_score);

        textViewCountDown = findViewById(R.id.quiz5_countdown);

        textViewQuestion1 = findViewById(R.id.quiz5_question1);
        rbGroup1 = findViewById(R.id.quiz5_radio_group1);
        rb11 = findViewById(R.id.quiz5_radio_button11);
        rb12 = findViewById(R.id.quiz5_radio_button12);
        rb13 = findViewById(R.id.quiz5_radio_button13);

        textViewQuestion2 = findViewById(R.id.quiz5_question2);
        rbGroup2 = findViewById(R.id.quiz5_radio_group2);
        rb21 = findViewById(R.id.quiz5_radio_button21);
        rb22 = findViewById(R.id.quiz5_radio_button22);
        rb23 = findViewById(R.id.quiz5_radio_button23);

        textViewQuestion3 = findViewById(R.id.quiz5_question3);
        rbGroup3 = findViewById(R.id.quiz5_radio_group3);
        rb31 = findViewById(R.id.quiz5_radio_button31);
        rb32 = findViewById(R.id.quiz5_radio_button32);
        rb33 = findViewById(R.id.quiz5_radio_button33);

        buttonConfirm = findViewById(R.id.quiz5_confirm);

        textColorDefaultCD = textViewCountDown.getTextColors();

        QuizDbHelper dbHelper = new QuizDbHelper(this);
        questionList = dbHelper.getAllQuestions();

        showQuestions();

        buttonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!answered) {
                    if ((rb11.isChecked() || rb12.isChecked() || rb13.isChecked())
                            & (rb21.isChecked() || rb22.isChecked() || rb23.isChecked())
                            & (rb31.isChecked() || rb32.isChecked() || rb33.isChecked())) {
                        checkAnswer();
                    } else {
                        Toast.makeText(Quiz5.this, "You still have answers not selected", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    finishQuiz();
                }
            }
        });
    }

    private void showQuestions() {
        rb11.setTextColor(Color.BLACK);
        rb12.setTextColor(Color.BLACK);
        rb13.setTextColor(Color.BLACK);
        rbGroup1.clearCheck();

        rb21.setTextColor(Color.BLACK);
        rb22.setTextColor(Color.BLACK);
        rb23.setTextColor(Color.BLACK);
        rbGroup2.clearCheck();

        rb31.setTextColor(Color.BLACK);
        rb32.setTextColor(Color.BLACK);
        rb33.setTextColor(Color.BLACK);
        rbGroup3.clearCheck();

        while(questionCounter < 12){
            questionCounter++;
        }
        if (questionCounter < 15) {

            currentQuestion1 = questionList.get(questionCounter);
            textViewQuestion1.setText(currentQuestion1.getQuestion());
            rb11.setText(currentQuestion1.getOption1());
            rb12.setText(currentQuestion1.getOption2());
            rb13.setText(currentQuestion1.getOption3());
            questionCounter++;
            answered = false;
            buttonConfirm.setText("Confirm");

            currentQuestion2 = questionList.get(questionCounter);
            textViewQuestion2.setText(currentQuestion2.getQuestion());
            rb21.setText(currentQuestion2.getOption1());
            rb22.setText(currentQuestion2.getOption2());
            rb23.setText(currentQuestion2.getOption3());
            questionCounter++;

            currentQuestion3 = questionList.get(questionCounter);
            textViewQuestion3.setText(currentQuestion3.getQuestion());
            rb31.setText(currentQuestion3.getOption1());
            rb32.setText(currentQuestion3.getOption2());
            rb33.setText(currentQuestion3.getOption3());
            questionCounter++;

            timeLeftInMillis = COUNTDOWN_IN_MILLIS;
            startCountDown();
        } else {
            finishQuiz();
        }
    }

    private void startCountDown() {
        countDownTimer = new CountDownTimer(timeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMillis = millisUntilFinished;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                timeLeftInMillis = 0;
                updateCountDownText();
                checkAnswer();
            }
        }.start();
    }

    private void updateCountDownText() {
        int minutes = (int) (timeLeftInMillis / 1000) / 60;
        int seconds = (int) (timeLeftInMillis / 1000) % 60;

        String timeFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);

        textViewCountDown.setText(timeFormatted);

        if (timeLeftInMillis < 10000) {
            textViewCountDown.setTextColor(Color.RED);
        } else {
            textViewCountDown.setTextColor(textColorDefaultCD);
        }
    }


    private void checkAnswer() {
        answered = true;

        countDownTimer.cancel();

        RadioButton rbSelected1 = findViewById(rbGroup1.getCheckedRadioButtonId());
        RadioButton rbSelected2 = findViewById(rbGroup2.getCheckedRadioButtonId());
        RadioButton rbSelected3 = findViewById(rbGroup3.getCheckedRadioButtonId());

        int answerNr1 = rbGroup1.indexOfChild(rbSelected1) + 1;
        int answerNr2 = rbGroup2.indexOfChild(rbSelected2) + 1;
        int answerNr3 = rbGroup3.indexOfChild(rbSelected3) + 1;

        if (answerNr1 == currentQuestion1.getAnswerNr()) {
            score++;
            textViewScore.setText("Score: " + score);
        }

        if (answerNr2 == currentQuestion2.getAnswerNr()) {
            score++;
            textViewScore.setText("Score: " + score);
        }

        if (answerNr3 == currentQuestion3.getAnswerNr()) {
            score++;
            textViewScore.setText("Score: " + score);
        }




        showSolution();
    }

    private void showSolution() {
        rb11.setTextColor(Color.RED);
        rb12.setTextColor(Color.RED);
        rb13.setTextColor(Color.RED);

        rb21.setTextColor(Color.RED);
        rb22.setTextColor(Color.RED);
        rb23.setTextColor(Color.RED);

        rb31.setTextColor(Color.RED);
        rb32.setTextColor(Color.RED);
        rb33.setTextColor(Color.RED);

        switch (currentQuestion1.getAnswerNr()) {
            case 1:
                rb11.setTextColor(Color.GREEN);
                textViewQuestion1.setText("Answer 1 is correct");
                break;
            case 2:
                rb12.setTextColor(Color.GREEN);
                textViewQuestion1.setText("Answer 2 is correct");
                break;
            case 3:
                rb13.setTextColor(Color.GREEN);
                textViewQuestion1.setText("Answer 3 is correct");
                break;
        }

        switch (currentQuestion2.getAnswerNr()) {
            case 1:
                rb21.setTextColor(Color.GREEN);
                textViewQuestion2.setText("Answer 1 is correct");
                break;
            case 2:
                rb22.setTextColor(Color.GREEN);
                textViewQuestion2.setText("Answer 2 is correct");
                break;
            case 3:
                rb23.setTextColor(Color.GREEN);
                textViewQuestion2.setText("Answer 3 is correct");
                break;
        }

        switch (currentQuestion3.getAnswerNr()) {
            case 1:
                rb31.setTextColor(Color.GREEN);
                textViewQuestion3.setText("Answer 1 is correct");
                break;
            case 2:
                rb32.setTextColor(Color.GREEN);
                textViewQuestion3.setText("Answer 2 is correct");
                break;
            case 3:
                rb33.setTextColor(Color.GREEN);
                textViewQuestion3.setText("Answer 3 is correct");
                break;
        }


        buttonConfirm.setText("Finish");
    }

    private void finishQuiz() {
        Intent resultIntent = new Intent();
        resultIntent.putExtra(EXTRA_SCORE5, score);
        setResult(RESULT_OK, resultIntent);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(countDownTimer != null) {
            countDownTimer.cancel();
        }
    }
}


