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

public class MakeClassActivity extends AppCompatActivity {
    private TextView t1;
    private TextView t2;
    private SimpleDateFormat tm1;
    private  SimpleDateFormat tm2;
    private EditText lon;
    private EditText lat;
    private  EditText crn;
    private GoogleFireStore g;
    private String startTime;
    private String endTime;

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
                        startTime = tm1.format(calendar.getTime());

                        t1.setText(startTime);

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
                        endTime = tm2.format(calendar.getTime());

                        t2.setText(endTime);

                    }
                },12,0,false
                );
                timePickerDialog.show();
            }
        });

    }

    public void confirm(View view) {
        if (startTime == null || endTime == null){
            Toast.makeText(MakeClassActivity.this, "Input Valid Times", Toast.LENGTH_SHORT).show();
        }
        else {
            if (validLat()) {
                if (validLon()) {
                    if (validCRN(crn.getText().toString())) {
                        g.dupOwnedClass(id, crn.getText().toString(), new GoogleFireStore.OnGetDataListener() {
                            @Override
                            public void onComplete(boolean success) {
                                if (success) {
                                    g.mkClass(startTime, endTime, lon.getText().toString(), lat.getText().toString(), crn.getText().toString());
                                    Toast.makeText(MakeClassActivity.this, "Updating Class", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getBaseContext(), MainActivity.class);
                                    intent.putExtra("id", id);
                                    startActivity(intent);
                                } else {
                                    g.addOwnerClass(id, crn.getText().toString(), new GoogleFireStore.OnGetClassListener() {
                                        @Override
                                        public void onComplete(ArrayList<String> success) {
                                            g.mkClass(startTime, endTime, lon.getText().toString(), lat.getText().toString(), crn.getText().toString());
                                            Toast.makeText(MakeClassActivity.this, "Making Class", Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(getBaseContext(), MainActivity.class);
                                            intent.putExtra("id", id);
                                            startActivity(intent);
                                        }
                                    });
                                }

                            }
                        });
                    } else {
                        Toast.makeText(MakeClassActivity.this, "Input Valid CRN", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(MakeClassActivity.this, "Input Valid Longitude", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(MakeClassActivity.this, "Input Valid Latitude", Toast.LENGTH_SHORT).show();
            }
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

    public boolean validLat(){
        try {
            Double d = Double.parseDouble(lat.getText().toString());
            if (-90 <= d && d <= 90){
                return true;
            }
            else{
                return false;
            }
        }
        catch (NumberFormatException e){
            return false;
        }

    }
    public boolean validLon(){
        try {
            Double d = Double.parseDouble(lon.getText().toString());
            if (-180 <= d && d <= 180){
                return true;
            }
            else{
                return false;
            }
        }
        catch (NumberFormatException e){
            return false;
        }
    }
}