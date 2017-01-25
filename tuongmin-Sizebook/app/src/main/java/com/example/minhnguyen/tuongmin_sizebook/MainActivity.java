package com.example.minhnguyen.tuongmin_sizebook;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    /* Declare variables name */
    ListView lv;
    TextView count;
    Button addNewButton;
    SharedPreferences prefs;

    public static final String PrefFileName ="PrefFile";

    public List<String> people = new ArrayList<>();
    public static List<String> ID_people = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* Instantiate variables with objects of its kind */
        lv = (ListView) findViewById(R.id.aList);
        count = (TextView) findViewById(R.id.viewCount);
        prefs = getSharedPreferences(PrefFileName, MODE_PRIVATE);
        addNewButton = (Button) findViewById(R.id.addNewButton);
        addNewButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                /* When click Add New, open AddEdit activity */
                Intent intent = new Intent(v.getContext(), AddEdit.class);
                startActivity(intent);
            }
        });

        /* Get the ID of people saved in SharedPref. This is a string of integer seperated with ", ".
        * Its key is "keysInOrder". Once got the string, split it and save to a list.
        * */
        List<String> buffer = Arrays.asList(prefs.getString("keysInOrder",", ").split(", "));
        for (String s: buffer) {
            if (!ID_people.contains(s) && !s.equals("")) ID_people.add(s);
        }

        /* The total number of entries. This is equal the length of the ID list created above */
        count.setText("Number of entry: " + String.valueOf(ID_people.size()));

        /* For each ID in the ID list as key, get the value stored in SharedPref.
        * Split it with the separator defined in Person class. Get the first element (the name)
        * and save it to a list.
        * */
        for (String o: ID_people) {
            System.out.println(o + prefs.getString(o, null));
            people.add(prefs.getString(o, null).split(Person.sep)[0]);
        }

        /* Create an array adapter with the name list*/
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, people);

        try {
            /* Populate the ListView with the name list */
            lv.setAdapter(arrayAdapter);

            /* When click on an entry in the list view, open the AddEdit activity and send the ID
            * of that person to the AddEdit activity. The ID is the element at the index [position]
            * in the ID list. */
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intentEdit = new Intent(view.getContext(), AddEdit.class);
                    intentEdit.putExtra("id", ID_people.get(position));
                    startActivity(intentEdit);
                }
            });
        }
        catch (NullPointerException e) {
            /*justincase*/
            Intent intent = new Intent(this.getApplicationContext(), AddEdit.class);
            startActivity(intent);
        }
    }
}
