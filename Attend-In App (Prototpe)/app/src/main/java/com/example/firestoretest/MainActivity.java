package com.example.firestoretest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

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
        Intent intent = new Intent(getBaseContext(), ClassListActivity.class);
        intent.putExtra("list",0);
        intent.putExtra("id",id);
        startActivity(intent);

    }
    public void addClassButton (View view){
        Intent intent = new Intent(getBaseContext(), AddClassActivity.class);
        intent.putExtra("id",id);
        startActivity(intent);
    }

    public void mkClassButton(View view){
        Intent intent = new Intent(getBaseContext(), MakeClassActivity.class);
        intent.putExtra("id",id);
        startActivity(intent);
    }

    public void attendanceButton(View view){
        Intent intent = new Intent(getBaseContext(),  ClassListActivity.class);
        intent.putExtra("list",1);
        intent.putExtra("id",id);
        startActivity(intent);
    }

    public void startClassButton(View view){
        Intent intent = new Intent(getBaseContext(),  ClassListActivity.class);
        intent.putExtra("list",2);
        intent.putExtra("id",id);
        startActivity(intent);
    }


}