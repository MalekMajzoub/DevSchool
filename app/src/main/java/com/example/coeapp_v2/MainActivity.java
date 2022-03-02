package com.example.coeapp_v2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText username, firstname, lastname, password, repassword;
    Button signup, signin;
    CheckBox termCheck;
    TextView termText;
    DBHelper DB;

    SharedPreferences sharedPreferences;
    private static final String SHARED_PREF_NAME = "mypref";
    private static final String KEY_USERNAME = "username";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        username = findViewById(R.id.username);
        firstname = findViewById(R.id.firstname);
        lastname = findViewById(R.id.lastname);
        password = findViewById(R.id.password);
        repassword = findViewById(R.id.repassword);
        signup = findViewById(R.id.signup);
        signin = findViewById(R.id.signin);
        termCheck = findViewById(R.id.termCheck);
        termText = findViewById(R.id.termText);
        DB = new DBHelper(this);

        termCheck.setText("");
        termText.setText(Html.fromHtml("I have read and agree to the <a href='https://www.websitepolicies.com/policies/view/qVgm84va'>TERMS AND CONDITIONS</a>"));
        termText.setClickable(true);
        termText.setMovementMethod(LinkMovementMethod.getInstance());

        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME,MODE_PRIVATE);

        String name = sharedPreferences.getString(KEY_USERNAME,null);

        signup.setOnClickListener(v -> {
            String user = username.getText().toString();
            String fname = firstname.getText().toString();
            String lname = lastname.getText().toString();
            String pass = password.getText().toString();
            String repass = repassword.getText().toString();

            if(TextUtils.isEmpty(user) || TextUtils.isEmpty(fname)|| TextUtils.isEmpty(lname)|| TextUtils.isEmpty(pass) || TextUtils.isEmpty(repass))
                Toast.makeText(MainActivity.this, "All fields are required", Toast.LENGTH_SHORT).show();

            else{
                if(pass.equals(repass)){
                   boolean checkUser = DB.checkUsername(user);
                   if(!checkUser){
                       if(!termCheck.isChecked()){
                           Toast.makeText(MainActivity.this, "You need to agree to terms and conditions", Toast.LENGTH_SHORT).show();
                       }else {
                           boolean insert = DB.insertData(user, fname, lname, pass);
                           if (insert) {
                               SharedPreferences.Editor editor = sharedPreferences.edit();
                               editor.putString(KEY_USERNAME, user);
                               editor.apply();
                               Toast.makeText(MainActivity.this, "Registered Successfully", Toast.LENGTH_SHORT).show();
                               Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                               startActivity(intent);
                           } else {
                               Toast.makeText(MainActivity.this, "Registration Failed", Toast.LENGTH_SHORT).show();
                           }
                       }
                   }else{
                       Toast.makeText(MainActivity.this, "User already exists",Toast.LENGTH_SHORT).show();
                   }
                }else{
                    Toast.makeText(MainActivity.this, "Passwords are not matching", Toast.LENGTH_SHORT).show();
                }
            }
        });

        signin.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
        });

    }


}