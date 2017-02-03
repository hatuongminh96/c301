package com.example.minhnguyen.tuongmin_sizebook;

/**
 * Created by MinhNguyen on 17/1/17.
 */

public class Person {
    /* Declare stuff */
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

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public Integer getNeck() {
        return Neck;
    }

    public void setNeck(Integer neck) {
        Neck = neck;
    }

    public Integer getBust() {
        return Bust;
    }

    public void setBust(Integer bust) {
        Bust = bust;
    }

    public Integer getChest() {
        return Chest;
    }

    public void setChest(Integer chest) {
        Chest = chest;
    }

    public Integer getWaist() {
        return Waist;
    }

    public void setWaist(Integer waist) {
        Waist = waist;
    }

    public Integer getHip() {
        return Hip;
    }

    public void setHip(Integer hip) {
        Hip = hip;
    }

    public Integer getInseam() {
        return Inseam;
    }

    public void setInseam(Integer inseam) {
        Inseam = inseam;
    }

    public String getComment() {
        return Comment;
    }

    public void setComment(String comment) {
        Comment = comment;
    }

    public Person(String name) {
        /* Initialize a person object */
        Name=name;
    }

    public String toString(){
        /* Join the attributes of a Person object to a string.
        * The attributes are separated by the sep variable.
        * Attributes that are null is replaced with NA variable.
        * */

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
        /* */

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
}
