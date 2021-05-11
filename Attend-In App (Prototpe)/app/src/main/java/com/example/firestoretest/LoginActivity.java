package com.example.firestoretest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class LoginActivity extends AppCompatActivity {
    private GoogleFireStore g;
    private EditText editTextName;
    private EditText editTextId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        editTextName = findViewById(R.id.usernameText);
        editTextId = findViewById(R.id.idText);
        g = new GoogleFireStore();

    }

    public void loginButton(View view) {
        final String name = editTextName.getText().toString();
        final String id = editTextId.getText().toString();

        if(name.equals("") || id.equals("")){
            Toast.makeText(LoginActivity.this, "Input Valid Name and Id", Toast.LENGTH_SHORT).show();
        }
        else if (!validPantherID (id)) {
            Toast.makeText(LoginActivity.this, "Input Valid Id", Toast.LENGTH_SHORT).show();
        }
        else {
            g.isValid(name,id, new GoogleFireStore.OnGetDataListener() {
                public void onComplete(boolean success){
                    if(success == true){
                        Toast.makeText(LoginActivity.this, "Logging", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getBaseContext(), MainActivity.class);
                        intent.putExtra("id", id);
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
        final String name = editTextName.getText().toString();
        final String id = editTextId.getText().toString();

        if(name.equals("") || id.equals("")){
            Toast.makeText(LoginActivity.this, "Input Valid Name and Id", Toast.LENGTH_SHORT).show();
        }
        else if(!validPantherID(id)){
            Toast.makeText(LoginActivity.this, "Input Valid Id", Toast.LENGTH_SHORT).show();
        }
        else {
            g.doesExist(id, new GoogleFireStore.OnGetDataListener() {
                public void onComplete(boolean success){
                    if(!success == true){
                        Toast.makeText(LoginActivity.this, "Making Account", Toast.LENGTH_SHORT).show();
                        g.setUserAcc(name, id, new GoogleFireStore.OnGetDataListener() {
                            @Override
                            public void onComplete(boolean success) {
                                Toast.makeText(LoginActivity.this, "Please Login", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                    else{
                        Toast.makeText(LoginActivity.this, "Input Valid Id", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    public boolean validPantherID(String id){
        Pattern pattern = Pattern.compile("\\d{3}-\\d{2}-\\d{4}");
        Matcher matcher = pattern.matcher(id);
        if(matcher.matches()){
            return true;
        }
        else{
            return false;
        }
    }
}