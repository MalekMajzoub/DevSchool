package com.example.coeapp_v2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class Tutorial extends AppCompatActivity {

    Spinner spinner;
    Lesson1 lesson1;
    Lesson2 lesson2;
    Lesson3 lesson3;
    Lesson4 lesson4;
    Lesson5 lesson5;
    Lesson6 lesson6;
    Lesson7 lesson7;

    Button backBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);
        getSupportActionBar().hide();

        spinner = findViewById(R.id.spinnerTutorial);

        lesson1 = new Lesson1();
        lesson2 = new Lesson2();
        lesson3 = new Lesson3();
        lesson4 = new Lesson4();
        lesson5 = new Lesson5();
        lesson6 = new Lesson6();
        lesson7 = new Lesson7();

        backBtn = findViewById(R.id.backBtn);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(Tutorial.this, R.layout.custom_spinner,getResources().getStringArray(R.array.fragments));

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch(i){
                    case 0:
                        setFragment(lesson1);
                        break;
                    case 1:
                        setFragment(lesson2);
                        break;
                    case 2:
                        setFragment(lesson3);
                        break;
                    case 3:
                        setFragment(lesson4);
                        break;
                    case 4:
                        setFragment(lesson5);
                        break;
                    case 5:
                        setFragment(lesson6);
                        break;
                    case 6: // 5
                        setFragment(lesson7); //
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        backBtn.setOnClickListener(v->{
            Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
            startActivity(intent);
        });

    }

    public void setFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frameLayoutTutorial,fragment);
        fragmentTransaction.commit();
    }

}