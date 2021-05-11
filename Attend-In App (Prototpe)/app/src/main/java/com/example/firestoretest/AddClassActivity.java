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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddClassActivity extends AppCompatActivity {
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

    public void add(View view){
        final String stringCRN = crn.getText().toString();
        if(validCRN(stringCRN)){
            g.isValidClass(stringCRN, new GoogleFireStore.OnGetDataListener() {
                @Override
                public void onComplete(boolean success) {
                    if(success){
                        g.dupClass(id, stringCRN, new GoogleFireStore.OnGetDataListener() {
                            @Override
                            public void onComplete(boolean success) {
                                if(success){
                                    Intent intent = new Intent(getBaseContext(), MainActivity.class);
                                    intent.putExtra("id",id);
                                    startActivity(intent);
                                    Toast.makeText(AddClassActivity.this, "You Have Already Added This Class", Toast.LENGTH_SHORT).show();

                                }
                                else {
                                    g.addClass(id, stringCRN, new GoogleFireStore.OnGetClassListener() {
                                        @Override
                                        public void onComplete(ArrayList<String> success) {
                                            Intent intent = new Intent(getBaseContext(), MainActivity.class);
                                            intent.putExtra("id",id);
                                            startActivity(intent);
                                            Toast.makeText(AddClassActivity.this, "Adding Class", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                            }
                        });
                    }
                    else{
                        Toast.makeText(AddClassActivity.this, "Input Valid CRN", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
        else{
            Toast.makeText(AddClassActivity.this, "Input Valid CRN", Toast.LENGTH_SHORT).show();
        }

    }

    public boolean validCRN(String crn){
        Pattern pattern = Pattern.compile("\\d{6}");
        Matcher matcher = pattern.matcher(crn);
        if(matcher.matches()){
            return true;
        }
        else{
            return false;
        }
    }
}