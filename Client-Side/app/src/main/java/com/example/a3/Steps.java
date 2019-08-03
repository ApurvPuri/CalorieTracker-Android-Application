package com.example.a3;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;


@Entity
public class Steps {
    @PrimaryKey(autoGenerate = true)
    public int sid;
    @ColumnInfo(name = "total_steps")
    public int totalSteps;
    @ColumnInfo(name = "recorded_time")
    public String time;

    public Steps(int totalSteps, String time) {
        this.totalSteps = totalSteps;
        this.time = time;
    }

    public int getId() {
        return sid;
    }

    public int getTotalSteps() {
        return totalSteps;
    }

    public void setTotalSteps(int totalSteps) {
        this.totalSteps = totalSteps;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
