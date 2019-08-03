package com.example.a3;

import java.math.BigDecimal;

public class NewFood {
    private Integer foodid;
    private String name;
    private String category;
    private Integer calorieamount;
    private String servingunit;
    private Integer fat;
    private BigDecimal servingamount;

    public NewFood(Integer foodid) {
        this.foodid = foodid;
    }

    public Integer getFoodid() {
        return foodid;
    }

    public void setFoodid(Integer foodid) {
        this.foodid = foodid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Integer getCalorieamount() {
        return calorieamount;
    }

    public void setCalorieamount(Integer calorieamount) {
        this.calorieamount = calorieamount;
    }

    public String getServingunit() {
        return servingunit;
    }

    public void setServingunit(String servingunit) {
        this.servingunit = servingunit;
    }

    public Integer getFat() {
        return fat;
    }

    public void setFat(Integer fat) {
        this.fat = fat;
    }

    public BigDecimal getServingamount() {
        return servingamount;
    }

    public void setServingamount(BigDecimal servingamount) {
        this.servingamount = servingamount;
    }

    public NewFood(String name, String category,
                   Integer calorieamount,
                   String servingunit,
                   Integer fat,
                   BigDecimal servingamount, Integer foodid) {
        this.foodid = foodid;
        this.name = name;
        this.category = category;
        this.calorieamount = calorieamount;
        this.servingunit = servingunit;
        this.fat = fat;
        this.servingamount = servingamount;
    }
}
