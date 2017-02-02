package com.example.minhnguyen.tuongmin_sizebook;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import android.support.annotation.MainThread;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

public class AddEdit extends AppCompatActivity {

    /* Declare variables name */
    Button saveButton;
    Button cancelButton;
    Button deleteButton;
    SharedPreferences prefs;
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

        /* Define variables into real object */

        saveButton = (Button) findViewById(R.id.button_save);
        cancelButton = (Button) findViewById(R.id.cancel_button);
        deleteButton = (Button) findViewById(R.id.delete_button);

        /* Get ID value from MainActivity. ID is the key of the person selected on the ListView.
        * Default key when selecting "Add New" is null.
        * */
        Intent rI = getIntent();
        final String id = rI.getStringExtra("id");
        final Intent intent = new Intent(this.getApplicationContext(), MainActivity.class);

        /* Call seteDate. This function create a popup windows to select date when the eDate EditText is selected. */
        seteDate();

        if (id == null) {
            /* Add New is selected. Hide Delete button and show Cancel button. */

            cancelButton.setVisibility(View.VISIBLE);
            deleteButton.setVisibility(View.GONE);
            saveButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {

                    /* When click save, create a Person object from the information entered into the
                  * text boxes. Then generate an ID and save the Person object into a SharedPref
                  * as a String with the key is the ID generated. Add the ID to the ID list and save it into SharedPref with
                  * the key "keysInOrder". This will assure the entries appear on the ListView in order they were inserted.
                  * */

                    Person person = getInfo();
                    if (person != null) {
                        MainActivity.people.add(person);
                        saveInFile();
                        startActivity(intent);
                    }
                }
            });

            // If select cancel then bring back the MainActivity
            cancelButton.setOnClickListener(new View.OnClickListener(){
                public void onClick (View v) {
                    startActivity(intent);
                }
            });
        }

        else {
            /*
            * An entry in ListView is selected. Hide the back button and show the delete button.
            * Returning to the previous screen can still be done with the Android back button.
            * Get info from the string saved in SharedPref and fill the text boxes.
            * When click save, get information in the text boxes and save it to SharedPref
            * with the same ID. */


            cancelButton.setVisibility(View.GONE);
            deleteButton.setVisibility(View.VISIBLE);
            saveButton.setOnClickListener(new View.OnClickListener(){
                public void onClick(View v) {

                    startActivity(intent);
                }
            });

            deleteButton.setOnClickListener(new View.OnClickListener(){
                public void onClick(View v){
                    /* When click delete, remove the entry of that person in SharedPref.
                    * Also update the ID list and save the updated list with key "keysInOrder".
                    * */


                    startActivity(intent);
                }
            });
        }
    }

    public Person getInfo() {
        /*
        * Get the information entered into text boxes  to create a Person object.
        * A person object needs a name. Any other variables can be null */

        eName = (EditText) findViewById(R.id.name_editText);
        eDate = (EditText) findViewById(R.id.date_editText);
        eNeck = (EditText) findViewById(R.id.neck_editText);
        eBust = (EditText) findViewById(R.id.bust_editText);
        eChest = (EditText) findViewById(R.id.chest_editText);
        eWaist = (EditText) findViewById(R.id.waist_editText);
        eHip = (EditText) findViewById(R.id.hip_editText);
        eInseam = (EditText) findViewById(R.id.inseam_editText);
        eComment = (EditText) findViewById(R.id.comment_editText);

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
            return new Person(name, date, neck, bust, chest, waist, hip, inseam, comment);
        }

        else {
            Toast.makeText(getApplicationContext(), "A man needs a name!", Toast.LENGTH_LONG).show();
            return null;
        }
    }

    public void seteDate() {
        /*
        * Create a Date picker windows that pop up when selecting the date text box
        * Default to be current date
        * */

        cal = Calendar.getInstance();
        eDate = (EditText) findViewById(R.id.date_editText);

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
