package com.example.firestoretest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void checkInButton (View view){

    }
    public void addClassButton (View view){

    }

    public void othersButton(View view){
        Intent intent = new Intent(getBaseContext(), MapsActivity.class);
        startActivity(intent);

    }

}