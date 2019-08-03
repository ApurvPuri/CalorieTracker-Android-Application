package com.example.a3;

import java.util.Date;

public class NewUser {
    private Integer userid;
    private String name;
    private String surname;
    private String email;
    private Date dateofbirth;
    private Integer height;
    private Integer weight;
    private String gender;
    private String address;
    private Integer postcode;
    private Integer levelofactivity;
    private Integer stepspermile;

    public NewUser(Integer userid) {
        this.userid = userid;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getDateofbirth() {
        return dateofbirth;
    }

    public void setDateofbirth(Date dateofbirth) {
        this.dateofbirth = dateofbirth;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getPostcode() {
        return postcode;
    }

    public void setPostcode(Integer postcode) {
        this.postcode = postcode;
    }

    public Integer getLevelofactivity() {
        return levelofactivity;
    }

    public void setLevelofactivity(Integer levelofactivity) {
        this.levelofactivity = levelofactivity;
    }

    public NewUser(Integer userid, String name, String surname, String email, Date dateofbirth, Integer height, Integer weight,
                   String gender, String address, Integer postcode, Integer levelofactivity, Integer stepspermile) {
        this.userid = userid;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.dateofbirth = dateofbirth;
        this.height = height;
        this.weight = weight;
        this.gender = gender;
        this.address = address;
        this.postcode = postcode;
        this.levelofactivity = levelofactivity;
        this.stepspermile = stepspermile;
    }
}
