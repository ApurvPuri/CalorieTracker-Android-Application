package com.example.a3;

import java.util.Date;

public class NewReport {

    private Integer reportid;
    private Date date;
    private int totcalconsumed;
    private int calburned;
    private int stepstaken;
    private int caloriegoal;
    private NewUser userid;

    public NewReport(Integer reportid, Date date, int totcalconsumed, int calburned, int stepstaken, int caloriegoal, NewUser userid) {
        this.reportid = reportid;
        this.date = date;
        this.totcalconsumed = totcalconsumed;
        this.calburned = calburned;
        this.stepstaken = stepstaken;
        this.caloriegoal = caloriegoal;
        this.userid = userid;
    }
}
