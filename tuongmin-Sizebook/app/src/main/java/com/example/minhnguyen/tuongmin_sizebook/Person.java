package com.example.minhnguyen.tuongmin_sizebook;

import android.provider.BaseColumns;

import java.util.Date;

/**
 * Created by MinhNguyen on 17/1/17.
 */

public class Person {
    String Name;
    String Date;
    Integer Neck;
    Integer Bust;
    Integer Chest;
    Integer Waist;
    Integer Hip;
    Integer Inseam;
    String Comment;
    static String NA = "dknlklsdnclqpowe3rwejlkdc";
    static String sep = ";lkafnlknda;lsdm adasd";

    public Person(String q, String w, Integer e, Integer r, Integer t, Integer y, Integer u, Integer i, String o) {
        Name=q;
        Date=w;
        Neck=e;
        Bust=r;
        Chest=t;
        Waist=y;
        Hip=u;
        Inseam=i;
        Comment=o;
    }

    public static int keygen() {
        int key = 0;
        for (String o: MainActivity.ID_people) {
            if (Integer.valueOf(o) >= key) {
                key = Integer.valueOf(o)+1;
            }
        }
        return key;
    }

    public String toString(){
        String i;
        i = Name + sep;
        i += (!Date.isEmpty() ? Date + sep : NA + sep);
        i += (Neck != null ? Neck + sep : NA + sep);
        i += (Bust != null ? Bust + sep : NA + sep);
        i += (Chest != null ? Chest + sep : NA + sep);
        i += (Waist != null ? Waist + sep : NA + sep);
        i += (Hip != null ? Hip + sep : NA + sep);
        i += (Inseam != null ? Inseam + sep : NA + sep);
        i += (!Comment.isEmpty() ? Comment : NA);
        return i;
    }

    public void fromString(String info) {
        Name = info.split(Person.sep)[0];
        Date = info.split(Person.sep)[1].equalsIgnoreCase(NA)?null:info.split(Person.sep)[1];
        Neck = info.split(Person.sep)[2].equalsIgnoreCase(NA)?null:Integer.valueOf(info.split(Person.sep)[2]);
        Bust = info.split(Person.sep)[3].equalsIgnoreCase(NA)?null:Integer.valueOf(info.split(Person.sep)[3]);
        Chest = info.split(Person.sep)[4].equalsIgnoreCase(NA)?null:Integer.valueOf(info.split(Person.sep)[4]);
        Waist = info.split(Person.sep)[5].equalsIgnoreCase(NA)?null:Integer.valueOf(info.split(Person.sep)[5]);
        Hip = info.split(Person.sep)[6].equalsIgnoreCase(NA)?null:Integer.valueOf(info.split(Person.sep)[6]);
        Inseam = info.split(Person.sep)[7].equalsIgnoreCase(NA)?null:Integer.valueOf(info.split(Person.sep)[7]);
        Comment = info.split(Person.sep)[8].equalsIgnoreCase(NA)?null:info.split(Person.sep)[2];
    }

    public static class PersonEntry implements BaseColumns {
        public static final String TABLE_NAME = "Person";
        public static final String ID = "id";
        public static final String NAME = "Name";
        public static final String DATE = "Date";
        public static final String NECK = "Neck";
        public static final String BUST = "Bust";
        public static final String CHEST = "Chest";
        public static final String WAIST = "Waist";
        public static final String HIP = "Hip";
        public static final String INSEAM = "Inseam";
        public static final String COMMENT = "Comment";
    }

}
