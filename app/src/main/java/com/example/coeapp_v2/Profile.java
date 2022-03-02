package com.example.coeapp_v2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class Profile extends AppCompatActivity {

    ImageView profilePic;
    TextView fullName, username;
    Button updateBtn;
    DBHelper DB;
    private static final int PICK_IMAGE = 100;
    Uri imageUri;

    SharedPreferences sharedPreferences;
    private static final String SHARED_PREF_NAME = "mypref";
    private static final String KEY_USERNAME = "username";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        username = findViewById(R.id.username_field);
        fullName = findViewById(R.id.fullname_field);
        profilePic = findViewById(R.id.profile_image);
        updateBtn = findViewById(R.id.updateBtn);
        DB = new DBHelper(this);

        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME,MODE_PRIVATE);

        String name = sharedPreferences.getString(KEY_USERNAME,null);
        if(name !=null){
            String fullname = DB.fetchName(name);
            fullName.setText(fullname);
            username.setText(name);
        }

        updateBtn.setOnClickListener(v -> {
                openGallery();
        });
    }
    private void openGallery(){
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE){
            imageUri = data.getData();
            profilePic.setImageURI(imageUri);
        }
    }
}