package com.example.a3;

import java.util.Date;

public class NewCredentials {
    private Integer userid;
    private String username;
    private Date signupdate;
    private String passwordhash;

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getSignupdate() {
        return signupdate;
    }

    public void setSignupdate(Date signupdate) {
        this.signupdate = signupdate;
    }

    public String getPasswordhash() {
        return passwordhash;
    }

    public void setPasswordhash(String passwordhash) {
        this.passwordhash = passwordhash;
    }

    public NewCredentials(Integer userid, String username, Date signupdate, String passwordhash) {
        this.userid = userid;
        this.username = username;
        this.signupdate = signupdate;
        this.passwordhash = passwordhash;
    }
}
