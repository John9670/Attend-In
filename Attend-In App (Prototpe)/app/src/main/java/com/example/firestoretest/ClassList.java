package com.example.firestoretest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class ClassList extends AppCompatActivity {
    private GoogleFireStore g;
    private ListView listView;
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_list);

        listView = findViewById(R.id.ListView);
        g = new GoogleFireStore();

        id = getIntent().getStringExtra("id");

        g.getClassList(id,new GoogleFireStore.OnGetClassListener() {
            @Override
            public void onComplete(ArrayList<String> success) {
                ArrayAdapter arrayAdapter = new ArrayAdapter(ClassList.this, android.R.layout.simple_list_item_1, success);
                listView.setAdapter(arrayAdapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Intent intent = new Intent(getBaseContext(),MapsActivity.class);
                        intent.putExtra("id",id);
                        startActivity(intent);
                    }
                });
            }
        });

    }




}