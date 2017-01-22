package com.example.minhnguyen.tuongmin_sizebook;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;

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

public class AddEdit extends AppCompatActivity {

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

        prefs = getSharedPreferences(MainActivity.PrefFileName, MODE_PRIVATE);
        final SharedPreferences.Editor editor = prefs.edit();

        saveButton = (Button) findViewById(R.id.button_save);
        cancelButton = (Button) findViewById(R.id.cancel_button);
        deleteButton = (Button) findViewById(R.id.delete_button);

        Intent rI = getIntent();
        final String id = rI.getStringExtra("id");
        final Intent intent = new Intent(this.getApplicationContext(), MainActivity.class);

        seteDate();

        if (id == null) {
            cancelButton.setVisibility(View.VISIBLE);
            deleteButton.setVisibility(View.GONE);
            saveButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Person person = getInfo();
                    if (person != null) {
                        String i = person.toString();

                        String key = String.valueOf(Person.keygen());
                        editor.putString(key, i);
                        MainActivity.ID_people.add(key);
                        editor.putString("keysInOrder", MainActivity.ID_people.toString().replace("[","").replace("]",""));
                        editor.commit();

                        System.out.println(i);
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

        else {
            setInfo(id);
            cancelButton.setVisibility(View.GONE);
            deleteButton.setVisibility(View.VISIBLE);
            saveButton.setOnClickListener(new View.OnClickListener(){
                public void onClick(View v) {
                    String i = getInfo().toString();
                    editor.putString(id, i);
                    editor.commit();
                    startActivity(intent);
                }
            });

            deleteButton.setOnClickListener(new View.OnClickListener(){
                public void onClick(View v){
                    editor.remove(id);
                    MainActivity.ID_people.remove(MainActivity.ID_people.indexOf(id));
                    editor.putString("keysInOrder", MainActivity.ID_people.toString().replace("[","").replace("]",""));
                    editor.commit();
                    startActivity(intent);
                }
            });
        }
    }

    public Person getInfo() {
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

    public void setInfo(String id) {
        String info = prefs.getString(id, null);

        eName = (EditText) findViewById(R.id.name_editText);
        eDate = (EditText) findViewById(R.id.date_editText);
        eNeck = (EditText) findViewById(R.id.neck_editText);
        eBust = (EditText) findViewById(R.id.bust_editText);
        eChest = (EditText) findViewById(R.id.chest_editText);
        eWaist = (EditText) findViewById(R.id.waist_editText);
        eHip = (EditText) findViewById(R.id.hip_editText);
        eInseam = (EditText) findViewById(R.id.inseam_editText);
        eComment = (EditText) findViewById(R.id.comment_editText);

        String name = info.split(Person.sep)[0];
        String date = info.split(Person.sep)[1];
        String neck = info.split(Person.sep)[2];
        String bust = info.split(Person.sep)[3];
        String chest = info.split(Person.sep)[4];
        String waist = info.split(Person.sep)[5];
        String hip = info.split(Person.sep)[6];
        String inseam = info.split(Person.sep)[7];
        String comment = info.split(Person.sep)[8];

        eName.setText(name);
        eDate.setText(date.equalsIgnoreCase(Person.NA) ? "" : date);
        eNeck.setText(neck.equalsIgnoreCase(Person.NA) ? "" : neck);
        eBust.setText(bust.equalsIgnoreCase(Person.NA) ? "" : bust);
        eChest.setText(chest.equalsIgnoreCase(Person.NA) ? "" :chest);
        eWaist.setText(waist.equalsIgnoreCase(Person.NA) ? "" : waist);
        eHip.setText(hip.equalsIgnoreCase(Person.NA) ? "" : hip);
        eInseam.setText(inseam.equalsIgnoreCase(Person.NA) ? "" : inseam);
        eComment.setText(comment.equalsIgnoreCase(Person.NA) ? "" :comment);

    }

    public void seteDate() {
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

}
