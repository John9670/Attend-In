package com.example.firestoretest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class CheckedIn extends AppCompatActivity {
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checked_in);

        id = getIntent().getStringExtra("id");
    }

    public void done(View view){
        Intent intent = new Intent(getBaseContext(),MainActivity.class);
        intent.putExtra("id",id);
        intent.putExtra("check",0);
        startActivity(intent);


    }
}