/*
 * Copyright (c) 2017. Tuong Minh Nguyen Tran. University of Alberta. All rights reserved.
 */

package com.example.minhnguyen.tuongmin_sizebook;

/**
 * Created by MinhNguyen on 17/1/17.
 */

 public class Person {
    /* Declare stuff */
    private String Name;
    private String Date;
    private Float Neck;
    private Float Bust;
    private Float Chest;
    private Float Waist;
    private Float Hip;
    private Float Inseam;
    private String Comment;

    /** Initialize a person object
     * A Person object always needs a name
     * */
     public Person(String name) {
        Name = name;
    }

    /**
     * Auto-generated getters and setters
     */

     String getName() { return Name; }

     String getDate() { return Date; }

     void setDate(String date) { Date = date; }

     Float getNeck() { return Neck; }

     void setNeck(Float neck) { Neck = round(neck); }

     Float getBust() { return Bust; }

     void setBust(Float bust) { Bust = round(bust); }

     Float getChest() { return Chest; }

     void setChest(Float chest) { Chest = round(chest); }

     Float getWaist() { return Waist; }

     void setWaist(Float waist) { Waist = round(waist); }

     Float getHip() { return Hip; }

     void setHip(Float hip) { Hip = round(hip); }

     Float getInseam() { return Inseam; }

     void setInseam(Float inseam) { Inseam = round(inseam); }

     String getComment() { return Comment; }

     void setComment(String comment) { Comment = comment; }

    private Float round(Float value){
        if (value == null || value % 0.5F == 0) return value;
        else if (Math.abs(Math.round(value) - value) <= 0.25F) return Float.valueOf(Math.round(value));
        else if (Math.round(value) > value) return Math.round(value) - 0.5F;
        else return Math.round(value) + 0.5F;
    }
}
