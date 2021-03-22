package com.example.firestoretest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    private GoogleFireStore g;
    private String id;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        g = new GoogleFireStore();
        id = getIntent().getStringExtra("id");
    }

    public void checkInButton (View view){
        Intent intent = new Intent(getBaseContext(), ClassList.class);
        intent.putExtra("id",id);
        startActivity(intent);

    }
    public void addClassButton (View view){
        Intent intent = new Intent(getBaseContext(), AddClass.class);
        intent.putExtra("id",id);
        startActivity(intent);
    }

    public void mkClassButton(View view){
        Intent intent = new Intent(getBaseContext(), MakeClass.class);
        intent.putExtra("id",id);
        startActivity(intent);


    }

}