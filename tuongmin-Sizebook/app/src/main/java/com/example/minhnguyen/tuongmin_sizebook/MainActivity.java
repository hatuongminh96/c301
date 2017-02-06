/*
 * Copyright (c) 2017. Tuong Minh Nguyen Tran. University of Alberta. All rights reserved.
 */

package com.example.minhnguyen.tuongmin_sizebook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Main screen of the application. This is where the ListView will display the name of people
 * saved in the program.
 */

public class MainActivity extends AppCompatActivity {

    /* Declare variables name */
    ListView lv;
    TextView count;
    Button addNewButton;

    static final String FileName ="data.json";
    static List<Person> people = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* Instantiate variables with objects of its kind */
        lv = (ListView) findViewById(R.id.aList);
        count = (TextView) findViewById(R.id.viewCount);
        addNewButton = (Button) findViewById(R.id.addNewButton);
        addNewButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                /* When click Add New, open AddEdit activity */

                Intent intent = new Intent(v.getContext(), AddEdit.class);
                startActivity(intent);
            }
        });

        /**
         * Call loadFromFile() to load Person objects saved in save file to people ArrayList
         * For each people in the people list get their name and save to the name list, which
         * will be used to show on ListView
         */
        loadFromFile();
        List<String> people_name = new ArrayList<>();
        for (Person p : people) {
            people_name.add(p.getName());
        }

        /** The total number of entries. This is equal the length of the people ArrayList */
        count.setText("Number of entry: " + String.valueOf(people.size()));

        /* Create an array adapter with the name list*/
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, people_name);
        lv.setAdapter(arrayAdapter);

        /** When click on an entry in the list view, open the AddEdit activity and send the
         * position of that entry to the AddEdit activity. The position is also the
         * index of that Person in the people list
         */
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intentEdit = new Intent(view.getContext(), AddEdit.class);
                intentEdit.putExtra("id", String.valueOf(position));
                startActivity(intentEdit);
            }
        });
    }

    /**
     * Open the save file and load all saved object to people list
     */
    private void loadFromFile() {
        try {
            FileInputStream fis = openFileInput(FileName);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));
            Gson gson = new Gson();
            people=gson.fromJson(in, new TypeToken<ArrayList<Person>>(){}.getType());
            fis.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            people = new ArrayList<Person>();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException();
        }
    }
}
