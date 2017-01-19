package com.example.minhnguyen.tuongmin_sizebook;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    ListView lv;
    Button addNewButton;
    SharedPreferences prefs;

    public static final String PrefFileName ="PrefFile";

    public List<String> people = new ArrayList<>();
    public static List<String> ID_people = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lv = (ListView) findViewById(R.id.aList);
        prefs = getSharedPreferences(PrefFileName, MODE_PRIVATE);
        addNewButton = (Button) findViewById(R.id.addNewButton);
        addNewButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), AddEdit.class);
                startActivity(intent);
            }
        });

        Map<String, ?> keys = prefs.getAll();
        System.out.println("this is: "+keys);
        for (Map.Entry<String, ?> entry : keys.entrySet()) {
            System.out.println(entry.getKey()+": "+entry.getValue().toString());
            people.add(entry.getValue().toString().split(Person.sep)[0]);
            if (!ID_people.contains(entry.getKey())) ID_people.add(entry.getKey());
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, people);

        try {
            lv.setAdapter(arrayAdapter);
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intentEdit = new Intent(view.getContext(), AddEdit.class);
                    intentEdit.putExtra("id", ID_people.get(position).toString());
                    startActivity(intentEdit);

                }
            });
        }
        catch (NullPointerException e) {
            Intent intent = new Intent(this.getApplicationContext(), AddEdit.class);
            startActivity(intent);
        }
    }
}
