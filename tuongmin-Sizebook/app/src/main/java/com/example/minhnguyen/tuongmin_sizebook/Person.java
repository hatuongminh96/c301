/*
 * Copyright (c) 2017. Tuong Minh Nguyen Tran. University of Alberta. All rights reserved.
 */

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

    /** Initialize a person object
     * A Person object always needs a name
     * */
    public Person(String name) {
        Name=name;
    }

    /**
     * Auto-generated getters and setters
     */

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
}
