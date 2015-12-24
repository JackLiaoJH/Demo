package com.liaojh.aidldemoservice;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author LiaoJH
 * @DATE 15/12/20
 * @VERSION 1.0
 * @DESC TODO
 */
public class User implements Parcelable {
    public String name;
    public String sex;

    public User() {
    }

    public User(String name, String sex) {
        this.name = name;
        this.sex = sex;
    }

    protected User(Parcel in) {
        name = in.readString();
        sex = in.readString();
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(sex);
    }
}
