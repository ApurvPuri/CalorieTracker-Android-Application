package com.example.a3;

import java.util.Date;

public class NewConsumption {
    private Integer consumptionid;
    private Date date;
    private int foodserving;
    private NewFood foodid;
    private NewUser userid;





    public NewConsumption(Integer consumptionid,
                          Date date,
                          int foodserving,
                          NewFood foodid,
                          NewUser userid) {
        this.consumptionid = consumptionid;
        this.date = date;
        this.foodserving = foodserving;
        this.foodid = foodid;
        this.userid = userid;
    }
}
