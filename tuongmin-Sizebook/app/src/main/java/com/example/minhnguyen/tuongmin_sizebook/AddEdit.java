/*
 * Copyright (c) 2017. Tuong Minh Nguyen Tran. University of Alberta. All rights reserved.
 */

package com.example.minhnguyen.tuongmin_sizebook;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

/**
 * This is the Add/Edit activity. When Adding a new person, this activity is shown with empty
 * text boxes. When selecting a person to edit, this activity is shown with that person
 * information in the text boxes, ready to modify, or keep still.
 */

public class AddEdit extends AppCompatActivity {

    /**  Declare variables name */
    Button saveButton;
    Button cancelButton;
    Button deleteButton;
    EditText eName;
    EditText eDate;
    EditText eNeck;
    EditText eBust ;
    EditText eChest ;
    EditText eWaist ;
    EditText eHip ;
    EditText eInseam;
    EditText eComment;
    Calendar cal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit);

        /** Define variables into real object */
        eName = (EditText) findViewById(R.id.name_editText);
        eDate = (EditText) findViewById(R.id.date_editText);
        eNeck = (EditText) findViewById(R.id.neck_editText);
        eBust = (EditText) findViewById(R.id.bust_editText);
        eChest = (EditText) findViewById(R.id.chest_editText);
        eWaist = (EditText) findViewById(R.id.waist_editText);
        eHip = (EditText) findViewById(R.id.hip_editText);
        eInseam = (EditText) findViewById(R.id.inseam_editText);
        eComment = (EditText) findViewById(R.id.comment_editText);

        saveButton = (Button) findViewById(R.id.button_save);
        cancelButton = (Button) findViewById(R.id.cancel_button);
        deleteButton = (Button) findViewById(R.id.delete_button);

        /** Get the position of the entry in ListView user selected. It is also the index in people
         * ArrayList in MainActivity
        */
        Intent rI = getIntent();
        final String id = rI.getStringExtra("id");
        final Intent intent = new Intent(this.getApplicationContext(), MainActivity.class);

        /**
         * Call seteDate. This function create a popup windows to select date when
         * the eDate EditText is selected.
         * */
        seteDate();
        System.out.println(id);

        /**
         * Add New is selected. Hide Delete button and show Cancel button.
         * When click on Save button, getInfo is called to get the information entered
         * in the text boxes and create a Person object. It will return null if
         * there is no Person Name entered.
         * When receive the Person object, add to the people list in MainActivity and
         * update save fiile.
         */
        if (id == null) {

            cancelButton.setVisibility(View.VISIBLE);
            deleteButton.setVisibility(View.GONE);
            saveButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {

                    Person person = getInfo();
                    if (person != null) {
                        MainActivity.people.add(person);
                        saveInFile();
                        startActivity(intent);
                    }
                }
            });

            cancelButton.setOnClickListener(new View.OnClickListener(){
                public void onClick (View v) {
                    startActivity(intent);
                }
            });
        }

        /**
        * An entry in ListView is selected. Get the person object as the position of the entry
         * in ListView is the index in the people ArrayList. Call SetInfo on that Person object.
         * Hide cancel button and show delete button. Save and Edit are done by updating the
         * people ArrayList in MainActivity and call saveInFile()
        * */
        else {

            final Person person = MainActivity.people.get(Integer.valueOf(id));
            System.out.println(person.getName());
            setInfo(person);

            cancelButton.setVisibility(View.GONE);
            deleteButton.setVisibility(View.VISIBLE);
            saveButton.setOnClickListener(new View.OnClickListener(){
                public void onClick(View v) {
                    Person updatePerson = getInfo();
                    MainActivity.people.set(Integer.valueOf(id), updatePerson);
                    saveInFile();
                    startActivity(intent);
                }
            });

            deleteButton.setOnClickListener(new View.OnClickListener(){
                public void onClick(View v){
                    MainActivity.people.remove(MainActivity.people.indexOf(person));
                    saveInFile();
                    startActivity(intent);
                }
            });
        }
    }

    /**
     * Get the information entered into text boxes  to create a Person object.
     * A person object needs a name. Any other variables can be null
     * */
    public Person getInfo() {

        String name = eName.getText().toString();
        String date = eDate.getText().toString();
        Integer neck = eNeck.getText().toString().isEmpty()? null :Integer.valueOf(eNeck.getText().toString());
        Integer bust = eBust.getText().toString().isEmpty()? null :Integer.valueOf(eBust.getText().toString());
        Integer chest = eChest.getText().toString().isEmpty()? null :Integer.valueOf(eChest.getText().toString());
        Integer waist = eWaist.getText().toString().isEmpty()? null :Integer.valueOf(eWaist.getText().toString());
        Integer hip = eHip.getText().toString().isEmpty()? null :Integer.valueOf(eHip.getText().toString());
        Integer inseam = eInseam.getText().toString().isEmpty()? null :Integer.valueOf(eInseam.getText().toString());
        String comment = eComment.getText().toString();

        if (!name.isEmpty()) {
            Person p = new Person(name);
            p.setBust(bust);
            p.setChest(chest);
            p.setComment(comment);
            p.setDate(date);
            p.setHip(hip);
            p.setInseam(inseam);
            p.setNeck(neck);
            p.setWaist(waist);
            return p;
        }

        /**
         * Notify when a person name is not entered, return null in order to not save a null
         * object to save file
         */
        else {

            Toast.makeText(getApplicationContext(), "A man needs a name!", Toast.LENGTH_LONG).show();
            return null;
        }
    }

    /**
     * Get a person object as input and set text boxes to their information accordingly
     */
    private void setInfo(Person p){

        String neck = p.getNeck() == null ? "" : p.getNeck().toString();
        String bust = p.getBust() == null ? "" : p.getBust().toString();
        String chest = p.getChest() == null ? "" : p.getChest().toString();
        String waist = p.getWaist() == null ? "" : p.getWaist().toString();
        String hip = p.getHip() == null ? "" : p.getHip().toString();
        String inseam = p.getInseam() == null ? "" : p.getInseam().toString();

        eName.setText(p.getName());
        eDate.setText(p.getDate());
        eNeck.setText(neck);
        eBust.setText(bust);
        eChest.setText(chest);
        eWaist.setText(waist);
        eHip.setText(hip);
        eInseam.setText(inseam);
        eComment.setText(p.getComment());
    }

    /**
     * Create a Date picker windows that pop up when selecting the date text box
     * Default to be current date
     * */
    public void seteDate() {

        cal = Calendar.getInstance();

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                cal.set(year, month, dayOfMonth);
                String myFormat = "yyyy-MM-dd"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

                eDate.setText(sdf.format(cal.getTime()));
            }
        };

        eDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Almost done");
                new DatePickerDialog(AddEdit.this,date,cal.get(Calendar.YEAR),cal.get(Calendar.MONTH),cal.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    /**
     * Open save file, convert the people ArrayList in MainActivity to GSon and save it to the file
     */
    private void saveInFile() {
        try {
            FileOutputStream fos = openFileOutput(MainActivity.FileName, Context.MODE_PRIVATE);
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(fos));

            Gson gson = new Gson();
            gson.toJson(MainActivity.people, out);
            out.flush();

            fos.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException();
        }
    }

}
