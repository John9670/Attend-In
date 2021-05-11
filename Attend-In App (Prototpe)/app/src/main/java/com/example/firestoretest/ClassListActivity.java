package com.example.firestoretest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ClassListActivity extends AppCompatActivity {
    private GoogleFireStore g;
    private ListView listView;
    private String id;
    private int list;
    private String crn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_list);

        listView = findViewById(R.id.ListView);
        g = new GoogleFireStore();

        id = getIntent().getStringExtra("id");
        list = getIntent().getIntExtra("list",0);

        if(list == 0) {
            g.getClassList(id, new GoogleFireStore.OnGetClassListener() {
                @Override
                public void onComplete(ArrayList<String> success) {
                    ArrayAdapter arrayAdapter = new ArrayAdapter(ClassListActivity.this, R.layout.row, success);
                    listView.setAdapter(arrayAdapter);
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            Intent intent = new Intent(getBaseContext(), MapsActivity.class);
                            intent.putExtra("id", id);
                            intent.putExtra("crn",(String) adapterView.getItemAtPosition(i));
                            intent.putExtra("location", (String) adapterView.getItemAtPosition(i));
                            startActivity(intent);
                        }
                    });
                }
            });
        }
        else if (list == 1){
            g.getOwnedClassList(id, new GoogleFireStore.OnGetClassListener() {
                @Override
                public void onComplete(ArrayList<String> success) {
                    ArrayAdapter arrayAdapter = new ArrayAdapter(ClassListActivity.this,  R.layout.row, success);
                    listView.setAdapter(arrayAdapter);
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(final AdapterView<?> adapterView, View view, final int i, long l) {
                            g.getAttendanceList((String) adapterView.getItemAtPosition(i), new GoogleFireStore.OnGetClassListener() {
                                @Override
                                public void onComplete(ArrayList<String> success) {
                                    crn =(String) adapterView.getItemAtPosition(i);
                                    ArrayAdapter arrayAdapter = new ArrayAdapter(ClassListActivity.this,  R.layout.row, success);
                                    listView.setAdapter(arrayAdapter);
                                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                            g.getStudentAttendance(crn, (String) adapterView.getItemAtPosition(i), new GoogleFireStore.OnGetClassListener() {
                                                @Override
                                                public void onComplete(ArrayList<String> success) {
                                                    ArrayAdapter arrayAdapter = new ArrayAdapter(ClassListActivity.this,  R.layout.row, success);
                                                    listView.setAdapter(arrayAdapter);
                                                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                                        @Override
                                                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                                                        }
                                                    });
                                                }
                                            });

                                        }
                                    });
                                }
                            });
                        }
                    });
                }
            });
        }
        else {
            g.getOwnedClassList(id, new GoogleFireStore.OnGetClassListener() {
                @Override
                public void onComplete(ArrayList<String> success) {
                    ArrayAdapter arrayAdapter = new ArrayAdapter(ClassListActivity.this,  R.layout.row, success);
                    listView.setAdapter(arrayAdapter);
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(final AdapterView<?> adapterView, View view, final int i, long l) {
                            g.isValidAttendanceList((String) adapterView.getItemAtPosition(i), new GoogleFireStore.OnGetDataListener() {
                                @Override
                                public void onComplete(boolean success) {
                                    if(success){
                                        Intent intent = new Intent(getBaseContext(), MainActivity.class);
                                        intent.putExtra("id", id);
                                        Toast.makeText(ClassListActivity.this, "Class Has Already Started", Toast.LENGTH_SHORT).show();
                                        startActivity(intent);
                                    }
                                    else{
                                        g.mkAttendanceList((String) adapterView.getItemAtPosition(i), new GoogleFireStore.OnGetDataListener() {
                                            @Override
                                            public void onComplete(boolean success) {
                                                Intent intent = new Intent(getBaseContext(), MainActivity.class);
                                                intent.putExtra("id", id);
                                                Toast.makeText(ClassListActivity.this, "Starting Class", Toast.LENGTH_SHORT).show();
                                                startActivity(intent);
                                            }
                                        });
                                    }
                                }
                            });
                        }
                    });
                }
            });
        }
    }
}