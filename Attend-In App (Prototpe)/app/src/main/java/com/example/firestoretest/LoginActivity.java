package com.example.firestoretest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

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
        editTextName = findViewById(R.id.usernameText);
        editTextId = findViewById(R.id.idText);
        buttonLogin = findViewById(R.id.loginButton);
        buttonLogin = findViewById(R.id.signupButton);
        g = new GoogleFireStore();

    }

    public void loginButton(View view) {
        //TODO check if valid signin else ask them for the info again ...
        String name = editTextName.getText().toString();
        String id = editTextId.getText().toString();

        if(name.equals("") || id.equals("")){
            Toast.makeText(LoginActivity.this, "Input Valid Name and Id", Toast.LENGTH_SHORT).show();
        }
        else {
            g.isValid(name,id, new GoogleFireStore.OnGetDataListener() {
                public void onComplete(boolean success){
                    if(success == true){
                        Toast.makeText(LoginActivity.this, "Logging", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getBaseContext(), MainActivity.class);
                        startActivity(intent);
                    }
                    else {
                        Toast.makeText(LoginActivity.this, "Input Valid Name and Id", Toast.LENGTH_SHORT).show();
                    }

                }
            });
        }
    }

    public void signupButton(View view) {
        //TODO check if there is already an account else ...
        final String name = editTextName.getText().toString();
        final String id = editTextId.getText().toString();

        if(name.equals("") || id.equals("")){
            Toast.makeText(LoginActivity.this, "Input Valid Name and Id", Toast.LENGTH_SHORT).show();
        }
        else {
            g.doesExist(id, new GoogleFireStore.OnGetDataListener() {
                public void onComplete(boolean success){
                    if(!success == true){
                        Toast.makeText(LoginActivity.this, "Making Account", Toast.LENGTH_SHORT).show();
                        g.setUserAcc(name, id);
                        Intent intent = new Intent(getBaseContext(), MainActivity.class);
                        startActivity(intent);
                    }
                    else{
                        Toast.makeText(LoginActivity.this, "Input Valid Id", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}