package com.example.firestoretest;

import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class AddClass extends AppCompatActivity {
    private String id;
    private EditText crn;
    private GoogleFireStore g;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_class);

        id = getIntent().getStringExtra("id");
        crn = findViewById(R.id.crnText);
        g = new GoogleFireStore();


    }

    public void done(View view){
        g.addClass(id, crn.getText().toString(), new GoogleFireStore.OnGetClassListener() {
            @Override
            public void onComplete(ArrayList<String> success) {
                Intent intent = new Intent(getBaseContext(), MainActivity.class);
                intent.putExtra("id",id);
                startActivity(intent);
            }
        });

    }
}