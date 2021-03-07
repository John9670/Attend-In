package com.example.firestoretest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {
    private GoogleFireStore g;
    private EditText editTextName;
    private EditText editTextId;
    private Button buttonLogin;
    private Button buttonSignup;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //From the login activity I've grabbed the two edit text and labeled to the respective global variables
        editTextName = findViewById(R.id.username);
        editTextId = findViewById(R.id.id);
        buttonLogin = findViewById(R.id.login);
        buttonLogin = findViewById(R.id.signup);
        g = new GoogleFireStore();
    }

    public void loginButton(View view) {
        //TODO check if valid signin else ask them for the info again ...


        Intent intent = new Intent(getBaseContext(), MapsActivity.class);
        startActivity(intent);

    }

    public void signupButton(View view) {
        //TODO check if there is already an account else ...

        g.setUserAcc(editTextName.getText().toString(),editTextId.getText().toString());

        Intent intent = new Intent(getBaseContext(), MainActivity.class);
        startActivity(intent);

    }
}