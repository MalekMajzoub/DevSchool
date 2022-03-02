package com.example.coeapp_v2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;

public class About extends AppCompatActivity {

    private Button buttonInstagram;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        getSupportActionBar().hide();

        buttonInstagram = findViewById(R.id.buttonInstagram);

        buttonInstagram.setOnClickListener(v->{
            Intent instaPage = new Intent(Intent.ACTION_VIEW);
            instaPage.setData(Uri.parse("https://www.instagram.com/we_are_dev_school/"));
            startActivity(instaPage);
        });

    }
}