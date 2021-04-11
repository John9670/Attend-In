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

public class MakeClassActivity extends AppCompatActivity {
    private TextView t1;
    private TextView t2;
    private SimpleDateFormat tm1;
    private  SimpleDateFormat tm2;
    private EditText lon;
    private EditText lat;
    private  EditText crn;
    private GoogleFireStore g;

    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_class);

        id = getIntent().getStringExtra("id");

        t1 = findViewById(R.id.startText);
        t2 = findViewById(R.id.endText);
        lon = findViewById(R.id.lonText);
        lat = findViewById(R.id.latText);
        crn = findViewById(R.id.crnText);
        g = new GoogleFireStore();

        t1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(
                        MakeClassActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int i, int i1) {

                        Calendar calendar = Calendar.getInstance();
                        calendar.set(0,0,0,i,i1);
                        tm1 = new SimpleDateFormat("hh:mm aa");
                        String test = tm1.format(calendar.getTime());

                        t1.setText(test);

                    }
                },12,0,false
                );
                timePickerDialog.show();
            }
        });
        t2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(
                        MakeClassActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int i, int i1) {
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(0,0,0,i,i1);
                        tm2 = new SimpleDateFormat("hh:mm aa");
                        String test = tm2.format(calendar.getTime());

                        t2.setText(test);

                    }
                },12,0,false
                );
                timePickerDialog.show();
            }
        });

    }
    public void confirm(View view){
        g.addOwnerClass(id, crn.getText().toString(), new GoogleFireStore.OnGetClassListener() {
            @Override
            public void onComplete(ArrayList<String> success) {
                g.mkClass(tm1,tm2,lon.getText().toString(),lat.getText().toString(),crn.getText().toString());
                Toast.makeText(MakeClassActivity.this, "Making Class", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getBaseContext(), MainActivity.class);
                intent.putExtra("id",id);
                startActivity(intent);
            }
        });
    }
}