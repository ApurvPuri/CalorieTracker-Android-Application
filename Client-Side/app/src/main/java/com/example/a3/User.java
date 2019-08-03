package com.example.a3;

import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable {

    private String name = "";
    private String password = "";

    public User(Parcel in) {
        this.name = in.readString();
        this.password = in.readString();
    }

    public User(String password, String name) {
        this.password = password;
        this.name = name;
    }

    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeString(name);
        parcel.writeString(password);
    }

    public int describeContents() {
        return 0;
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public void setPassword(String password) {
        this.password = password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }
}

